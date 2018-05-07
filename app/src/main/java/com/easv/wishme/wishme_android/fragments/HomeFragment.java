package com.easv.wishme.wishme_android.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.adapters.WishAdapter;
import com.easv.wishme.wishme_android.adapters.WishlistAdapter;
import com.easv.wishme.wishme_android.dal.AuthenticationHelper;
import com.easv.wishme.wishme_android.dal.DatabaseHelper;
import com.easv.wishme.wishme_android.dal.ICallBack;
import com.easv.wishme.wishme_android.dal.ICallBackDatabase;
import com.easv.wishme.wishme_android.entities.User;
import com.easv.wishme.wishme_android.entities.Wishlist;
import com.easv.wishme.wishme_android.utils.CreateWishlistDialog;
import com.easv.wishme.wishme_android.utils.LogOutDialog;
import com.easv.wishme.wishme_android.utils.UniversalImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    private static final String TAG = "homeFragment";
    private CardView mWishlistCard;
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
    private DatabaseHelper dataHelper;


    public interface OnWishlistItemClicked{
    void getWishlistItemClicked(Wishlist wList);

    }
    OnWishlistItemClicked mOnWishlistItemClicked;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        toolbar = view.findViewById(R.id.logoutToolbar);
        dataHelper = new DatabaseHelper();
        mNoListTV = view.findViewById(R.id.noListTV);
        authHelper = new AuthenticationHelper();
        mWishlistCard = view.findViewById(R.id.cardView2);
        mAddressTV = view.findViewById(R.id.addressTV);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mProfileProgressBar = (ProgressBar) view.findViewById(R.id.profileImageProgressBar);

        mNameTV = view.findViewById(R.id.nameTV);
        mContactTV = view.findViewById(R.id.contactTV);
        mCreateWishlist = (FloatingActionButton) view.findViewById(R.id.createWishlistFab);
        mImageView = view.findViewById(R.id.profileImager);
        mWishList = view.findViewById(R.id.wishlist);
        mProfileCard = view.findViewById(R.id.cardView);
        mProfileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });
        mWishlistCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishlistClicked();
            }
        });
        db = FirebaseFirestore.getInstance();
        mWishList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Wishlist wList = wishList.get(position);
                Log.d(TAG, "onItemClick: " + wList.getId() + " " + "<><><><><><><><><><><><><><><><>");
                mOnWishlistItemClicked.getWishlistItemClicked(wList);
                wishlistClicked();
            }
        });
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        initProgressBar();
        initImageProgressBar();
        setHasOptionsMenu(true);
        setWishlist();
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


    private void wishlistClicked() {
        WishesFragment fragment = new WishesFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        // Inflate the menu; this adds items to the action bar if it is present.
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

    private void showCreateWishListDialog(){
        CreateWishlistDialog dialog = new CreateWishlistDialog();
        dialog.show(getFragmentManager(), getString(R.string.create_wishlist));
        dialog.setTargetFragment(HomeFragment.this, 1);

    }
    private void setUserInfo(){
        showImageProgressBar();
        User user =  authHelper.getUserWithInfo(new ICallBack() {
                 @Override
                 public void onFinish(User user) {
                     mNameTV.setText(user.getname());
                     mContactTV.setText(user.getContactEmail());
                     mAddressTV.setText(user.getAddress());



              Log.d(TAG, "setUserInfo: " + user.toString());
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
    private void setProfileImage(){
        mSelectedImage = authHelper.getProfileImage(new ICallBack() {
            @Override
            public void onFinish(User user) {

            }

            @Override
            public void onFinishFireBaseUser(FirebaseUser user) {

            }

            @Override
            public void onFinishGetImage(Bitmap bitmap) {
                mImageView.setImageBitmap(bitmap);
            }
        });
    }




    private void setWishlist(){
        showProgressBar();
        dataHelper.getWishLists(new ICallBackDatabase() {
            @Override
            public void onFinishWishList(Wishlist wList) {

            }

            @Override
            public void onFinishWishListList(ArrayList list) {
                wishList = list;
                wishlistAdapter = new WishlistAdapter(getActivity(), R.layout.wishlist_item, wishList , "https://");
                mWishList.setAdapter(wishlistAdapter);
                mNoListTV.setText("");
                hideProgressBar();
                return;

            }





        });
        if(wishList == null) {
            mWishlistCard.setVisibility(View.INVISIBLE);
            mNoListTV.setText("You don't have a Wish list yet!");
            hideProgressBar();
        }





    }



    private void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
    }

    private void initProgressBar(){

        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void showImageProgressBar(){
        mProfileProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideImageProgressBar(){
        mProfileProgressBar.setVisibility(View.GONE);
    }

    private void initImageProgressBar(){

        mProfileProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnWishlistItemClicked = (OnWishlistItemClicked) getTargetFragment();

        }catch(ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " +  e.getMessage());

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setUserInfo();
        setProfileImage();
    }

}
