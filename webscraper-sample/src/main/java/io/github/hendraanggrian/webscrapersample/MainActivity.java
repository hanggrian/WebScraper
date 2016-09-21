package io.github.hendraanggrian.webscrapersample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.hendraanggrian.lazyandroid.Lazy;
import io.github.hendraanggrian.lazyandroid.intent.BundleBuilder;
import io.github.hendraanggrian.webscraper.WebScraper;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.editText) EditText editText;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.webScraper) WebScraper webScraper;
    @BindView(R.id.floatingActionButton) FloatingActionButton floatingActionButton;

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Lazy.init(this);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        editText.setSelection(editText.getText().length());
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(this);

        webScraper.callback(new WebScraper.SimpleCallback() {
            @Override
            public void onStarted(WebScraper scraper) {
                menu.findItem(R.id.item).setIcon(R.drawable.ic_stop);
                progressBar.setProgress(0);
                floatingActionButton.hide();
            }

            @Override
            public void onProgress(WebScraper scraper, int progress) {
                progressBar.setProgress(progress);
            }

            @Override
            public void onSuccess(WebScraper scraper, final String html) {
                menu.findItem(R.id.item).setIcon(R.drawable.ic_load);
                floatingActionButton.show();
                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, ViewActivity.class).putExtras(new BundleBuilder()
                                .putString(R.string.extra_title, webScraper.getTitle())
                                .putString(R.string.extra_subtitle, webScraper.getUrl())
                                .putString(R.string.extra_content, html)
                                .build()));
                    }
                });
            }

            @Override
            public void onNoInternet(WebScraper scraper) {
                menu.findItem(R.id.item).setIcon(R.drawable.ic_load);
                Toast.makeText(MainActivity.this, "No internet!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        webScraper.retry();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item:
                if (item.getIcon().getConstantState().equals(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_load).getConstantState())) {
                    webScraper.loadUrl(editText.getText().toString());
                    if (getCurrentFocus() == editText)
                        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText.getWindowToken(), 0);
                } else {
                    webScraper.stop();
                    menu.findItem(R.id.item).setIcon(R.drawable.ic_load);
                }
                return true;
            case R.id.item_TimeoutNone:
                webScraper.clearTimeout();
                return true;
            case R.id.item_Timeout5s:
                webScraper.timeout(2000, new WebScraper.OnTimeoutListener() {
                    @Override
                    public void onTimeout(WebScraper webScraper) {
                        webScraper.retry();
                        Toast.makeText(MainActivity.this, "timeout", Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            case R.id.item_Timeout15s:
                webScraper.timeout(15000, new WebScraper.OnTimeoutListener() {
                    @Override
                    public void onTimeout(WebScraper webScraper) {
                        Toast.makeText(MainActivity.this, "timeout", Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            case R.id.item_Timeout30s:
                webScraper.timeout(30000, new WebScraper.OnTimeoutListener() {
                    @Override
                    public void onTimeout(WebScraper webScraper) {
                        Toast.makeText(MainActivity.this, "timeout", Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}