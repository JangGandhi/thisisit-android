package com.example.thisisit_newandquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Fragment4 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_4, container, false);

        // 스위치 상태 감지 및 전환
        Switch switchToHard = view.findViewById(R.id.switch1);
        switchToHard.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Fragment5 fragment5 = new Fragment5();
                Bundle bundle = new Bundle();
                bundle.putBoolean("switchState", true); // 상태 전달
                fragment5.setArguments(bundle);

                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment5);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // 버튼 클릭 시 Fragment6으로 이동
        Button btnSdcard = view.findViewById(R.id.btn_sdcard);
        btnSdcard.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Fragment6())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}
