package com.hendraanggrian.websnatchsample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hendraanggrian on 07/01/16.
 */
public class FragmentWebSnatch extends Fragment {

    @Bind(R.id.textView) TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_websnatch, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void load(String html) {
        textView.setText(html);
    }
}