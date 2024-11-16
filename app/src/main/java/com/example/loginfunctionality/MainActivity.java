package com.example.loginfunctionality;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Note> noteList = new ArrayList<>();

    private static final int GALLERY_REQUEST = 1;
    private ImageView imgView;
    EditText emailStr, name, pss;
    String imagePath;
    Button submit_area, Check_users;
    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = AppDatabase.getInstance(this);
        imgView = findViewById(R.id.img);
        submit_area = findViewById(R.id.submit_area);
        Check_users = findViewById(R.id.Check_users);
        emailStr = findViewById(R.id.enter_email);
        name = findViewById(R.id.username);
        pss = findViewById(R.id.password);
        submit_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!emailStr.getText().toString().equals("")) {
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    if (emailStr.getText().toString().matches(emailPattern)) {
                        Toast.makeText(getApplicationContext(), "valid email address", Toast.LENGTH_SHORT).show();
                        if (!name.getText().toString().equals("")) {
                            if (name != null) {
                                Toast.makeText(getApplicationContext(), "valid username", Toast.LENGTH_SHORT).show();
                                if (!pss.getText().toString().equals("")) {
                                    if (pss != null) {
                                        Toast.makeText(getApplicationContext(), "valid password", Toast.LENGTH_SHORT).show();
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                // Perform the database operation on the background thread
                                                Note note = new Note(name.getText().toString(), emailStr.getText().toString(), pss.getText().toString(),imagePath);
                                                database.noteDao().insert(note);
                                            }
                                        }).start();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
        Check_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment performdatabaseoperations = new performdatabaseoperations();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //frame_container is your layout name in xml file
                transaction.replace(androidx.fragment.R.id.fragment_container_view_tag, performdatabaseoperations);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        imgView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST:
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        imgView.setImageBitmap(bitmap);
                        imagePath = getPathFromUri(selectedImage);

                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
            }
    }

    private String getPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        } else {
            return uri.getPath(); // Fallback to using the URI path if the cursor is null
        }
    }

}
