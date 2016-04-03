package com.example.fotagmobile;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ImageActivity extends AppCompatActivity {

    ImageActivity self = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        ImageButton imageButton = (ImageButton) findViewById(R.id.fullscreenImage);
        int imageResource = getIntent().getExtras().getInt("imageResource");
        imageButton.setImageResource(imageResource);

        imageButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                self.finish();
            }
        });
    }
}
