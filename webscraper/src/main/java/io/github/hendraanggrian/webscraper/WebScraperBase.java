package io.github.hendraanggrian.webscraper;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public abstract class WebScraperBase extends WebView {

    protected final String NAME = "HTMLOUT";
    protected final String PROCESS_URL = "javascript:window.HTMLOUT.processHTML(document.getElementsByTagName('html')[0].innerHTML);";

    protected WebScraperBase(Context context) {
        super(context);
        init(context);
    }

    protected WebScraperBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    protected WebScraperBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected WebScraperBase(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    protected WebScraperBase(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
        init(context);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init(Context context) {
        if (!(context instanceof Activity))
            throw new RuntimeException("Please use Activity as first param of WebScraper constructor, not plain Context.");
        this.getSettings().setJavaScriptEnabled(true);
    }

    protected interface JavascriptProcessor {
        @JavascriptInterface
        void processHTML(String html);
    }
}