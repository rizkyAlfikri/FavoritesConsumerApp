package com.dicoding.picodiploma.favoritesconsumerapp.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dicoding.picodiploma.favoritesconsumerapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowConsumerFragment extends Fragment {


    public TvShowConsumerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_consumer, container, false);
    }

}
