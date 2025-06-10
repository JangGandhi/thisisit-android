package com.example.thisisit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView textGreeting = view.findViewById(R.id.text_greeting);
        TextView textArticles = view.findViewById(R.id.text_articles);
        TextView textSdCard = view.findViewById(R.id.text_sdcard);
        TextView textColoredSdCard = view.findViewById(R.id.text_colored_sdcard);

        textGreeting.setText("반가워요! 박규선님");
        textArticles.setText("읽은 기사: 1개");  // TODO: 실제 기사 수 반영하려면 파일 또는 SharedPreferences 연동 필요

        int defaultSDCount = 0;
        int shinySDCount = 0;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(requireContext().openFileInput("fragment8_sdcard.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("normal")) defaultSDCount++;
                else if (line.trim().equals("color")) shinySDCount++;
            }
        } catch (Exception e) {
            // 파일 없으면 0 유지
        }

        textSdCard.setText("획득한 SD카드: " + defaultSDCount + "개");
        textColoredSdCard.setText("획득한 색이 다른 SD카드: " + shinySDCount + "개");

        return view;
    }
}
