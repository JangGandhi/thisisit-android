package com.example.thisisit;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

// 사용자의 프로필 정보를 보여주는 액티비티
public class ProfileActivity extends AppCompatActivity {

    // 텍스트뷰 선언: 인사말, 기사 수, SD카드 수, 색 다른 SD카드 수
    private TextView textGreeting, textArticles, textSdCard, textColoredSdCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_profile.xml을 UI로 설정
        setContentView(R.layout.activity_profile);

        // 레이아웃에서 텍스트뷰들을 찾아서 변수에 연결
        textGreeting = findViewById(R.id.text_greeting);
        textArticles = findViewById(R.id.text_articles);
        textSdCard = findViewById(R.id.text_sdcard);
        textColoredSdCard = findViewById(R.id.text_colored_sdcard);

        // 예시 사용자 데이터 설정
        String userName = "박규선";
        int articlesRead = 1;
        int sdCards = 1;
        int coloredSdCards = 0;

        // 위 데이터를 UI에 반영
        updateProfile(userName, articlesRead, sdCards, coloredSdCards);
    }

    // 사용자 정보를 기반으로 텍스트뷰 업데이트하는 메서드
    private void updateProfile(String name, int articles, int sd, int coloredSd) {
        textGreeting.setText("반가워요! " + name + "님");
        textArticles.setText("읽은 기사: " + articles + "개");
        textSdCard.setText("획득한 SD카드: " + sd + "개");
        textColoredSdCard.setText("획득한 색이 다른 SD카드: " + coloredSd + "개");
    }
}
