package com.example.fotagmobile;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.Rating;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    Model model;

    RatingBar ratingBar;

    MainActivity self = this;

    // panels and ratingBars share the same index
    ArrayList<LinearLayout> panels;
    ArrayList<RatingBar> ratingBars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        model = new Model(this);
        model.addObserver(this);

        panels = new ArrayList<LinearLayout>();
        ratingBars = new ArrayList<RatingBar>();

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                model.setStars((int) rating);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.load_images) {
            loadImages();
            return true;
        }

        if (id == R.id.clear_rating) {
            clearRating();
            return true;
        }

        if (id == R.id.clear_all) {
            clearAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadImages() {
        //loadSample((R.drawable.sample1));
        //loadSample((R.drawable.sample2));
        //loadSample((R.drawable.sample3));
        //loadSample((R.drawable.sample4));
        //loadSample((R.drawable.sample5));
        loadSample((R.drawable.sample6));
        loadSample((R.drawable.sample7));
        loadSample((R.drawable.sample8));
        loadSample(R.drawable.star_full);

    }

    public void loadSample(int drawableInt) {
        GridLayout gridLayout = (GridLayout) findViewById(R.id.imageLayout);
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(Color.GRAY);

        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f)
        );

/*
        GridLayout.LayoutParams parem = new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f),      GridLayout.spec(GridLayout.UNDEFINED, 1f));
        linearLayout.setLayoutParams(parem);
*/
        final ImageButton thumbnail = new ImageButton(getApplicationContext());
        thumbnail.setImageResource(drawableInt);
        thumbnail.setTag(drawableInt); // because ImageButton.getImageResource doesn't exist
        thumbnail.setAdjustViewBounds(true);
        thumbnail.setScaleType(ImageView.ScaleType.FIT_XY);
        thumbnail.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ImageActivity.class);
                intent.putExtra("imageResource", (Integer) thumbnail.getTag());
                startActivity(intent);
            }
        });


        linearLayout.addView(thumbnail);

        final RatingBar ratingBar = new RatingBar(getApplicationContext());
        ratingBar.setNumStars(5);
        ratingBar.setStepSize(1);
        ratingBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.addView(ratingBar);

        Button clearButton = new Button(getApplicationContext());
        clearButton.setText("Clear Rating");
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingBar.setRating(0);
            }
        });
        linearLayout.addView(clearButton);

        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);

        gridLayout.addView(linearLayout);

        panels.add(linearLayout);
        ratingBars.add(ratingBar);
    }

    public void clearAll() {
        for (int i = 0; i < panels.size(); i++) {
            LinearLayout panel = panels.get(i);
            panel.removeAllViews();
            panel.setVisibility(LinearLayout.GONE);
        }
        panels.clear();
        ratingBars.clear();
    }

    public void clearRating() {
        ratingBar.setRating(0);
    }

    public void filterImages() {
        for (int i = 0; i < ratingBars.size(); i++) {
            if (ratingBars.get(i).getRating() < model.getStars()) {
                panels.get(i).setVisibility(LinearLayout.GONE);
            }
            else {
                panels.get(i).setVisibility(LinearLayout.VISIBLE);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg == "stars") {
            filterImages();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main);
        // TODO

        Log.d("test", "test");
    }

}
