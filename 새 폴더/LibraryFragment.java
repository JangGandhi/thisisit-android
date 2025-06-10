package com.example.thisisit;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LibraryFragment extends Fragment {

    private TextView textNormal;
    private TextView textColored;
    private TextView textFromFragment6;

    public LibraryFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        textNormal = view.findViewById(R.id.text_sdcard_normal);
        textColored = view.findViewById(R.id.text_sdcard_colored);
        textFromFragment6 = view.findViewById(R.id.text_sdcard_fragment6);

        loadSDCardStatusFragment8();
        loadSDCardStatusFragment6();

        // 설명 다이얼로그
        textNormal.setOnClickListener(v -> showDialog("일반 SD카드란?", "기사를 읽고 얻은 보상입니다."));
        textColored.setOnClickListener(v -> showDialog("색상 SD카드란?", "특정 미션 성공 시 얻는 보상입니다."));
        textFromFragment6.setOnClickListener(v -> showDialog("Fragment6 SD카드란?", "Fragment6에서 획득한 SD카드 수입니다."));

        return view;
    }

    private void loadSDCardStatusFragment8() {
        int normalCount = 0;
        int colorCount = 0;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(requireContext().openFileInput("fragment8_sdcard.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("normal")) normalCount++;
                else if (line.trim().equals("color")) colorCount++;
            }
        } catch (Exception e) {
            // 무시하고 기본값 유지
        }

        textNormal.setText("일반 SD카드: " + normalCount + "개");
        textColored.setText("색상 SD카드: " + colorCount + "개");
    }

    private void loadSDCardStatusFragment6() {
        int normalCount = 0;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(requireContext().openFileInput("fragment6_sdcard.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("normal")) normalCount++;
            }
        } catch (Exception e) {
            // 무시
        }

        textFromFragment6.setText("Fragment6 SD카드: " + normalCount + "개");
    }

    private void showDialog(String title, String message) {
        new AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("확인", null)
                .show();
    }
}
