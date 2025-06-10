package com.example.thisisit;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

// MainActivity2는 앱에서 ViewPager2로 프래그먼트를 좌우 스와이프 전환하는 역할을 함
public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager; // ViewPager2 객체 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_main.xml을 레이아웃으로 설정
        setContentView(R.layout.activity_main);

        // XML 레이아웃에서 ID가 viewPager인 ViewPager2를 찾아서 변수에 연결
        viewPager = findViewById(R.id.viewPager);

        // ViewPager2에 어댑터를 설정 (Fragment를 관리하는 어댑터)
        viewPager.setAdapter(new FragmentStateAdapter(this) {

            // position에 따라 Fragment를 반환
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                // position이 0이면 LibraryFragment, 아니면 ProfileFragment 반환
                if (position == 0) return new LibraryFragment();
                else return new ProfileFragment();
            }

            // 전체 Fragment 개수는 2개
            @Override
            public int getItemCount() {
                return 2;
            }
        });
    }
}
