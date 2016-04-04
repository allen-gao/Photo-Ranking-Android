package com.example.fotagmobile;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.SearchView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.searchView));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("test", query);
                UrlTask urlTask = new UrlTask(self, query);
                urlTask.execute();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


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
        loadSample(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.sample1));
        loadSample(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.sample2));
        loadSample(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.sample3));
        loadSample(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.sample4));
        loadSample(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.sample5));
        loadSample(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.sample6));
        loadSample(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.sample7));
        loadSample(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.sample8));
        loadSample(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.star_empty));
        loadSample(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.star_full));
    }

    public void loadSample(final Bitmap bitmap) {
        GridLayout gridLayout = (GridLayout) findViewById(R.id.imageLayout);
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(Color.GRAY);

        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
        );

/*
        GridLayout.LayoutParams parem = new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f),      GridLayout.spec(GridLayout.UNDEFINED, 1f));
        linearLayout.setLayoutParams(parem);
*/
        final ImageButton thumbnail = new ImageButton(getApplicationContext());
        thumbnail.setImageBitmap(bitmap);
        thumbnail.setAdjustViewBounds(true);
        thumbnail.setScaleType(ImageButton.ScaleType.FIT_XY);
        thumbnail.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = "bitmap_file";
                try {
                    FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close();
                    Intent intent = new Intent(getApplicationContext(), ImageActivity.class);
                    startActivity(intent);
                }
                catch(FileNotFoundException e) {
                    Log.e("FileNotFoundException", e.toString());
                }
                catch(IOException e) {
                    Log.e("IOException", e.toString());
                }
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
        //setContentView(R.layout.activity_main);

        Log.d("test", "test");
    }

}
