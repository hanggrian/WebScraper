package io.github.hendraanggrian.websnatch;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class TimeoutException extends RuntimeException {
    public TimeoutException(int timeout) {
        super("Loading webpage timeout at " + timeout + "ms");
    }
}