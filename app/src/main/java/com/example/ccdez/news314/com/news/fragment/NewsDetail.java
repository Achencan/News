package com.example.ccdez.news314.com.news.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ccdez.news314.R;

/**
 * Created by cc on 17-9-26.
 */

public class NewsDetail extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_detail, container, false);
        return view;
    }
}
