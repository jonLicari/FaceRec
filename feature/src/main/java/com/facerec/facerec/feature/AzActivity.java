package com.facerec.facerec.feature;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.*;
import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.graphics.*;
import android.widget.*;
import android.provider.*;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.location.Location;

public class AzActivity extends AppCompatActivity {

    private final int PICK_IMAGE = 1;
    private ProgressDialog detectionProgressDialog;
    private Location location = new Location(String.valueOf(this));
    double lat = location.getLatitude();
    double lon = location.getLongitude();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_az);
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(
                        intent, "Select Picture"), PICK_IMAGE);

                // --- GRAB COORDINATES BEFORE COMPOSING EMAIL --- //
                //getCor();
                // --- EMAIL FUNCTIONALITY --- //
                sendEmail();
            }
        });

        detectionProgressDialog = new ProgressDialog(this);
    }

    private void sendEmail() {
        Log.i(null, "Compose email");
        String[] TO = {""};
        String[] CC = {""};
        Intent intent1 = new Intent(Intent.ACTION_SEND);
        intent1.setData(Uri.parse("mailto:"));
        intent1.setType("text/plain");
        intent1.putExtra(Intent.EXTRA_EMAIL  , new String[]{"Recipient"});
        intent1.putExtra(Intent.EXTRA_SUBJECT, "subject");
        intent1.putExtra(Intent.EXTRA_TEXT , "Longitude: ");//+location.getLongitude());
        intent1.putExtra(Intent.EXTRA_TEXT , "Latitude: ");//+location.getLatitude());
        Toast.makeText(AzActivity.this, "Latitude: "+lat, Toast.LENGTH_SHORT).show();
        Toast.makeText(AzActivity.this, "Longitude: "+lon, Toast.LENGTH_SHORT).show();

        try {
            startActivity(Intent.createChooser(intent1, "Send mail..."));
            finish();
            Log.i("Finished sending email.", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(AzActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), uri);
                ImageView imageView = findViewById(R.id.imageView1);
                imageView.setImageBitmap(bitmap);

                // Comment out for tutorial
                //detectAndFrame(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
