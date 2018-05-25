package com.easv.wishme.wishme_android.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.adapters.WishAdapter;
import com.easv.wishme.wishme_android.dal.DatabaseHelper;
import com.easv.wishme.wishme_android.entities.User;
import com.easv.wishme.wishme_android.entities.Wish;
import com.easv.wishme.wishme_android.entities.Wishlist;
import com.easv.wishme.wishme_android.interfaces.ICallBack;
import com.easv.wishme.wishme_android.interfaces.ICallBackDatabase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WishesFragment extends android.support.v4.app.Fragment {

    public static final String TAG = "WishesFragment";
    public ListView mWishList;
    public WishAdapter wishAdapter;
    private TextView mNoWishes, mNameOfWishlist;
    private ArrayList<Wish> wishListList;
    private Wishlist listFromHome;
    private FloatingActionButton mAddWish;
    private FirebaseFirestore db;
    private Toolbar toolbar;
    private RatingBar mRatingBar;
    private DatabaseHelper dataHelper;
    private ProgressBar mProgressBar;
    private RelativeLayout mRelative;

    public interface OnEditWishList {
        void getWishlist(Wishlist wList);
    }

    WishesFragment.OnEditWishList mOnEditWishList;

    public WishesFragment() {
        super();
        setArguments(new Bundle());
    }

    public interface OnWishRetrievedListener {
        void getWishToDisplay(Wish wish);
    }

    OnWishRetrievedListener mOnWishRetrievedListener;

    public interface OnWishListToAddWishListener {
        void getWishListToAddWish(Wishlist wList);
    }

    OnWishListToAddWishListener mOnWishListToAddWishListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        mNoWishes = view.findViewById(R.id.textNoWishes);
        mProgressBar = view.findViewById(R.id.progressBar);
        mWishList = view.findViewById(R.id.wishesList);
        mNameOfWishlist = view.findViewById(R.id.nameOfWishlist);
        mAddWish = (FloatingActionButton) view.findViewById(R.id.addWishFab);
        mRatingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        mRelative = view.findViewById(R.id.relative);
        db = FirebaseFirestore.getInstance();
        initProgressBar();
        toolbar = view.findViewById(R.id.wishlistToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        listFromHome = getWishListFromBundle();
        mNameOfWishlist.setText(listFromHome.getwListName());
        setHasOptionsMenu(true);
        dataHelper = new DatabaseHelper();

        ImageView ivBackArrow = view.findViewById(R.id.ivBackArrow);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });

        mWishList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Wish selectedWish = (Wish) mWishList.getItemAtPosition(position);
                mOnWishRetrievedListener.getWishToDisplay(selectedWish);
            }
        });

        mAddWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWish();
            }
        });
        return view;
    }

    private void addWish() {
        mOnWishListToAddWishListener.getWishListToAddWish(listFromHome);
    }

    private Wishlist getWishListFromBundle() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            return bundle.getParcelable("WishList");
        } else {
            return null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mNoWishes.setText("Loading...");
        wishListList = new ArrayList<>();
        dataHelper.getWish(listFromHome, new ICallBackDatabase() {
            @Override
            public void onFinishWishList(Wishlist wList) {
            }

            @Override
            public void onFinishWishListList(ArrayList list) {
            }

            @Override
            public void onFinishWish(Wish wish) {
            }

            @Override
            public void onFinnishGetWishes(ArrayList list) {
                if (list.size() == 0) {
                    mNoWishes.setText("" + mNameOfWishlist.getText() + " is empty");
                }

                final List<Wish> wList = list;
                for (final Wish w : wList) {
                    dataHelper.getWishImage(w.getId(), new ICallBack() {
                        @Override
                        public void onFinish(User user) {
                        }

                        @Override
                        public void onFinishFireBaseUser(FirebaseUser user) {
                        }

                        @Override
                        public void onFinishGetImage(Bitmap bitmap) {
                            w.setImageBitmap(bitmap);
                            if (wList != null && getActivity() != null) {
                                wishAdapter = new WishAdapter(getActivity(), R.layout.wish_item, wList);
                                mWishList.setAdapter(wishAdapter);
                                sortListByName();
                                mNoWishes.setText("");
                            }
                        }
                    });
                }
            }
        });
    }

    private void sortListByName() {
        Collections.sort(wishListList, new Comparator<Wish>() {
            @Override
            public int compare(Wish o1, Wish o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.wishlist_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuitem_edit_wishlist:
                mOnEditWishList.getWishlist(listFromHome);
                return true;
            case R.id.menuitem_delete_wishlist:
                deleteWishList(listFromHome);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteWishList(Wishlist listFromHome) {
        mRelative.setVisibility(getView().INVISIBLE);
        mAddWish.setVisibility(getView().INVISIBLE);
        showProgressBar();
        dataHelper.deleteWishList(listFromHome, new ICallBackDatabase() {
            @Override
            public void onFinishWishList(Wishlist wList) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }

            @Override
            public void onFinishWishListList(ArrayList list) {
            }

            @Override
            public void onFinishWish(Wish wish) {
            }

            @Override
            public void onFinnishGetWishes(ArrayList list) {
            }
        });
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void initProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnEditWishList = (WishesFragment.OnEditWishList) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
        try {
            mOnWishRetrievedListener = (OnWishRetrievedListener) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
        try {
            mOnWishListToAddWishListener = (WishesFragment.OnWishListToAddWishListener) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
    }
}