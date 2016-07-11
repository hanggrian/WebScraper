package io.github.hendraanggrian.webreadersample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    private MenuItem item_Status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ExtraCollector.init(this);

        webReader
                .addCallback(new WebReader.SimpleCallback() {
                    @Override
                    public void onProgress(WebReader reader, int progress) {
                        item_Status.setTitle(String.valueOf(progress) + "%");
                        item_Status.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                return false;
                            }
                        });
                    }

                    @Override
                    public void onSuccess(WebReader reader, final String html) {
                        item_Status.setTitle("See HTML");
                        item_Status.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                Extras extras = new Extras()
                                        .putString(R.string.extra_title, webReader.getTitle())
                                        .putString(R.string.extra_subtitle, webReader.getUrl())
                                        .putString(R.string.extra_content, html);

                                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                                intent.putExtras(extras.toBundle());
                                startActivity(intent);
                                return true;
                            }
                        });
                    }

                    @Override
                    public void onError(WebReader reader, Exception exc) {
                        exc.printStackTrace();
                        item_Status.setTitle("Error");
                        item_Status.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                return false;
                            }
                        });
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        item_Status = menu.findItem(R.id.item_Status);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_Go:
                webReader.loadUrl(editText.getText().toString());
                return true;
            case R.id.item_Stop:
                webReader.stop();
                return true;
            case R.id.item_Retry:
                webReader.retry();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}