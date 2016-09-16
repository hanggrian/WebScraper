package io.github.hendraanggrian.webscrapersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.hendraanggrian.lazy.intent.ExtraRes;
import io.github.hendraanggrian.lazy.intent.LazyIntent;

public class ViewActivity extends AppCompatActivity {

    @ExtraRes(R.string.extra_title) String extra_title;
    @ExtraRes(R.string.extra_subtitle) String extra_subtitle;
    @ExtraRes(R.string.extra_content) String extra_content;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.textView) TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        LazyIntent.collect(this);

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