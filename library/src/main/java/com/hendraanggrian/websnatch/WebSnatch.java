package com.hendraanggrian.websnatch;

import android.content.Context;
import android.os.AsyncTask;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by hendraanggrian on 06/01/16.
 */
public class WebSnatch extends WebView {

    private final String NAME = "HTMLOUT";
    private final String PROCESS_URL = "javascript:window.HTMLOUT.processHTML(document.getElementsByTagName('html')[0].innerHTML);";

    public WebSnatch(Context context) {
        super(context);
    }

    public WebSnatch setUserAgent(String userAgent) {
        getSettings().setUserAgentString(userAgent);
        return this;
    }

    public void load(String url, final Completion completion) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    URLConnection conn = new URL(params[0]).openConnection();
                    conn.getHeaderFields();
                    return conn.getURL().toString();
                } catch (Exception exc) {
                    completion.onError(exc);
                    return "";
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
        }.execute(url);

        try {
            getSettings().setJavaScriptEnabled(true);
            addJavascriptInterface(new SnatchInterface() {
                @Override
                @JavascriptInterface
                public void processHTML(String html) {
                    completion.onSuccess(html);
                }
            }, NAME);
            setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    view.loadUrl(PROCESS_URL);
                }
            });
            loadUrl(getLongUrl(url));
        } catch (Exception exc) {
            completion.onError(exc);
        }
    }

    public String getLongUrl(String url) throws IOException {
        URLConnection conn = new URL(url).openConnection();
        conn.getHeaderFields();
        return conn.getURL().toString();
    }

    public interface SnatchInterface {
        @JavascriptInterface
        void processHTML(String html);
    }

    public interface Completion {
        void onSuccess(String html);

        void onError(Exception exc);
    }
}