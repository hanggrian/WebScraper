package io.github.hendraanggrian.webscrapersample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.hendraanggrian.lazy.Lazy;
import io.github.hendraanggrian.lazy.intent.BundleBuilder;
import io.github.hendraanggrian.webscraper.WebScraper;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.layout) LinearLayout layout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.editText) EditText editText;
    @BindView(R.id.webReader)
    WebScraper webScraper;

    private MenuItem item_Status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        Lazy.init(this);

        webScraper.addCallback(new WebScraper.SimpleCallback() {
            @Override
            public void onProgress(WebScraper scraper, int progress) {
                item_Status.setTitle(String.valueOf(progress) + "%");
                item_Status.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        return false;
                    }
                });
            }

            @Override
            public void onSuccess(WebScraper scraper, final String html) {
                item_Status.setTitle("See HTML");
                item_Status.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Bundle bundle = new BundleBuilder()
                                .putString(R.string.extra_title, webScraper.getTitle())
                                .putString(R.string.extra_subtitle, webScraper.getUrl())
                                .putString(R.string.extra_content, html)
                                .build();

                        startActivity(new Intent(MainActivity.this, ViewActivity.class).putExtras(bundle));
                        return true;
                    }
                });
            }

            @Override
            public void onError(WebScraper scraper, Exception exc) {
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
                webScraper.loadUrl(editText.getText().toString());
                return true;
            case R.id.item_Stop:
                webScraper.stop();
                return true;
            case R.id.item_Retry:
                webScraper.retry();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}