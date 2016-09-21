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
        public void onNoInternet(WebScraper webScraper) {

        }
    })
    .loadUrl("http://www.google.com");
```

Add several permissions to AndroidManifest.xml
```xml
<uses-permission android:name="android.permission.INTERNET" /> 
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```


Download
--------

```gradle
compile 'io.github.hendraanggrian:webscraper:0.0.9'
```
