WebScraper
==========

HTML and JavaScript source scraper for Android.

```java
new WebScraper(this)
    .addCallback(new WebScraper.Callback() {
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
    }).loadUrl("http://www.google.com");
```


Download
--------

Download from Gradle line

```gradle
compile 'io.github.hendraanggrian:webscraper:0.0.1'
```
