package io.github.hendraanggrian.webreader;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class HostUnresolvedException extends Exception {

    public HostUnresolvedException(String url) {
        super("could not connect to " + url);
    }
}