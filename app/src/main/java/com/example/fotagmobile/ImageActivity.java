package com.example.fotagmobile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.io.FileNotFoundException;

public class ImageActivity extends AppCompatActivity {

    ImageActivity self = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        ImageButton imageButton = (ImageButton) findViewById(R.id.fullscreenImage);
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(openFileInput("bitmap_file"));
            imageButton.setImageBitmap(bitmap);
        }
        catch(FileNotFoundException e) {
            Log.e("error", e.toString());
        }

        imageButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                self.finish();
            }
        });
    }
}
