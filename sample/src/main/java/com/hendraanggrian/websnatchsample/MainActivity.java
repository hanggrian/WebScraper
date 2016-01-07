package com.hendraanggrian.websnatchsample;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hendraanggrian.websnatch.UserAgent;
import com.hendraanggrian.websnatch.WebSnatch;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.editText) EditText editText;
    @Bind(R.id.tabLayout) TabLayout tabLayout;
    @Bind(R.id.viewPager) ViewPager viewPager;

    private FragmentWebView fragmentWebView = new FragmentWebView();
    private FragmentWebSnatch fragmentWebSnatch = new FragmentWebSnatch();

    private String userAgent = "";
    private int timeout = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.userAgent:
                int preposition;
                if (userAgent.equals(UserAgent.MOBILE))
                    preposition = 1;
                else if (userAgent.equals(UserAgent.DESKTOP))
                    preposition = 2;
                else
                    preposition = 0;

                new MaterialDialog.Builder(this)
                        .title("User Agent")
                        .items(new String[]{"DEFAULT", "MOBILE", "DESKTOP"})
                        .itemsCallbackSingleChoice(preposition, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                switch (which) {
                                    case 0:
                                        userAgent = "";
                                        return true;
                                    case 1:
                                        userAgent = UserAgent.MOBILE;
                                        return true;
                                    case 2:
                                        userAgent = UserAgent.DESKTOP;
                                        return true;
                                    default:
                                        return true;
                                }
                            }
                        })
                        .negativeText(android.R.string.cancel)
                        .positiveText(android.R.string.ok)
                        .show();
                return true;
            case R.id.timeout:
                String prefill;
                if (timeout == -1)
                    prefill = "";
                else
                    prefill = String.valueOf(timeout);

                new MaterialDialog.Builder(this)
                        .title("Timeout")
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .negativeText(android.R.string.cancel)
                        .positiveText(android.R.string.ok)
                        .input("Timeout", prefill, false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                if (input.toString().isEmpty())
                                    return;

                                timeout = Integer.valueOf(input.toString());
                            }
                        }).show();
                return true;
            case R.id.load:
                fragmentWebView.load(userAgent, editText.getText().toString());

                WebSnatch webSnatch = new WebSnatch(this);
                if (!userAgent.isEmpty())
                    webSnatch.setUserAgent(userAgent);
                if (timeout != -1)
                    webSnatch.setTimeout(timeout);
                webSnatch.load(editText.getText().toString(), new WebSnatch.Completion() {
                    @Override
                    public void onProgress(int progress) {

                    }

                    @Override
                    public void onSuccess(final String html) {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fragmentWebSnatch.load(html);
                            }
                        });
                    }

                    @Override
                    public void onError(Exception exc) {
                        exc.printStackTrace();
                    }
                });
                /*new WebSnatch(this)
                        .load(editText.getText().toString(), new WebSnatch.Completion() {
                            @Override
                            public void onProgress(int progress) {

                            }

                            @Override
                            public void onSuccess(String html) {
                                for (int i = 0; i < html.split("\n").length; i++)
                                    Log.w(String.valueOf(i), html.split("\n")[i]);
                            }

                            @Override
                            public void onError(Exception exc) {
                                exc.printStackTrace();
                            }
                        });*/
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) return fragmentWebView;
            else return fragmentWebSnatch;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) return "WebView";
            else return "WebSnatch";
        }
    }
}