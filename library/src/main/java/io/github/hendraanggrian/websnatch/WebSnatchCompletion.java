package io.github.hendraanggrian.websnatch;

/**
 * Created by hendraanggrian on 3/28/16.
 */
public interface WebSnatchCompletion {

    void onStarted(WebSnatch webSnatch);

    void onProgress(WebSnatch webSnatch, int progress);

    void onSuccess(WebSnatch webSnatch, String html);

    void onError(WebSnatch webSnatch, Exception exc);

    abstract class Simple implements WebSnatchCompletion {
        @Override
        public void onStarted(WebSnatch webSnatch) {
        }

        @Override
        public void onProgress(WebSnatch webSnatch, int progress) {
        }

        @Override
        public void onSuccess(WebSnatch webSnatch, String html) {
        }

        @Override
        public void onError(WebSnatch webSnatch, Exception exc) {
        }
    }

}