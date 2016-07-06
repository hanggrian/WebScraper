package io.github.hendraanggrian.webreader.internal;

import android.webkit.JavascriptInterface;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public interface WebReaderInterface {

    String NAME = "HTMLOUT";
    String PROCESS_URL = "javascript:window.HTMLOUT.processHTML(document.getElementsByTagName('html')[0].innerHTML);";

    @JavascriptInterface
    void processHTML(String html);
}