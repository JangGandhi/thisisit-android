package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment6#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment6 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String target;
    private ArrayList<String> Question = new ArrayList<>();
    private ArrayList<Boolean> Answer = new ArrayList<>();

    private ArrayList<Integer> numbers = new ArrayList<>();

    private int currentIndex;
    private double dropRate;
    private TextView textView;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private LinearLayout linearLayout;

    public Fragment6() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment6.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment6 newInstance(String param1, String param2) {
        Fragment6 fragment = new Fragment6();
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
        if (getArguments() != null) {
            target = getArguments().getString("difficulty");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_6, container, false);
        textView = view.findViewById(R.id.textView);
        textView1 = view.findViewById(R.id.textView1);
        textView2 = view.findViewById(R.id.textView2);
        textView3 = view.findViewById(R.id.textView3);
        linearLayout = view.findViewById(R.id.layout);

        loadQuestions(target);
        startQuiz();
        textView2.setOnClickListener(v -> handleAnswer(true));
        textView3.setOnClickListener(v -> handleAnswer(false));
        return view;
    }

    private void handleAnswer(boolean userAnswer) {
        textView2.setEnabled(false);
        textView3.setEnabled(false);

        updateTried();
        adjustDropRate(userAnswer);
        currentIndex++;

        if (currentIndex < Question.size()) {
            updateQuiz();
        }
    }

    private void loadQuestions(String target) {
        BufferedReader reader = null;
        String line;

        if (target.equalsIgnoreCase("easy")) {
            linearLayout.setBackgroundColor(Color.parseColor("#003850"));
        } else {
            linearLayout.setBackgroundColor(Color.parseColor("#1C3800"));
        }


        try {
            InputStream is = requireContext().getAssets().open("Quiz.txt");
            reader = new BufferedReader(new InputStreamReader(is));

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 3) {
                    String unknownQuestion = parts[0].trim();
                    Boolean unknownAnswer = Boolean.parseBoolean(parts[1].trim());
                    String difficulty = parts[2].trim();
                    if (difficulty.equalsIgnoreCase(target)) {
                        Question.add(unknownQuestion);
                        Answer.add(unknownAnswer);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void startQuiz() {
        boolean stop = true;
        numbers.clear();
        for (int i = 0; i < 10; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        currentIndex = 0;
        dropRate = 0.2;
        updateQuiz();
    }

    private void updateQuiz() {
        if (currentIndex < 5) {
            textView.setText((currentIndex + 1) + "/5");
            textView1.setText(Question.get(numbers.get(currentIndex)));
            textView2.setEnabled(true);
            textView3.setEnabled(true);
        } else {
            resultQuiz();
            goFragment7();
        }
    }

    private void adjustDropRate(boolean userAnswer) {
        if (Answer.get(numbers.get(currentIndex)) == userAnswer) {
            updateCorrect();
            if(target.equalsIgnoreCase("hard")) {
                dropRate += 0.03;
            }
            dropRate += 0.05;
            Toast.makeText(getContext(), "정답입니다!", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(getContext(), "아쉽지만 오답이에요!", Toast.LENGTH_SHORT).show();
    }

    private void resultQuiz() {
        if (Math.random() < dropRate) {
            updateTotal();
            File file = new File(requireContext().getFilesDir(), "Library.txt");
            String line;
            List<String> lines = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("=");
                    if (parts[0].equalsIgnoreCase("BITCOIN_SDCARD_ACQUIRED") && !Boolean.parseBoolean(parts[1].trim())) {
                        lines.add(parts[0] + "=true");
                        continue;
                    } else lines.add(line);
                }
                Toast.makeText(getContext(), "획득!", Toast.LENGTH_SHORT).show();
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
    }

    private void updateTotal() {
        File file = new File(requireContext().getFilesDir(), "Profile.txt");
        String line;
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts[0].equalsIgnoreCase("TOTAL_SDCARD_COUNT")) {
                    lines.add(parts[0] + "=" + (Integer.parseInt(parts[1])+1) );
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

    private void updateTried() {
        File file = new File(requireContext().getFilesDir(), "Profile.txt");
        String line;
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts[0].equalsIgnoreCase("TRIED_QUIZ_COUNT")) {
                    lines.add(parts[0] + "=" + (Integer.parseInt(parts[1])+1) );
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
    private void updateCorrect() {
        File file = new File(requireContext().getFilesDir(), "Profile.txt");
        String line;
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts[0].equalsIgnoreCase("CORRECT_QUIZ_COUNT")) {
                    lines.add(parts[0] + "=" + (Integer.parseInt(parts[1])+1) );
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

    public void goFragment7() {
        Fragment7 fragment7 = new Fragment7();

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment7);
        transaction.commit();
    }
}