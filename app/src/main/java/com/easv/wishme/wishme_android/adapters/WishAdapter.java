package com.easv.wishme.wishme_android.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.dal.DatabaseHelper;
import com.easv.wishme.wishme_android.entities.User;
import com.easv.wishme.wishme_android.entities.Wish;
import com.easv.wishme.wishme_android.interfaces.ICallBack;
import com.easv.wishme.wishme_android.utils.ImageHandler;
import com.easv.wishme.wishme_android.utils.UniversalImageLoader;
import com.google.firebase.auth.FirebaseUser;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

public class WishAdapter extends ArrayAdapter<Wish> {

    public Context mContext;
    private LayoutInflater mInflater;
    private List<Wish> mWishes = null;
    private int layoutResource;



    public WishAdapter(@NonNull Context context, int resource, @NonNull List<Wish> wishes) {
        super(context, resource, wishes);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        this.mContext = context;
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
            holder.mRatingBar = convertView.findViewById(R.id.ratingBar);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String wishNameStr = getItem(position).getName();
        String wishPriceStr = getItem(position).getPrice();;
       // holder.wishImage.setImageBitmap(getItem(position).getImageBitmap());
        Bitmap wishImage = getItem(position).getImageBitmap();
        Log.d("BITMAP", "" + getItem(position).getImageBitmap());




        //String wishImagePathStr = getItem(position).getImage();
        float wishRating = getItem(position).getRating();

        holder.wishName.setText(wishNameStr);
        holder.wishPrice.setText(wishPriceStr);
        holder.mRatingBar.setRating(wishRating);
        holder.wishImage.setImageBitmap(wishImage);
        return convertView;


    }

    private static class ViewHolder {
        TextView wishName;
        TextView wishPrice;
        ImageView wishImage;
        RatingBar mRatingBar;
    }
}
