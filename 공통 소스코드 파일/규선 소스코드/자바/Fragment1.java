package com.example.thisisit_newandquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Fragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        // 첫 번째 뉴스 카드 클릭 시 Fragment4로 이동
        LinearLayout bitcoinCard = view.findViewById(R.id.bitcoin);
        bitcoinCard.setOnClickListener(v -> {
            FragmentTransaction transaction = requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.fragment_container, new Fragment4());
            transaction.addToBackStack(null); // 뒤로가기 가능
            transaction.commit();
        });

        return view;
    }
}
