package com.example.picturesandgallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "DEBUG_MAIN";
    //any number
    private final int GET_PHOTO_FROM_GALLERY_REQUEST_CODE = 14;
    private final int TAKE_PHOTO_ACTIVITY_REQUEST_CODE = 21;

    File photoFile;
    String photoFileName = "myTestPhoto.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCameraPressed(View view) {
        // use the built in camera app
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // create a file handle for the phoot that will be taken
        this.photoFile = getFileForPhoto(this.photoFileName);


        // For api > 24, you need to wrap the File object in a file provider (content provider)
        // - See AndroidManifest.xml: <provider> tag
        // - See res/xml/fileprovider.xml
        Uri fileProvider = FileProvider.getUriForFile(MainActivity.this, "com.example.picturesandgallery", this.photoFile);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        Log.d(TAG, "The pickerview activity closed!");

        // get the photo and display it in the image view
        // check if we got a picture from the gallery
        if (data != null) {
            Uri photoURI = data.getData();

            Log.d(TAG, "Path to the photo the user selected: " + photoURI.toString());
            try {
                Bitmap selectedPhoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI);

                // make the image fit into the imageview
                ImageView ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
                ivPhoto.setImageBitmap(selectedPhoto);
            }
            catch (FileNotFoundException e) {
                Log.d(TAG, "FileNotFoundException: Unable to open photo gallery file");
                e.printStackTrace();
            }
            catch (IOException e) {
                Log.d(TAG, "IOException: Unable to open photo gallery file");
                e.printStackTrace();
            }
        }

    }

    // Helper function to generate an empty file to store the photo that you take with the Camera app
    public File getFileForPhoto(String fileName) {
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        if (mediaStorageDir.exists() == false && mediaStorageDir.mkdirs() == false) {
            Log.d(TAG, "Cannot create directory for storing photos");
        }

        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
        return file;
    }
}
