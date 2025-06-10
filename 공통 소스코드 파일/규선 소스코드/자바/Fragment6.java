package com.example.thisisit_newandquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.FileOutputStream;
import java.io.IOException;

public class Fragment6 extends Fragment {

    private String[] questionsSet1 = {
            "비트코인은 중앙 기관의 통제를 받아 거래가 이루어진다.",
            "비트코인은 블록체인 기술을 기반으로 한다.",
            "비트코인 거래는 신원 확인이 반드시 필요하다.",
            "비트코인은 채굴 과정을 통해 발행된다.",
            "비트코인은 정부가 발행하는 실물 화폐이다."
    };
    private boolean[] answersSet1 = { false, true, false, true, false };

    private String[] questionsSet2 = {
            "블록체인은 누구나 열람할 수 있는 공공장부다.",
            "비트코인 주소는 사람 이름과 연결된다.",
            "채굴자는 수학 문제를 해결하여 블록을 생성한다.",
            "비트코인은 무제한으로 발행 가능하다.",
            "공개키와 개인키는 암호화 보안을 위해 사용된다."
    };
    private boolean[] answersSet2 = { true, false, true, false, true };

    private String[] questions;
    private boolean[] answers;
    private int currentIndex = 0;
    private int correctCount = 0;

    private boolean hasRetried = false;
    private boolean resultUsed = false;

    private TextView questionText, progressText;
    private TextView btnO, btnX;
    private Button btnRetry, btnResult;
    private LinearLayout resultButtons;

    private int calculatedChance = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_6, container, false);

        progressText = view.findViewById(R.id.textView);
        questionText = view.findViewById(R.id.textView1);
        btnO = view.findViewById(R.id.textView2);
        btnX = view.findViewById(R.id.textView3);
        btnRetry = view.findViewById(R.id.btn_retry);
        btnResult = view.findViewById(R.id.btn_result);
        resultButtons = view.findViewById(R.id.result_buttons);

        questions = questionsSet1;
        answers = answersSet1;
        updateQuestion();

        btnO.setOnClickListener(v -> checkAnswer(true));
        btnX.setOnClickListener(v -> checkAnswer(false));

        btnRetry.setOnClickListener(v -> {
            SharedPreferences prefs = requireContext().getSharedPreferences("quiz_data", Context.MODE_PRIVATE);
            boolean sdcardChecked = prefs.getBoolean("sdcard_checked", false);

            if (sdcardChecked && resultUsed) {
                Toast.makeText(getContext(), "이미 SD카드 결과를 확인했습니다. 재시도는 불가능합니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!hasRetried) {
                currentIndex = 0;
                correctCount = 0;
                questions = questionsSet2;
                answers = answersSet2;
                hasRetried = true;
                updateQuestion();
                resultButtons.setVisibility(View.GONE);
                Toast.makeText(getContext(), "문제가 새로 교체되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "재시도는 1번만 가능합니다.", Toast.LENGTH_SHORT).show();
            }
        });

        btnResult.setOnClickListener(v -> {
            if (resultUsed) {
                Toast.makeText(getContext(), "이미 결과를 확인했습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            resultUsed = true;

            SharedPreferences prefs = requireContext().getSharedPreferences("quiz_data", Context.MODE_PRIVATE);
            int chance = prefs.getInt("calculated_chance", 0);
            int random = (int)(Math.random() * 100) + 1;

            if (random <= chance) {
                Toast.makeText(getContext(), "SD카드를 획득했습니다!", Toast.LENGTH_LONG).show();

                // 내부 저장소 저장
                saveStatsToFile("fragment6_stats.txt", questions.length, correctCount);
                saveResultToFile("fragment6_sdcard.txt", "normal");
            } else {
                Toast.makeText(getContext(), "SD카드 획득에 실패했습니다.", Toast.LENGTH_LONG).show();
            }

            // 버튼을 눌렀다는 사실만 저장
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("sdcard_checked", true).apply();
        });

        return view;
    }

    private void checkAnswer(boolean userAnswer) {
        if (userAnswer == answers[currentIndex]) {
            correctCount++;
            Toast.makeText(getContext(), "정답!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "오답!", Toast.LENGTH_SHORT).show();
        }

        currentIndex++;
        if (currentIndex < questions.length) {
            updateQuestion();
        } else {
            calculatedChance = getChanceByCorrectCount(correctCount);

            SharedPreferences prefs = requireContext().getSharedPreferences("quiz_data", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("total", questions.length);
            editor.putInt("correct", correctCount);
            editor.putInt("calculated_chance", calculatedChance);
            editor.apply();

            btnResult.setText("SD카드 획득하기 (" + calculatedChance + "%)");
            resultButtons.setVisibility(View.VISIBLE);
        }
    }

    private void updateQuestion() {
        questionText.setText(questions[currentIndex]);
        progressText.setText((currentIndex + 1) + "/" + questions.length);
    }

    private int getChanceByCorrectCount(int correct) {
        switch (correct) {
            case 0: return 0;
            case 1: return 20;
            case 2: return 40;
            case 3: return 50;
            case 4: return 60;
            case 5: return 80;
            default: return 0;
        }
    }

    private void saveStatsToFile(String filename, int total, int correct) {
        String content = "총 문제 수: " + total + ", 정답 수: " + correct + "\n";
        try (FileOutputStream fos = requireContext().openFileOutput(filename, Context.MODE_APPEND)) {
            fos.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveResultToFile(String filename, String content) {
        try (FileOutputStream fos = requireContext().openFileOutput(filename, Context.MODE_APPEND)) {
            fos.write((content + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
