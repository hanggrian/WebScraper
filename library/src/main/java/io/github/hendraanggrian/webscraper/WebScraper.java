package io.github.hendraanggrian.webscraper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.List;

import io.github.hendraanggrian.webscraper.internal.ExpandUrlTask;
import io.github.hendraanggrian.webscraper.internal.WebScraperClient;
import io.github.hendraanggrian.webscraper.internal.WebScraperInterface;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class WebScraper extends WebView {

    private List<Callback> callbacks;
    private boolean fetchLongUrl;
    private ExpandUrlTask task;

    public WebScraper(Context context) {
        super(context);
        init(context);
    }

    public WebScraper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init(Context context) {
        if (!(context instanceof Activity))
            throw new RuntimeException("Please use Activity as first param of WebScraper constructor, not plain Context.");

        this.callbacks = new ArrayList<>();
        this.getSettings().setJavaScriptEnabled(true);
    }

    public WebScraper addCallback(Callback callback) {
        this.callbacks.add(callback);
        return this;
    }

    public WebScraper removeCallback(Callback callback) {
        this.callbacks.remove(callback);
        return this;
    }

    public WebScraper removeAllCallbacks() {
        this.callbacks.clear();
        return this;
    }

    public WebScraper setUserAgent(String userAgent) {
        this.getSettings().setUserAgentString(userAgent);
        return this;
    }

    public WebScraper fetchLongUrl(boolean fetchLongUrl) {
        this.fetchLongUrl = fetchLongUrl;
        return this;
    }

    @Override
    public void loadUrl(String url) {
        if (url.equals(WebScraperInterface.PROCESS_URL)) {
            super.loadUrl(url);
            return;
        }

        for (Callback callback : callbacks)
            callback.onStarted(this);

        if (fetchLongUrl) {
            task = new ExpandUrlTask() {
                @Override
                protected void onPreExecute() {
                    for (WebScraper.Callback callback : callbacks)
                        callback.onProgress(WebScraper.this, 0);
                }

                @Override
                protected void onPostExecute(Object o) {
                    if (o instanceof Exception) {
                        for (Callback callback : callbacks)
                            callback.onError(WebScraper.this, (Exception) o);

                    } else if (o instanceof String) {
                        addJavascriptInterface(scraperInterface, WebScraperInterface.NAME);
                        setWebViewClient(scraperClient);
                        WebScraper.super.loadUrl(o.toString());
                    }
                }
            };
            task.execute(url);

        } else {
            addJavascriptInterface(scraperInterface, WebScraperInterface.NAME);
            setWebViewClient(scraperClient);
            WebScraper.super.loadUrl(url);
        }
    }

    public void stop() {
        if (task != null && !task.isCancelled())
            task.cancel(true);
        removeJavascriptInterface(WebScraperInterface.NAME);
        setWebViewClient(new WebViewClient());

        stopLoading();
        clearHistory();
        clearCache(true);
    }

    public void retry() {
        stop();
        loadUrl(getOriginalUrl());
    }

    private WebScraperClient scraperClient = new WebScraperClient() {
        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            if (!finished)
                for (Callback callback : callbacks)
                    callback.onProgress(WebScraper.this, view.getProgress());
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            for (Callback callback : callbacks)
                callback.onRequest(WebScraper.this, url);
            return super.shouldInterceptRequest(view, url);
        }

        @Override
        public void onPageFinished(WebView view, final String url) {
            super.onPageFinished(view, url);
            view.loadUrl(WebScraperInterface.PROCESS_URL);
        }
    };

    private WebScraperInterface scraperInterface = new WebScraperInterface() {
        @Override
        @JavascriptInterface
        public void processHTML(final String html) {
            ((Activity) getContext()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (Callback callback : callbacks)
                        callback.onSuccess(WebScraper.this, html);
                }
            });
        }
    };

    public interface Callback {
        void onStarted(WebScraper scraper);

        void onProgress(WebScraper scraper, int progress);

        void onRequest(WebScraper scraper, String url);

        void onSuccess(WebScraper scraper, String html);

        void onError(WebScraper scraper, Exception exc);
    }

    public static class SimpleCallback implements Callback {
        @Override
        public void onStarted(WebScraper scraper) {
        }

        @Override
        public void onProgress(WebScraper scraper, int progress) {
        }

        @Override
        public void onRequest(WebScraper scraper, String url) {
        }

        @Override
        public void onSuccess(WebScraper scraper, String html) {
        }

        @Override
        public void onError(WebScraper scraper, Exception exc) {
        }
    }
}