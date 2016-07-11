package io.github.hendraanggrian.webreader;

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

import io.github.hendraanggrian.webreader.internal.ExpandUrlTask;
import io.github.hendraanggrian.webreader.internal.WebReaderClient;
import io.github.hendraanggrian.webreader.internal.WebReaderInterface;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class WebReader extends WebView {

    private List<Callback> callbacks;
    private boolean fetchLongUrl;
    private ExpandUrlTask task;

    public WebReader(Context context) {
        super(context);
        init(context);
    }

    public WebReader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init(Context context) {
        if (!(context instanceof Activity))
            throw new RuntimeException("Please use Activity as first param of WebReader constructor, not plain Context.");

        this.callbacks = new ArrayList<>();
        this.getSettings().setJavaScriptEnabled(true);
    }

    public WebReader addCallback(Callback callback) {
        this.callbacks.add(callback);
        return this;
    }

    public WebReader removeCallback(Callback callback) {
        this.callbacks.remove(callback);
        return this;
    }

    public WebReader removeAllCallbacks() {
        this.callbacks.clear();
        return this;
    }

    public WebReader setUserAgent(String userAgent) {
        this.getSettings().setUserAgentString(userAgent);
        return this;
    }

    public WebReader fetchLongUrl(boolean fetchLongUrl) {
        this.fetchLongUrl = fetchLongUrl;
        return this;
    }

    @Override
    public void loadUrl(String url) {
        if (url.equals(WebReaderInterface.PROCESS_URL)) {
            super.loadUrl(url);
            return;
        }

        for (Callback callback : callbacks)
            callback.onStarted(this);

        if (fetchLongUrl) {
            task = new ExpandUrlTask() {
                @Override
                protected void onPreExecute() {
                    for (WebReader.Callback callback : callbacks)
                        callback.onProgress(WebReader.this, 0);
                }

                @Override
                protected void onPostExecute(Object o) {
                    if (o instanceof Exception) {
                        for (Callback callback : callbacks)
                            callback.onError(WebReader.this, (Exception) o);

                    } else if (o instanceof String) {
                        addJavascriptInterface(readerInterface, WebReaderInterface.NAME);
                        setWebViewClient(readerClient);
                        WebReader.super.loadUrl(o.toString());
                    }
                }
            };
            task.execute(url);

        } else {
            addJavascriptInterface(readerInterface, WebReaderInterface.NAME);
            setWebViewClient(readerClient);
            WebReader.super.loadUrl(url);
        }
    }

    public void stop() {
        if (task != null && !task.isCancelled())
            task.cancel(true);
        removeJavascriptInterface(WebReaderInterface.NAME);
        setWebViewClient(new WebViewClient());

        stopLoading();
        clearHistory();
        clearCache(true);
    }

    public void retry() {
        stop();
        loadUrl(getOriginalUrl());
    }

    private WebReaderClient readerClient = new WebReaderClient() {
        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            if (!finished)
                for (Callback callback : callbacks)
                    callback.onProgress(WebReader.this, view.getProgress());
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            for (Callback callback : callbacks)
                callback.onRequest(WebReader.this, url);
            return super.shouldInterceptRequest(view, url);
        }

        @Override
        public void onPageFinished(WebView view, final String url) {
            super.onPageFinished(view, url);
            view.loadUrl(WebReaderInterface.PROCESS_URL);
        }
    };

    private WebReaderInterface readerInterface = new WebReaderInterface() {
        @Override
        @JavascriptInterface
        public void processHTML(final String html) {
            ((Activity) getContext()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (Callback callback : callbacks)
                        callback.onSuccess(WebReader.this, html);
                }
            });
        }
    };

    public interface Callback {
        void onStarted(WebReader reader);

        void onProgress(WebReader reader, int progress);

        void onRequest(WebReader reader, String url);

        void onSuccess(WebReader reader, String html);

        void onError(WebReader reader, Exception exc);
    }

    public static class SimpleCallback implements Callback {
        @Override
        public void onStarted(WebReader reader) {
        }

        @Override
        public void onProgress(WebReader reader, int progress) {
        }

        @Override
        public void onRequest(WebReader reader, String url) {
        }

        @Override
        public void onSuccess(WebReader reader, String html) {
        }

        @Override
        public void onError(WebReader reader, Exception exc) {
        }
    }
}