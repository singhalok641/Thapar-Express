package com.exun.thaparexpress.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.TextView;

import com.exun.thaparexpress.R;

/**
 * Created by root on 21/1/16.
 */
public class FullBlog extends AppCompatActivity {

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_blog_full);

        TextView blogText = (TextView) findViewById(R.id.blogText);

        Bundle m = getIntent().getExtras();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(m.getString("name"));
        getSupportActionBar().setSubtitle(m.getString("date"));

        // Blog text
        blogText.setText(Html.fromHtml(m.getString("text")));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
