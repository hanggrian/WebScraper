package io.github.hendraanggrian.webscraper.internal;

import android.webkit.JavascriptInterface;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public interface WebScraperInterface {

    String NAME = "HTMLOUT";
    String PROCESS_URL = "javascript:window.HTMLOUT.processHTML(document.getElementsByTagName('html')[0].innerHTML);";

    @JavascriptInterface
    void processHTML(String html);
}