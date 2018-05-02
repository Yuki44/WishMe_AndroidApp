package com.easv.wishme.wishme_android.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.entities.Wishlist;

import java.util.List;

public class WishlistAdapter extends ArrayAdapter<Wishlist> {


    private LayoutInflater mInflater;
    private List<Wishlist> mWishlists = null;
    private int layoutResource;
    private Context mContext;
    private String mAppend;

    public WishlistAdapter(@NonNull Context context, int resource, @NonNull List<Wishlist> wishlists, String append) {
        super(context, resource, wishlists);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        this.mContext = context;
        mAppend = append;
        this.mWishlists = wishlists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final WishlistAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new WishlistAdapter.ViewHolder();

            holder.wishlistName = convertView.findViewById(R.id.wishlist_name);
            holder.mProgressBar = convertView.findViewById(R.id.wishlistProgressbar);

            convertView.setTag(holder);

        } else {
            holder = (WishlistAdapter.ViewHolder) convertView.getTag();
        }

        String wishlistNameStr = getItem(position).getwListName();

        holder.wishlistName.setText(wishlistNameStr);

        return convertView;
    }

    private static class ViewHolder {
        TextView wishlistName;
        ProgressBar mProgressBar;
    }

}
