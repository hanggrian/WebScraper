[![license](https://img.shields.io/github/license/hendraanggrian/webscraper)](http://www.apache.org/licenses/LICENSE-2.0)

### This library is no longer maintained.

WebScraper
==========
HTML and JavaScript source scraper for Android.

```java
new WebScraper(activity)
    .timeout(5000, new WebScraper.OnTimeoutListener() { // optional
        @Override
        public void onTimeout(WebScraper webScraper) {

        }
    })
    .callback(new WebScraper.Callback() {
        @Override
        public void onStarted(WebScraper webScraper) {

        }

        @Override
        public void onProgress(WebScraper webScraper, int progress) {

        }

        @Override
        public void onRequest(WebScraper webScraper, String url) {

        }

        @Override
        public void onSuccess(WebScraper webScraper, String html) {

        }

        @Override
        public void onError(WebScraper webScraper) {

        }
    })
    .loadUrl("http://www.google.com");
```


Download
--------

```gradle
compile 'io.github.hendraanggrian:webscraper:0.1.0'
```
