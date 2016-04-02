package com.example.fotagmobile;

import android.app.ActionBar;
import android.graphics.drawable.Drawable;
import android.media.Image;
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

    ArrayList<ImageButton> starButtonArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        model = new Model(this);
        model.addObserver(this);
        /*
        ImageButton star1 = (ImageButton) findViewById(R.id.star1);
        ImageButton star2 = (ImageButton) findViewById(R.id.star2);
        ImageButton star3 = (ImageButton) findViewById(R.id.star3);
        ImageButton star4 = (ImageButton) findViewById(R.id.star4);
        ImageButton star5 = (ImageButton) findViewById(R.id.star5);
        starButtonArray = new ArrayList<ImageButton>();
        starButtonArray.add(star1);
        starButtonArray.add(star2);
        starButtonArray.add(star3);
        starButtonArray.add(star4);
        starButtonArray.add(star5);
        */
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

        return super.onOptionsItemSelected(item);
    }

    public void setStars() {
        int stars = model.getStars();
        for (int i = 0; i < starButtonArray.size(); i++) {
            if (stars >= i+1) {
                starButtonArray.get(i).setImageResource(R.drawable.star_full);
            }
            else {
                starButtonArray.get(i).setImageResource(R.drawable.star_empty);
            }
        }
    }

    public void loadImages() {
        //loadSample((R.drawable.sample1));
        //loadSample((R.drawable.sample2));
        loadSample((R.drawable.sample3));
        loadSample((R.drawable.sample4));
        //loadSample((R.drawable.sample5));
        loadSample((R.drawable.sample6));
        loadSample((R.drawable.sample7));
        //loadSample((R.drawable.sample8));

    }

    public void loadSample(int drawableInt) {
        GridLayout gridLayout = (GridLayout) findViewById(R.id.imageLayout);
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        ImageButton thumbnail = new ImageButton(getApplicationContext());
        thumbnail.setImageResource(drawableInt);

        thumbnail.setAdjustViewBounds(true);
        thumbnail.setScaleType(ImageView.ScaleType.FIT_XY);
        linearLayout.addView(thumbnail);
/*
        LinearLayout buttonLayout = new LinearLayout(getApplicationContext());
        ImageButton star1 = new ImageButton(getApplicationContext());
        star1.setImageResource(R.drawable.star_empty);
        ImageButton star2 = new ImageButton(getApplicationContext());
        star2.setImageResource(R.drawable.star_empty);
        ImageButton star3 = new ImageButton(getApplicationContext());
        star3.setImageResource(R.drawable.star_empty);
        ImageButton star4 = new ImageButton(getApplicationContext());
        star4.setImageResource(R.drawable.star_empty);
        ImageButton star5 = new ImageButton(getApplicationContext());
        star5.setImageResource(R.drawable.star_empty);
        buttonLayout.addView(star1);
        buttonLayout.addView(star2);
        buttonLayout.addView(star3);
        buttonLayout.addView(star4);
        buttonLayout.addView(star5);
        linearLayout.addView(buttonLayout);
*/
        RatingBar ratingBar = new RatingBar(getApplicationContext());
        ratingBar.setNumStars(5);
        ratingBar.setStepSize(1);
        //ViewGroup.LayoutParams params = ratingBar.getLayoutParams();
        //params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        //params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        ratingBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.addView(ratingBar);

        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        gridLayout.addView(linearLayout);
    }

    public void rating1(View view) {
        if (model.getStars() == 1) {
            model.setStars(0);
        }
        else {
            model.setStars(1);
        }
    }

    public void rating2(View view) {
        if (model.getStars() == 2) {
            model.setStars(0);
        }
        else {
            model.setStars(2);
        }
    }

    public void rating3(View view) {
        if (model.getStars() == 3) {
            model.setStars(0);
        }
        else {
            model.setStars(3);
        }
    }

    public void rating4(View view) {
        if (model.getStars() == 4) {
            model.setStars(0);
        }
        else {
            model.setStars(4);
        }
    }

    public void rating5(View view) {
        if (model.getStars() == 5) {
            model.setStars(0);
        }
        else {
            model.setStars(5);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg == "stars") {
            setStars();
        }
    }

}
