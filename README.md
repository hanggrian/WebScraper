WebScraper
==========

HTML and JavaScript source scraper for Android.

```java
new WebScraper(this)
    .setTimeout(5000, new WebScraper.OnTimeoutListener() { // optional
        @Override
        public void onTimeout(WebScraper scraper) {

        }
    })
    .loadUrl("http://www.google.com", new WebScraper.Callback() {
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
    });
```


Download
--------

Download from Gradle line

```gradle
compile 'io.github.hendraanggrian:webscraper:0.0.2'
```
