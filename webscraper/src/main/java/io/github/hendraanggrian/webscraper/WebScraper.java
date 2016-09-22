package io.github.hendraanggrian.webscraper;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class WebScraper extends WebScraperBase {

    private int timeout;
    private OnTimeoutListener timeoutListener;
    private boolean isFinished;

    public WebScraper(Context context) {
        super(context);
    }

    public WebScraper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WebScraper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WebScraper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public WebScraper(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }

    public WebScraper setUserAgent(String userAgent) {
        this.getSettings().setUserAgentString(userAgent);
        return this;
    }

    public WebScraper timeout(int timeout, OnTimeoutListener listener) {
        this.timeout = timeout;
        this.timeoutListener = listener;
        return this;
    }

    public WebScraper clearTimeout() {
        this.timeoutListener = null;
        return this;
    }

    public WebScraper callback(final Callback callback) {
        setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (!getActivity().isFinishing())
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onStarted(WebScraper.this);
                        }
                    });

                isFinished = false;
                if (timeoutListener != null)
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(timeout);
                            } catch (InterruptedException exc) {
                                exc.printStackTrace();
                            }
                            if (!isFinished && !getActivity().isFinishing())
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        stopLoading();
                                        timeoutListener.onTimeout(WebScraper.this);
                                    }
                                });
                        }
                    }).start();
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                callback.onRequest(WebScraper.this, url);
                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                isFinished = true;
                view.loadUrl(PROCESS_URL);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                callback.onError(WebScraper.this);
            }
        });
        setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                callback.onProgress(WebScraper.this, newProgress);
            }
        });
        addJavascriptInterface(new JavascriptProcessor() {
            @Override
            @JavascriptInterface
            public void processHTML(final String html) {
                if (!getActivity().isFinishing())
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(WebScraper.this, html);
                        }
                    });
            }
        }, NAME);
        return this;
    }

    @Override
    public void loadUrl(String url) {
        if (!url.equals(PROCESS_URL)) {
            clearHistory();
            clearCache(true);
            super.loadUrl(url);
        } else {
            super.loadUrl(url);
        }
    }

    public interface OnTimeoutListener {

        void onTimeout(WebScraper webScraper);
    }

    public interface Callback {

        void onStarted(WebScraper scraper);

        void onProgress(WebScraper scraper, int progress);

        void onRequest(WebScraper scraper, String url);

        void onSuccess(WebScraper scraper, String html);

        void onError(WebScraper scraper);
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
        public void onError(WebScraper scraper) {

        }
    }
}