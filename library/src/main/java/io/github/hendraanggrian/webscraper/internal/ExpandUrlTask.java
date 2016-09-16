package io.github.hendraanggrian.webscraper.internal;

import android.os.AsyncTask;

import java.net.URL;
import java.net.URLConnection;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class ExpandUrlTask extends AsyncTask<String, Void, Object> {

    @Override
    protected Object doInBackground(String... params) {
        try {
            URLConnection conn = new URL(params[0]).openConnection();
            conn.getHeaderFields();
            return conn.getURL().toString();
        } catch (Exception exc) {
            return exc;
        }
    }
}