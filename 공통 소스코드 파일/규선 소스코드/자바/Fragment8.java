package com.example.thisisit_newandquiz;

import android.content.Context;
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

public class Fragment8 extends Fragment {

    private String[][] questionSets = {
            {
                    "블록체인의 트랜잭션은 삭제가 가능하다.",
                    "비트코인의 채굴 난이도는 자동으로 조정된다.",
                    "공개키는 비밀스럽게 유지되어야 한다.",
                    "블록체인은 오직 금융 산업에만 사용된다.",
                    "비트코인의 트랜잭션은 블록에 포함되기 전까지 확인되지 않는다."
            },
            {
                    "블록체인은 중앙 서버에서 관리된다.",
                    "비트코인은 익명성을 완전히 보장하지 않는다.",
                    "채굴자는 블록 생성 시 보상을 받는다.",
                    "블록체인은 거래 기록의 변조를 막는다.",
                    "비트코인의 발행량은 무제한이다."
            },
            {
                    "비트코인은 실시간으로 모든 거래를 보장한다.",
                    "블록체인은 모든 블록에 이전 해시를 포함한다.",
                    "공개키는 거래 인증에 사용된다.",
                    "비트코인은 기존 은행 시스템에 의존한다.",
                    "블록체인은 탈중앙 시스템이다."
            }
    };

    private boolean[][] answerSets = {
            { false, true, false, false, true },
            { false, true, true, true, false },
            { false, true, true, false, true }
    };

    private int currentSetIndex = 0;
    private int currentIndex = 0;
    private int correctCount = 0;
    private int usedRetry = 0;

    private TextView questionText, progressText;
    private TextView btnO, btnX;
    private Button btnRetry, btnResult;
    private LinearLayout resultButtons;

    private boolean resultUsed = false;
    private int maxRetry = 1; // 보너스가 있으면 2로 설정 가능
    private boolean useColoredSDCard = false; // false면 일반, true면 빛나는

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_8, container, false);

        progressText = view.findViewById(R.id.textView);
        questionText = view.findViewById(R.id.textView_question);
        btnO = view.findViewById(R.id.btn_o);
        btnX = view.findViewById(R.id.btn_x);
        btnRetry = view.findViewById(R.id.btn_retry);
        btnResult = view.findViewById(R.id.btn_result);
        resultButtons = view.findViewById(R.id.result_buttons);

        updateQuestion();

        btnO.setOnClickListener(v -> checkAnswer(true));
        btnX.setOnClickListener(v -> checkAnswer(false));

        btnRetry.setOnClickListener(v -> {
            if (resultUsed) {
                Toast.makeText(getContext(), "SD카드 결과 확인 후에는 재시도할 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (usedRetry < maxRetry && currentSetIndex < questionSets.length - 1) {
                usedRetry++;
                currentSetIndex++;
                currentIndex = 0;
                correctCount = 0;
                updateQuestion();
                resultButtons.setVisibility(View.GONE);
                Toast.makeText(getContext(), "새로운 문제 세트로 교체되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "재시도 횟수를 초과했습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        btnResult.setOnClickListener(v -> {
            if (resultUsed) {
                Toast.makeText(getContext(), "이미 결과를 확인했습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            resultUsed = true;

            int chance = getChanceByCorrectCount(correctCount);
            int random = (int)(Math.random() * 100) + 1;

            saveStatsToFile("fragment8_stats.txt", questionSets[currentSetIndex].length, correctCount);

            if (random <= chance) {
                String sdResult = useColoredSDCard ? "color" : "normal";
                saveResultToFile("fragment8_sdcard.txt", sdResult);

                String message = useColoredSDCard ? "빛나는 SD카드를 획득했습니다!" : "일반 SD카드를 획득했습니다!";
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "SD카드 획득에 실패했습니다.", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    private void checkAnswer(boolean userAnswer) {
        if (userAnswer == answerSets[currentSetIndex][currentIndex]) {
            correctCount++;
            Toast.makeText(getContext(), "정답!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "오답!", Toast.LENGTH_SHORT).show();
        }

        currentIndex++;
        if (currentIndex < questionSets[currentSetIndex].length) {
            updateQuestion();
        } else {
            btnResult.setText("SD카드 획득하기 (" + getChanceByCorrectCount(correctCount) + "%)");
            resultButtons.setVisibility(View.VISIBLE);
        }
    }

    private void updateQuestion() {
        questionText.setText(questionSets[currentSetIndex][currentIndex]);
        progressText.setText((currentIndex + 1) + "/" + questionSets[currentSetIndex].length);
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

    private void saveResultToFile(String filename, String content) {
        try {
            FileOutputStream fos = requireContext().openFileOutput(filename, Context.MODE_APPEND); // append 모드
            fos.write((content + "\n").getBytes()); // 줄 단위 저장
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void saveStatsToFile(String filename, int total, int correct) {
        String content = "총 문제 수: " + total + ", 정답 수: " + correct;
        try {
            FileOutputStream fos = requireContext().openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
