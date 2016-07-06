package io.github.hendraanggrian.webreadersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.hendraanggrian.extracollector.ExtraCollector;
import io.github.hendraanggrian.extracollector.annotation.Extra;

public class ViewActivity extends AppCompatActivity {

    @Extra(R.string.extra_title) String extra_title;
    @Extra(R.string.extra_subtitle) String extra_subtitle;
    @Extra(R.string.extra_content) String extra_content;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.textView) TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ExtraCollector.onCreate(this);

        getSupportActionBar().setTitle(extra_title);
        getSupportActionBar().setSubtitle(extra_subtitle);
        textView.setText(extra_content);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}