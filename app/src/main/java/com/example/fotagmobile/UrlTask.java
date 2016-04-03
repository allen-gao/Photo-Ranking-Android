package com.example.fotagmobile;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
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
            InputStream is = (InputStream) new URL(url).getContent();
            final Drawable d = Drawable.createFromStream(is, "src name");
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mainActivity.loadSample(0, d);
                }
            });
        } catch (Exception e) {
            // handle error
            Log.d("error", "" + e);
        }
        return null;
    }
}
