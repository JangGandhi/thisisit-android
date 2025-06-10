package com.example.thisisit_newandquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Fragment2 extends Fragment {

    private TextView textNormal;
    private TextView textColored;
    private TextView textFromFragment6;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        textNormal = view.findViewById(R.id.text_sdcard_normal);
        textColored = view.findViewById(R.id.text_sdcard_colored);
        textFromFragment6 = view.findViewById(R.id.text_sdcard_fragment6);

        loadSDCardStatusFragment8();
        loadSDCardStatusFragment6();

        return view;
    }

    private void loadSDCardStatusFragment8() {
        int normalCount = 0;
        int colorCount = 0;

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(requireContext().openFileInput("fragment8_sdcard.txt"))
            );
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("normal")) {
                    normalCount++;
                } else if (line.trim().equals("color")) {
                    colorCount++;
                }
            }
            reader.close();
        } catch (Exception e) {
            // 파일 없을 경우 기본값 유지
        }

        textNormal.setText("일반 SD카드: " + normalCount + "개");
        textColored.setText("색상 SD카드: " + colorCount + "개");
    }

    private void loadSDCardStatusFragment6() {
        int normalCount = 0;

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(requireContext().openFileInput("fragment6_sdcard.txt"))
            );
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("normal")) {
                    normalCount++;
                }
            }
            reader.close();
        } catch (Exception e) {
            // 파일 없을 경우 기본값 유지
        }

        textFromFragment6.setText("Fragment6 SD카드: " + normalCount + "개");
    }
}
