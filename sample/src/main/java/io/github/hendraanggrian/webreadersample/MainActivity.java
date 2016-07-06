package io.github.hendraanggrian.webreadersample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.hendraanggrian.extracollector.ExtraCollector;
import io.github.hendraanggrian.extracollector.Extras;
import io.github.hendraanggrian.webreader.WebReader;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.layout) LinearLayout layout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.editText) EditText editText;
    @BindView(R.id.webReader) WebReader webReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ExtraCollector.init(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_Go:
                webReader.removeAllCallbacks()
                        .fetchLongUrl(true)
                        .addCallback(new WebReader.SimpleCallback() {
                            @Override
                            public void onProgress(WebReader reader, int progress) {
                                Snackbar.make(layout, "Loading... " + progress + "%", Snackbar.LENGTH_INDEFINITE).setAction("Stop", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        webReader.stop();
                                    }
                                }).show();
                            }

                            @Override
                            public void onSuccess(WebReader reader, final String html) {
                                Snackbar.make(layout, "Completed! ", Snackbar.LENGTH_INDEFINITE).setAction("See HTML", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Extras extras = new Extras()
                                                .putString(R.string.extra_title, webReader.getTitle())
                                                .putString(R.string.extra_subtitle, webReader.getUrl())
                                                .putString(R.string.extra_content, html);

                                        Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                                        intent.putExtras(extras.toBundle());
                                        startActivity(intent);
                                    }
                                }).show();
                            }

                            @Override
                            public void onError(WebReader reader, Exception exc) {
                                exc.printStackTrace();
                                Snackbar.make(layout, "Error occured.", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        webReader.retry();
                                    }
                                }).show();
                            }
                        }).loadUrl(editText.getText().toString());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}