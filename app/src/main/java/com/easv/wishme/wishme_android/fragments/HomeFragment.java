package com.easv.wishme.wishme_android.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.adapters.WishlistAdapter;
import com.easv.wishme.wishme_android.dal.AuthenticationHelper;
import com.easv.wishme.wishme_android.dal.DatabaseHelper;
import com.easv.wishme.wishme_android.entities.Wish;
import com.easv.wishme.wishme_android.interfaces.ICallBack;
import com.easv.wishme.wishme_android.interfaces.ICallBackDatabase;
import com.easv.wishme.wishme_android.entities.User;
import com.easv.wishme.wishme_android.entities.Wishlist;
import com.easv.wishme.wishme_android.dialogfragments.CreateWishlistDialog;
import com.easv.wishme.wishme_android.dialogfragments.LogOutDialog;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    private static final String TAG = "homeFragment";
    private CardView mProfileCard;
    private Toolbar toolbar;
    public WishlistAdapter wishlistAdapter;
    private AuthenticationHelper authHelper;
    private TextView mNameTV;
    private TextView mNoListTV;
    private TextView mAddressTV;
    private TextView mContactTV;
    private ProgressBar mProgressBar;
    private ProgressBar mProfileProgressBar;
    private CircleImageView mImageView;
    private ArrayList<Wishlist> wishList;
    private FirebaseFirestore db;
    public ListView mWishList;
    private FloatingActionButton mCreateWishlist;
    public static Bitmap mSelectedImage;
    private DatabaseHelper databaseHelper;

    public interface OnWishlistItemClicked {
        void getWishlistItemClicked(Wishlist wList);
    }

    OnWishlistItemClicked mOnWishlistItemClicked;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        toolbar = view.findViewById(R.id.logoutToolbar);
        databaseHelper = new DatabaseHelper();
        mNoListTV = view.findViewById(R.id.noListTV);
        authHelper = new AuthenticationHelper();
        mAddressTV = view.findViewById(R.id.addressTV);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mProfileProgressBar = (ProgressBar) view.findViewById(R.id.profileImageProgressBar);
        mNameTV = view.findViewById(R.id.nameTV);
        mContactTV = view.findViewById(R.id.contactTV);
        mCreateWishlist = (FloatingActionButton) view.findViewById(R.id.createWishlistFab);
        mImageView = view.findViewById(R.id.profileImager);
        mWishList = view.findViewById(R.id.wishlist);
        mNoListTV.setText("");
        mProfileCard = view.findViewById(R.id.cardView);
        mProfileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });

        db = FirebaseFirestore.getInstance();
        mWishList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Wishlist wList = wishList.get(position);
                mOnWishlistItemClicked.getWishlistItemClicked(wList);
            }
        });
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        setHasOptionsMenu(true);
        initProgressBar();
        initImageProgressBar();
        setUserInfo();
        setProfileImage();
        mCreateWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateWishListDialog();
            }
        });
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);
        // Inflate the menu; this adds items to the action bar if it is present
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuitem_logout:
                logout();
                return true;
            case R.id.menuitem_edit_user:
                editProfile();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        setWishlist();
    }

    private void editProfile() {
        EditProfileFragment fragment = new EditProfileFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        return;
    }

    private void logout() {
        LogOutDialog dialog = new LogOutDialog();
        dialog.show(getFragmentManager(), getString(R.string.logout));
        dialog.setTargetFragment(HomeFragment.this, 1);
    }

    private void showCreateWishListDialog() {
        CreateWishlistDialog dialog = new CreateWishlistDialog();
        dialog.show(getFragmentManager(), getString(R.string.create_wishlist));
        dialog.setTargetFragment(HomeFragment.this, 1);
    }

    private void setUserInfo() {
        showImageProgressBar();
        User user = authHelper.getUserWithInfo(new ICallBack() {
            @Override
            public void onFinish(User user) {
                mNameTV.setText(user.getname());
                mContactTV.setText(user.getContactEmail());
                mAddressTV.setText(user.getAddress());
            }

            @Override
            public void onFinishFireBaseUser(FirebaseUser user) {
            }

            @Override
            public void onFinishGetImage(Bitmap bitmap) {
                setProfileImage();
            }
        });
    }

    private void setProfileImage() {
        mSelectedImage = authHelper.getProfileImage(new ICallBack() {
            @Override
            public void onFinish(User user) {
            }

            @Override
            public void onFinishFireBaseUser(FirebaseUser user) {
            }

            @Override
            public void onFinishGetImage(Bitmap bitmap) {
                hideImageProgressBar();
                mImageView.setImageBitmap(bitmap);
            }
        });
    }

    private void setWishlist() {
        mNoListTV.setText("Loading...");
        databaseHelper.getWishLists(new ICallBackDatabase() {
            @Override
            public void onFinishWishList(Wishlist wList) {
            }

            @Override
            public void onFinishWishListList(ArrayList list) {
                if (list.size() != 0) {
                    wishList = list;
                    if (wishList != null && getActivity() != null) {
                        wishlistAdapter = new WishlistAdapter(getActivity(), R.layout.wishlist_item, wishList, "https://");
                        mWishList.setAdapter(wishlistAdapter);
                        mNoListTV.setText("");
                        mNoListTV.setText("");
                        return;
                    }
                }
            }

            @Override
            public void onFinishWish(Wish wish) {

            }

            @Override
            public void onFinnishGetWishes(ArrayList list) {

            }
        });
        mNoListTV.setText("Nothing here yet...");
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

    private void showImageProgressBar() {
        mProfileProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideImageProgressBar() {
        mProfileProgressBar.setVisibility(View.GONE);
    }

    private void initImageProgressBar() {
        mProfileProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnWishlistItemClicked = (OnWishlistItemClicked) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setUserInfo();
        setProfileImage();
    }
}
