package com.example.fotagmobile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlTask extends AsyncTask {

    MainActivity mainActivity;
    String url;

    public UrlTask(MainActivity mainActivity, String url) {
        this.mainActivity = mainActivity;
        this.url = url;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            URL myUrl = new URL(url);
            final Bitmap bitmap = BitmapFactory.decodeStream(myUrl.openConnection().getInputStream());
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mainActivity.loadSample(bitmap);
                }
            });
        }
        catch(MalformedURLException e) {
            Log.e("MalformedURLException", e.toString());
        }
        catch(IOException e) {
            Log.e("IOException", e.toString());
        }
        return null;
    }
}
