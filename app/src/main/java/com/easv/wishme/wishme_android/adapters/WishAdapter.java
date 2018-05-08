package com.easv.wishme.wishme_android.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.entities.Wish;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

public class WishAdapter extends ArrayAdapter<Wish> {

    public Context mContext;
    private LayoutInflater mInflater;
    private List<Wish> mWishes = null;
    private int layoutResource;
    private String mAppend;

    public WishAdapter(@NonNull Context context, int resource, @NonNull List<Wish> wishes, String append) {
        super(context, resource, wishes);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        this.mContext = context;
        mAppend = append;
        this.mWishes = wishes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();

            holder.wishName = convertView.findViewById(R.id.wish_name);
            holder.wishPrice = convertView.findViewById(R.id.wish_price);
            holder.wishImage = convertView.findViewById(R.id.wish_image);
            holder.mProgressBar = convertView.findViewById(R.id.wishProgressbar);
            holder.mRatingBar = convertView.findViewById(R.id.ratingBar);


            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String wishNameStr = getItem(position).getName();
        String wishPriceStr = getItem(position).getPrice();
        String wishImagePathStr = getItem(position).getImage();
        float wishRating = getItem(position).getRating();


        holder.wishName.setText(wishNameStr);
        holder.wishPrice.setText(wishPriceStr);
        holder.mRatingBar.setRating(wishRating);

        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.displayImage(mAppend + wishImagePathStr, holder.wishImage, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                holder.mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                holder.mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                holder.mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                holder.mProgressBar.setVisibility(View.GONE);
            }
        });

        return convertView;


    }

    private static class ViewHolder {
        TextView wishName;
        TextView wishPrice;
        ImageView wishImage;
        ProgressBar mProgressBar;
        RatingBar mRatingBar;
    }
}
