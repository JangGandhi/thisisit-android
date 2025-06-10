package com.example.thisisit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

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
        textArticles.setText("읽은 기사: 1개");
        textSdCard.setText("획득한 SD카드: 1개");
        textColoredSdCard.setText("획득한 색이 다른 SD카드: 0개");

        return view;
    }
}
