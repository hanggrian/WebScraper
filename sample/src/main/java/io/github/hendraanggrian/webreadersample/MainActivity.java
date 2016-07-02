package io.github.hendraanggrian.webreadersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.hendraanggrian.webreader.WebReader;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.editText) EditText editText;
    @BindView(R.id.textView) TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        new WebReader(this, "http://www.google.com")
                .read(new WebReader.Completion() {
                    @Override
                    public void onStarted() {

                    }

                    @Override
                    public void onProgress(int progress) {

                    }

                    @Override
                    public void onSuccess(String source) {

                    }

                    @Override
                    public void onError(Exception exc) {

                    }
                });
    }
}