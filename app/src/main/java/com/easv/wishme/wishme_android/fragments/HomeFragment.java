package com.easv.wishme.wishme_android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easv.wishme.wishme_android.R;

public class HomeFragment extends Fragment {

    private static final String TAG = "homeFragment";
    private CardView mWishlistCard;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        mWishlistCard = view.findViewById(R.id.cardView2);

        mWishlistCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishlistClicked();
            }
        });

        return view;
    }

    private void wishlistClicked() {
        WishesFragment fragment = new WishesFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
