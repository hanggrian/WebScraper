package io.github.hendraanggrian.webreader;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hendraanggrian on 02-Jul-16.
 */
public class WebSource {

    private String shortUrl;
    private String longUrl;
    private String source;

    public WebSource(String shortUrl) throws IOException {
        this.shortUrl = shortUrl;

        // get extended url
        URLConnection conn = new URL(shortUrl).openConnection();
        conn.getHeaderFields();
        this.longUrl = conn.getURL().toString();
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public List<String> getUrls() {
        return Arrays.asList(shortUrl, longUrl);
    }

    public String getSource() {
        return source;
    }

    protected void setSource(String source) {
        this.source = source;
    }
}