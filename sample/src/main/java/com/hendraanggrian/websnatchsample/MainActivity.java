package com.hendraanggrian.websnatchsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hendraanggrian.websnatch.UserAgent;
import com.hendraanggrian.websnatch.WebSnatch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new WebSnatch(this)
                .setUserAgent(UserAgent.MOBILE)
                .load("http://www.smule.com/recording/charlie-puth-marvin-gaye/401669803_166079920", new WebSnatch.Completion() {
                    @Override
                    public void onSuccess(String html) {
                        for (int i = 0; i < html.split("\n").length; i++)
                            Log.w(String.valueOf(i), html.split("\n")[i]);
                    }

                    @Override
                    public void onError(Exception exc) {
                        exc.printStackTrace();
                    }
                });
    }
}