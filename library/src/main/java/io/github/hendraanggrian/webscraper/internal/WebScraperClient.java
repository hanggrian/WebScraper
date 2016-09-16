package io.github.hendraanggrian.webscraper.internal;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public abstract class WebScraperClient extends WebViewClient {

    protected boolean finished;

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        finished = false;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        finished = true;
    }
}