package com.example.thisisit_newandquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Fragment5 extends Fragment {

    private Button btnBonusRetry, btnBonusColor, btnBonusBoost;
    private Switch switchToEasy;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_5, container, false);

        btnBonusRetry = view.findViewById(R.id.btn_bonus_retry);
        btnBonusColor = view.findViewById(R.id.btn_bonus_color);
        btnBonusBoost = view.findViewById(R.id.btn_bonus_boost);
        switchToEasy = view.findViewById(R.id.switch2);

        // 전달받은 상태 적용 (기본값: true)
        Bundle args = getArguments();
        if (args != null) {
            boolean switchState = args.getBoolean("switchState", true);
            switchToEasy.setChecked(switchState);
        }

        // 스위치 꺼짐 → Fragment4로 이동
        switchToEasy.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Fragment4());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // 모두 숨기고 하나 무작위 표시
        btnBonusRetry.setVisibility(View.GONE);
        btnBonusColor.setVisibility(View.GONE);
        btnBonusBoost.setVisibility(View.GONE);

        List<Button> bonusButtons = Arrays.asList(btnBonusRetry, btnBonusColor, btnBonusBoost);
        Collections.shuffle(bonusButtons);
        Button selectedButton = bonusButtons.get(0);
        selectedButton.setVisibility(View.VISIBLE);

        btnBonusRetry.setOnClickListener(v -> {
            SharedPreferences prefs = requireContext().getSharedPreferences("quiz_data", Context.MODE_PRIVATE);
            prefs.edit().putInt("max_retry", 2).apply();
            moveToFragment8();
        });

        btnBonusColor.setOnClickListener(v -> {
            SharedPreferences prefs = requireContext().getSharedPreferences("quiz_data", Context.MODE_PRIVATE);
            prefs.edit().putBoolean("colored_sdcard", true).apply();
            moveToFragment8();
        });

        btnBonusBoost.setOnClickListener(v -> {
            SharedPreferences prefs = requireContext().getSharedPreferences("quiz_data", Context.MODE_PRIVATE);
            prefs.edit().putInt("chance_bonus", 20).apply();
            moveToFragment8();
        });

        return view;
    }

    private void moveToFragment8() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new Fragment8());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
