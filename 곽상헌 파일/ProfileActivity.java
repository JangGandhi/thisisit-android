package com.example.thisisit;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView textGreeting, textArticles, textSdCard, textColoredSdCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textGreeting = findViewById(R.id.text_greeting);
        textArticles = findViewById(R.id.text_articles);
        textSdCard = findViewById(R.id.text_sdcard);
        textColoredSdCard = findViewById(R.id.text_colored_sdcard);

        // 예시 데이터
        String userName = "박규선";
        int articlesRead = 1;
        int sdCards = 1;
        int coloredSdCards = 0;

        updateProfile(userName, articlesRead, sdCards, coloredSdCards);
    }

    private void updateProfile(String name, int articles, int sd, int coloredSd) {
        textGreeting.setText("반가워요! " + name + "님");
        textArticles.setText("읽은 기사: " + articles + "개");
        textSdCard.setText("획득한 SD카드: " + sd + "개");
        textColoredSdCard.setText("획득한 색이 다른 SD카드: " + coloredSd + "개");
    }
}
