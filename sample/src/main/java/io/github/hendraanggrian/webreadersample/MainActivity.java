package io.github.hendraanggrian.webreadersample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.hendraanggrian.extracollector.ExtraCollector;
import io.github.hendraanggrian.extracollector.Extras;
import io.github.hendraanggrian.webreader.WebReader;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.editText) EditText editText;
    @BindView(R.id.webReader) WebReader webReader;
    @BindView(R.id.floatingActionButton) FloatingActionButton floatingActionButton;

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
                        .addCallback(new WebReader.SimpleCallback() {
                            @Override
                            public void onStarted(WebReader reader) {
                                floatingActionButton.hide();
                            }

                            @Override
                            public void onSuccess(WebReader reader, final String html) {
                                floatingActionButton.show();
                                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Extras extras = new Extras()
                                                .putString(R.string.extra_title, webReader.getTitle())
                                                .putString(R.string.extra_subtitle, webReader.getUrl())
                                                .putString(R.string.extra_content, html);

                                        Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                                        intent.putExtras(extras.toBundle());
                                        startActivity(intent);
                                    }
                                });
                            }
                        }).loadUrl(editText.getText().toString());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}