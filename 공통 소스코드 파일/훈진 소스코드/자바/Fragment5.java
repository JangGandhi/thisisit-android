package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment5#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment5 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String difficulty;
    private Switch switch2;
    private TextView upChance;

    public Fragment5() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment5.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment5 newInstance(String param1, String param2) {
        Fragment5 fragment = new Fragment5();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_5, container, false);
        upChance = view.findViewById(R.id.up_chance);
        difficulty = "hard";

        switch2 = view.findViewById(R.id.switch2);
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                if (!isChecked) {
                    Fragment fragment4 = new Fragment4();
                    transaction.replace(R.id.container, fragment4);
                }
                transaction.commit();
            }
        });
        switch2.setChecked(true);
        showButton("BITCOIN");
        return view;
    }

    private void showButton(String news) {
        if (questionAvailableCheck()) {
            upChance.setAlpha(0.4f);
            new CountDownTimer(10000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    int secondsRemaining = (int) (millisUntilFinished / 1000);
                    String timeText = String.format("00:00:%02d", secondsRemaining);
                    upChance.setText(timeText);
                }

                @Override
                public void onFinish() {

                    upChance.setText("확률 UP! SD카드 획득하기");
                    upChance.setClickable(true);
                    upChance.setAlpha(1.0f);

                    ScaleAnimation scale = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    scale.setDuration(150);
                    scale.setRepeatCount(1);
                    scale.setRepeatMode(Animation.REVERSE);
                    upChance.startAnimation(scale);
                    upChance.setOnClickListener(v -> {
                        goFragment6(difficulty);
                    });
                }
            }.start();
        } else if (cardAcquiredCheck(news)){
            upChance.setClickable(false);
            upChance.setBackgroundResource(R.drawable.sd_disable_background);
            upChance.setText("이미 획득한 SD카드");
        } else {
            upChance.setClickable(false);
            upChance.setBackgroundResource(R.drawable.sd_disable_background);
            upChance.setText("오늘은 더 이상 도전할 수 없어요!");
        }
    }

    private Boolean cardAcquiredCheck(String news) {
        File file = new File(requireContext().getFilesDir(), "Library.txt");
        String line;
        boolean flag = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts[0].equalsIgnoreCase(news+"_SDCARD_ACQUIRED") && Boolean.parseBoolean(parts[1].trim())) {
                    flag = true;
                    continue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    private Boolean questionAvailableCheck() {
        File file = new File(requireContext().getFilesDir(), "News.txt");
        String line;
        boolean flag = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts[0].equalsIgnoreCase("BITCOIN_SDCARD_UNLOCKED") && Boolean.parseBoolean(parts[1].trim())) {
                    flag = true;
                    continue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    private void questionAvailableReturn() {
        File file = new File(requireContext().getFilesDir(), "News.txt");
        String line;
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts[0].equalsIgnoreCase("BITCOIN_SDCARD_UNLOCKED") && Boolean.parseBoolean(parts[1].trim())) {
                    lines.add(parts[0] + "=false");
                    continue;
                } else lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void goFragment6(String difficulty) {
        questionAvailableReturn();
        Fragment6 fragment6 = new Fragment6();

        Bundle bundle = new Bundle();
        bundle.putString("difficulty", difficulty);

        fragment6.setArguments(bundle);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment6);
        transaction.commit();
    }
}