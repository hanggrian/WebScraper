WebReader
=========

HTML and JavaScript source reader for Android.

```java
new WebReader(this)
    .addCallback(new WebReader.Callback() {
        @Override
        public void onStarted(WebReader reader) {
        
        }
        
        @Override
        public void onProgress(WebReader reader, int progress) {
        
        }
        
        @Override
        public void onRequest(WebReader reader, String url) {
            
        }
        
        @Override
        public void onSuccess(WebReader reader, String html) {
            
        }
        
        @Override
        public void onError(WebReader reader, Exception exc) {
        
        }
    }).loadUrl("http://www.google.com");
```


Download
--------

Download from Gradle line

```gradle
compile 'io.github.hendraanggrian:webreader:0.5.0'
```


License
--------

    The MIT License (MIT)

    Copyright (c) 2015 Hendra Anggrianto Wijaya
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.
