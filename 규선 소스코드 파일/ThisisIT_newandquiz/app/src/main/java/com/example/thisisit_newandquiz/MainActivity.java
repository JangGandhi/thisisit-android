package com.example.thisisit_newandquiz;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    TextView tabNews, tabLibrary, tabProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 탭 참조 연결
        tabNews = findViewById(R.id.tab_news);
        tabLibrary = findViewById(R.id.tab_library);
        tabProfile = findViewById(R.id.tab_profile);

        // 앱 시작 시: 뉴스 프래그먼트 표시
        switchFragment(new Fragment1());

        // 모든 탭 텍스트를 검정색으로 초기화
        tabNews.setTextColor(Color.BLACK);
        tabLibrary.setTextColor(Color.BLACK);
        tabProfile.setTextColor(Color.BLACK);

        // 탭 클릭 시 프래그먼트 전환 + 탭 강조 변경
        tabNews.setOnClickListener(v -> {
            switchFragment(new Fragment1());
            highlightTab(tabNews);
        });

        tabLibrary.setOnClickListener(v -> {
            switchFragment(new Fragment2());
            highlightTab(tabLibrary);
        });

        tabProfile.setOnClickListener(v -> {
            switchFragment(new Fragment3());
            highlightTab(tabProfile);
        });
    }

    // 프래그먼트를 전환하는 함수
    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    // 선택된 탭은 검정색, 나머지는 회색 처리
    private void highlightTab(TextView selectedTab) {
        tabNews.setTextColor(Color.parseColor("#AAAAAA"));
        tabLibrary.setTextColor(Color.parseColor("#AAAAAA"));
        tabProfile.setTextColor(Color.parseColor("#AAAAAA"));

        selectedTab.setTextColor(Color.BLACK);
    }
}
