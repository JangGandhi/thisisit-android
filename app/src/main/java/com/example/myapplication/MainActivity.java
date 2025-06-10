package com.example.myapplication;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        TabLayout tabs = findViewById(R.id.tabs);
        String[] tabTitles = {"뉴스", "라이브러리", "프로필"};

        for (String title : tabTitles) {
            TextView tabTextView = new TextView(this);
            tabTextView.setText(title);
            tabTextView.setTextSize(14);
            tabTextView.setGravity(Gravity.CENTER);

            TabLayout.Tab tab = tabs.newTab();
            tab.setCustomView(tabTextView);
            tabs.addTab(tab);
        }

        tabs.getTabAt(0).select();
        TextView selectedTextView = (TextView) tabs.getTabAt(0).getCustomView();
        if (selectedTextView != null) {
            selectedTextView.setTextSize(18);
            selectedTextView.setTypeface(null, Typeface.BOLD);
        }

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView textView = (TextView) tab.getCustomView();
                if (textView != null) {
                    textView.setTextSize(18);
                    textView.setTypeface(null, Typeface.BOLD);
                }

                int position = tab.getPosition();
                Fragment selected = null;
                if (position == 0) {
                    selected = fragment1;
                } else if (position == 1) {
                    selected = fragment2;
                } else if (position == 2) {
                    selected = fragment3;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView textView = (TextView) tab.getCustomView();
                if (textView != null) {
                    textView.setTextSize(14);
                    textView.setTypeface(null, Typeface.NORMAL);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                TextView textView = (TextView) tab.getCustomView();
                if (textView != null) {
                    textView.setTextSize(18);
                    textView.setTypeface(null, Typeface.BOLD);
                }
            }
        });

        copyAssetToInternalStorage("News.txt");
        copyAssetToInternalStorage("Library.txt");
        copyAssetToInternalStorage("Profile.txt");
    }

    private void copyAssetToInternalStorage(String fileName) {
        File destFile = new File(getFilesDir(), fileName);
        if (!destFile.exists()) {
            try (InputStream is = getAssets().open(fileName); OutputStream os = new FileOutputStream(destFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}