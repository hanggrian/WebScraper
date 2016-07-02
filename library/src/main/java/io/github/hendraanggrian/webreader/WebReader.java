package io.github.hendraanggrian.webreader;

import android.app.Activity;
import android.os.AsyncTask;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class WebReader extends WebView {

    private final String NAME = "HTMLOUT";
    private final String PROCESS_URL = "javascript:window.HTMLOUT.processHTML(document.getElementsByTagName('html')[0].innerHTML);";

    private String url;
    private AsyncTask<Void, Void, WebSource> task;

    public WebReader(Activity activity, String url) {
        super(activity);
        this.url = url;
    }

    public WebReader userAgent(String userAgent) {
        this.getSettings().setUserAgentString(userAgent);
        return this;
    }

    public void read(final Completion completion) {
        task = new AsyncTask<Void, Void, WebSource>() {
            private Exception exception;

            @Override
            protected void onPreExecute() {
                completion.onStarted();
            }

            @Override
            protected WebSource doInBackground(Void... urls) {
                try {
                    return new WebSource(url);
                } catch (Exception exc) {
                    this.exception = exc;
                    return null;
                }
            }

            @Override
            protected void onPostExecute(WebSource webSource) {
                if (webSource == null && exception != null) {
                    completion.onError(exception);
                    return;
                }

                WebViewClient webViewClient = new WebViewClient() {
                    @Override
                    public void onLoadResource(WebView view, String url) {
                        super.onLoadResource(view, url);
                        completion.onProgress(view.getProgress());
                    }

                    @Override
                    public void onPageFinished(final WebView view, final String url) {
                        super.onPageFinished(view, url);
                        view.loadUrl(PROCESS_URL);
                    }
                };

                getSettings().setJavaScriptEnabled(true);
                addJavascriptInterface(new JavaScriptInterface() {
                    @Override
                    @JavascriptInterface
                    public void processHTML(final String html) {
                        ((Activity) getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                completion.onSuccess(html);
                            }
                        });
                    }
                }, NAME);
                setWebViewClient(webViewClient);
                loadUrl(webSource.getLongUrl());
            }
        };
        task.execute();
    }

    public void stop() {
        task.cancel(true);
        getSettings().setJavaScriptEnabled(false);
        setWebViewClient(null);
        stopLoading();
    }

    private interface JavaScriptInterface {
        @JavascriptInterface
        void processHTML(String html);
    }

    public static abstract class Completion {
        public abstract void onStarted();

        public abstract void onProgress(int progress);

        public abstract void onSuccess(String source);

        public abstract void onError(Exception exc);
    }

    public static class SimpleCompletion extends Completion {
        @Override
        public void onStarted() {
        }

        @Override
        public void onProgress(int progress) {
        }

        @Override
        public void onSuccess(String source) {
        }

        @Override
        public void onError(Exception exc) {
        }
    }
}