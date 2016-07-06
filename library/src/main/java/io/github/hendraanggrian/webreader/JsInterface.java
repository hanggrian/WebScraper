package io.github.hendraanggrian.webreader;

import android.webkit.JavascriptInterface;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public interface JsInterface {

    @JavascriptInterface
    void processHTML(String html);
}