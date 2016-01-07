package com.hendraanggrian.websnatch;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class TimeoutException extends RuntimeException {
    public TimeoutException(String detailMessage) {
        super(detailMessage);
    }
}