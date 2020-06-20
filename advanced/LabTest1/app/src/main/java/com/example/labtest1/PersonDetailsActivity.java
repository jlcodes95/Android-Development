package com.example.labtest1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PersonDetailsActivity extends AppCompatActivity {

    private int CODE = 21;
    private final String TAG = "DEBUG_PERSONDETAILSACTIVITY";
    private final int GET_PHOTO_FROM_GALLERY_REQUEST_CODE = 14;
    private final int TAKE_PHOTO_ACTIVITY_REQUEST_CODE = 21;
    private Person person;
    private int index;
    private ImageView ivSelfie;
    private Button btnSend;

    private String filename = "selfie.jpg";
    private File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);

        //attempt to get data
        try {
            Intent intent = getIntent();
            this.person = intent.getParcelableExtra("person");
            this.index = intent.getIntExtra("index", -1);
            Log.d(TAG, "the person is: " + this.person.toString());
            loadData();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadData() {
        this.ivSelfie = (ImageView) findViewById(R.id.ivSelfie);
        this.btnSend = (Button) findViewById(R.id.btnSend);

        ImageView ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        TextView tvFullName = (TextView) findViewById(R.id.tvFullName);
        TextView tvLastName = (TextView) findViewById(R.id.tvLastName);
        TextView tvCoordinates = (TextView) findViewById(R.id.tvCoordinates);

        loadImage(ivPhoto, this.person.getPhotoURL());
        tvFullName.setText(this.person.getFullName());
        tvLastName.setText("Last Name: " + this.person.getLastName());
        tvCoordinates.setText("Coordinates: " + this.person.getCoordinates());
    }

    public void loadImage(final ImageView ivPhoto, String url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                        ivPhoto.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    public void onInterestedPressed(View view) {
        Random random = new Random();
        boolean like = random.nextBoolean();
        if (!like) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Unlucky");
            builder.setMessage("Unfortunately, the other person is not interested. Click OK to go back.");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    redirectAndRemove();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }else {
            getPhoto(view);
        }
    }

    public void onNotInterestedPressed(View view) {
        redirectAndRemove();
    }

    public void onSendPressed(View view) {
        TextView tvResult = (TextView) findViewById(R.id.tvResult);
        tvResult.setText("Photo Sent!");
    }

    private void getPhoto(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Upload Picture");
        builder.setMessage("Please upload a picture of yourself");

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
        Uri fileProvider = FileProvider.getUriForFile(PersonDetailsActivity.this, "com.example.labtest1", this.photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // Try to open the camera app
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, TAKE_PHOTO_ACTIVITY_REQUEST_CODE);
        }
    }

    public void onGalleryPressed(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Log.d(TAG, "Path to photo gallery: " + MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, GET_PHOTO_FROM_GALLERY_REQUEST_CODE);
        }
    }

    private void redirectAndRemove() {
        Intent intent = new Intent(this, MatchesActivity.class);
//        intent.putExtra("personToRemove", this.person);
        intent.putExtra("index", this.index);
        setResult(RESULT_OK, intent);
        finish();
    }

    // Helper function to generate an empty file to store the photo that you take with the Camera app
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
                Bitmap image = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

                // make the image fit into the imageview
                this.ivSelfie.setImageBitmap(image);
                this.btnSend.setVisibility(View.VISIBLE);
            } else {
                Toast t = Toast.makeText(this, "Not able to take photo", Toast.LENGTH_SHORT);
                t.show();
            }
        }

        // check if it was the photo gallery that just closed
        if (requestCode == GET_PHOTO_FROM_GALLERY_REQUEST_CODE) {
            // check if we got a picture from the gallery
            if (data != null) {
                Uri photoURI = data.getData();

                Log.d(TAG, "Path to the photo the user selected: " + photoURI.toString());
                try {
                    Bitmap selectedPhoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI);

                    // make the image fit into the imageview
                    this.ivSelfie.setImageBitmap(selectedPhoto);
                    this.btnSend.setVisibility(View.VISIBLE);
                } catch (FileNotFoundException e) {
                    Log.d(TAG, "FileNotFoundException: Unable to open photo gallery file");
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d(TAG, "IOException: Unable to open photo gallery file");
                    e.printStackTrace();
                }
            }
        }
    }

}
