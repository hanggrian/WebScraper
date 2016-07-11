package io.github.hendraanggrian.webreader.internal;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public abstract class WebReaderClient extends WebViewClient {

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