package com.example.administrator.mychat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class PicturesView2 extends AppCompatActivity {

    private ImageView picture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures_view2);
       picture=(ImageView)findViewById(R.id.pircture_Irev);
       picture.setImageBitmap(Chat.bitmap_pictureIrev);
    }
}
