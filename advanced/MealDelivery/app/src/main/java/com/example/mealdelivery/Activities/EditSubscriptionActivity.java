package com.example.mealdelivery.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mealdelivery.Data.Subscription;
import com.example.mealdelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class EditSubscriptionActivity extends AppCompatActivity {

    private final String TAG = "DEBUG_NEWSUBSCRIPTION";
    private final int GET_PHOTO_FROM_GALLERY_REQUEST_CODE = 14;
    private final int TAKE_PHOTO_ACTIVITY_REQUEST_CODE = 21;

    private final String ERR_MSG_NAME = "Please enter a name";
    private final String ERR_MSG_PRICE = "Please enter a price";
    private final String ERR_MSG_DISCOUNT = "Please enter a discount for semi-annual subscription";
    private final String ERR_MSG_DESCRIPTION = "Please enter a description";
    private final String ERR_MSG_PHOTO = "Please choose a photo to represent meal kit";
    private final String ERR_MSG_SAMPLE_PHOTO = "Please choose at least one sample meal photo";
    private final String ERR_MSG_NUMBER_FORMAT = "Please make sure the price and the discounts are valid numbers";
    private final String MSG_SUCC_ADD = "Successfully added subscription";
    private final String MSG_SUCC_UPDATE = "Successfully updated subscription";
    private final String MSG_FAILED_PHOTO = "Failed to take photo";
    private final String MSG_FAILED_FILE = "Failed to upload file";


    private EditText etName;
    private EditText etPrice;
    private EditText etDiscount;
    private EditText etAllergy;
    private EditText etDescription;
    private ImageView ivPhoto;
    private HorizontalScrollView hsvSamplePhoto;
    private LinearLayout layoutSamplePhoto;


    private String photoUrl;
    private ArrayList<String> samplePhotoUrls;

    private String filename = "image.jpg";
    private File photoFile;

    private FirebaseFirestore db;
    private StorageReference storage;
    private boolean isMealkitPhoto = true;
    private String documentId;
    private Subscription existingSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subscription);

        Toolbar toolbar = findViewById(R.id.tbNewSubscription);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        etDiscount = findViewById(R.id.etDiscount);
        etAllergy = findViewById(R.id.etAllergy);
        etDescription = findViewById(R.id.etDescription);
        ivPhoto = findViewById(R.id.ivPhoto);
        hsvSamplePhoto = findViewById(R.id.hsvSamplePhoto);
        layoutSamplePhoto = findViewById(R.id.layoutSamplePhoto);

        samplePhotoUrls = new ArrayList<>();

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance().getReference();

        Intent intent = getIntent();
        existingSubscription = (Subscription) intent.getSerializableExtra("existingSubscription");
        documentId = intent.getStringExtra("documentId");
        if (existingSubscription != null) {
            loadExistingSubscriptionData();
            TextView tvNewSubscription = findViewById(R.id.tvNewSubscription);
            tvNewSubscription.setText("Update Subscription");
            Button btnAddSubscription = findViewById(R.id.btnAddSubscription);
            btnAddSubscription.setText("Update Subscription");
        }
    }

    public void onAddPhotoPressed(View view) {
        Log.d(TAG, "add photo");
        //get camera / gallery
        isMealkitPhoto = true;
        getPhoto(view);
    }

    public void onAddSamplePhotoPressed(View view) {
        Log.d(TAG, "add sample photo");
        //get camera / gallery
        isMealkitPhoto = false;
        getPhoto(view);
    }

    public void onAddSubscriptionPressed(View view) {
        Log.d(TAG, "add subscription");
        String name = etName.getText().toString();
        String price = etPrice.getText().toString();
        String discount = etDiscount.getText().toString();
        String allergy = etAllergy.getText().toString();
        String description = etDescription.getText().toString();

        if (name.isEmpty()) {
            etName.setError(ERR_MSG_NAME);
        }else if (price.isEmpty()) {
            etPrice.setError(ERR_MSG_PRICE);
        }else if (discount.isEmpty()) {
            etDiscount.setError(ERR_MSG_DISCOUNT);
        }else if (description.isEmpty()) {
            etDescription.setError(ERR_MSG_DESCRIPTION);
        }else if (photoUrl == null) {
            Toast.makeText(this, ERR_MSG_PHOTO, Toast.LENGTH_LONG).show();
        }else if (samplePhotoUrls.size() == 0) {
            Toast.makeText(this, ERR_MSG_SAMPLE_PHOTO, Toast.LENGTH_LONG).show();
        }else {
            try {
                Subscription subscription = new Subscription(name,
                        Double.parseDouble(price),
                        Double.parseDouble(discount),
                        allergy,
                        description,
                        photoUrl,
                        samplePhotoUrls);
                if (existingSubscription == null) {
                    addSubscription(subscription);
                }else {
                    updateSubscription(subscription);
                }
            }catch (NumberFormatException e) {
                etPrice.setError(ERR_MSG_NUMBER_FORMAT);
                etDiscount.setError(ERR_MSG_NUMBER_FORMAT);
            }
        }
    }

    private void addSubscription(Subscription subscription) {
        db.collection("subscriptions")
                .add(subscription)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditSubscriptionActivity.this, MSG_SUCC_ADD, Toast.LENGTH_LONG).show();
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                });
    }

    private void updateSubscription(Subscription subscription) {
        db.collection("subscriptions")
                .document(documentId)
                .set(subscription)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditSubscriptionActivity.this, MSG_SUCC_UPDATE, Toast.LENGTH_LONG).show();
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }

    private void getPhoto(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Upload Picture");
        builder.setMessage("Please upload a photo");

        builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                onCameraPressed(view);
            }
        });

        builder.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                onGalleryPressed(view);
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void onCameraPressed(View view) {
        // use the built in camera app
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // create a file handle for the phoot that will be taken
        this.photoFile = getFileForPhoto(this.filename);
        Uri fileProvider = FileProvider.getUriForFile(EditSubscriptionActivity.this, "com.example.mealdelivery", this.photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // Try to open the camera app
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, TAKE_PHOTO_ACTIVITY_REQUEST_CODE);
        }
    }

    private String getFileUrl(String fileName) {
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        if (mediaStorageDir.exists() == false && mediaStorageDir.mkdirs() == false) {
            Log.d(TAG, "Cannot create directory for storing photos");
        }

        return mediaStorageDir.getPath() + File.separator + fileName;
    }

    private File getFileForPhoto(String fileName) {
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        if (mediaStorageDir.exists() == false && mediaStorageDir.mkdirs() == false) {
            Log.d(TAG, "Cannot create directory for storing photos");
        }

        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        Log.d(TAG, "The pickerview activity closed!");

        // check if it was the camera app that just closed
        if (requestCode == TAKE_PHOTO_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // the photo is saved to file
//                Bitmap image = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                Log.d(TAG, photoFile.getAbsolutePath());
                uploadFile(Uri.fromFile(photoFile));
            } else {
                Toast t = Toast.makeText(this, MSG_FAILED_PHOTO, Toast.LENGTH_SHORT);
                t.show();
            }
        }

        // check if it was the photo gallery that just closed
        if (requestCode == GET_PHOTO_FROM_GALLERY_REQUEST_CODE) {
            // check if we got a picture from the gallery
            if (data != null) {
                Uri photoURI = data.getData();
                uploadFile(data.getData());
                Log.d(TAG, "Path to the photo the user selected: " + photoURI.toString());
            }
        }
    }

    public void onGalleryPressed(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Log.d(TAG, "Path to photo gallery: " + MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, GET_PHOTO_FROM_GALLERY_REQUEST_CODE);
        }
    }

    private void uploadFile(final Uri photoURI) {

//        Uri file = Uri.fromFile(new File(photoURI));
        StorageReference imageRef = storage.child("images/" + UUID.randomUUID().toString());

        imageRef.putFile(photoURI)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess
                            (UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> subTask) {
                                if (subTask.isSuccessful()) {
                                    Log.d(TAG, subTask.getResult().toString());
                                    Uri uri = subTask.getResult();
                                    if (isMealkitPhoto) {
                                        photoUrl = uri.toString();
                                        setMealkitPhoto(photoURI);
                                    }else {
                                        samplePhotoUrls.add(uri.toString());
                                        addSamplePhoto(photoURI);
                                    }
                                }
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        Toast.makeText(EditSubscriptionActivity.this, MSG_FAILED_FILE, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void setMealkitPhoto(Uri uri) {
        try {
            Log.d(TAG, "setting mealkitphoto");
            Bitmap image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            // make the image fit into the imageview
            ivPhoto.setImageBitmap(image);
        } catch (FileNotFoundException e) {
            Log.d(TAG, "FileNotFoundException: Unable to open photo gallery file");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, "IOException: Unable to open photo gallery file");
            e.printStackTrace();
        }
    }

    private void addSamplePhoto(Uri uri) {
        try {
            Log.d(TAG, "adding sample photo");
            Bitmap image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            // make the image fit into the imageview
            ImageView photo = new ImageView(this);
            photo.setImageBitmap(image);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    (int) getResources().getDimension(R.dimen.sample_photo_width),
                    (int) getResources().getDimension(R.dimen.sample_photo_height));
            photo.setLayoutParams(layoutParams);
            photo.setScaleType(ImageView.ScaleType.CENTER_CROP);
            photo.setPadding(5, 5, 5, 5);
            layoutSamplePhoto.addView(photo);
        } catch (FileNotFoundException e) {

            Log.d(TAG, "FileNotFoundException: Unable to open photo gallery file");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, "IOException: Unable to open photo gallery file");
            e.printStackTrace();
        }
    }

    private void loadExistingSubscriptionData() {
        etName.setText(existingSubscription.getName());
        etPrice.setText("" + existingSubscription.getPrice());
        etDiscount.setText("" + existingSubscription.getDiscount());
        etDescription.setText(existingSubscription.getDescription());
        etAllergy.setText(existingSubscription.getAllergy());
        photoUrl = existingSubscription.getPhotoUrl();
        samplePhotoUrls = existingSubscription.getSamplePhotoUrls();

        Glide.with(this).load(photoUrl).into(ivPhoto);

        for (String url: samplePhotoUrls) {
            ImageView photo = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    (int) getResources().getDimension(R.dimen.sample_photo_width),
                    (int) getResources().getDimension(R.dimen.sample_photo_height));
            photo.setLayoutParams(layoutParams);
            photo.setScaleType(ImageView.ScaleType.CENTER_CROP);
            photo.setPadding(5, 5, 5, 5);
            Glide.with(this).load(url).into(photo);
            layoutSamplePhoto.addView(photo);
        }
    }
}
