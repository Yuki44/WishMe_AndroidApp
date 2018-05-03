package com.easv.wishme.wishme_android.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easv.wishme.wishme_android.R;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class ChangePhotoDialog extends DialogFragment {
    private static final String TAG = "ChangePhotoDialog";
//    private String mSelectedImagePath;
//    private static final int CAMERA_REQUEST_CODE = 10;
//    String mCurrentPhotoPath;
//    private static final String CAPTURE_IMAGE_FILE_PROVIDER = "com.easv.boldi.yuki.mapme.fileprovider";

    private static final int CAMERA_REQUEST_CODE = 4321;
    private static final int PICKFILE_REQUEST_CODE = 1234;

    public interface OnPhotoSelectedListener{
        void getImagePath(Uri imagePath);
        void getImageBitmap(Bitmap bitmap);
    }
    OnPhotoSelectedListener mOnPhotoSelectedListener;

    public static Bitmap rotateImage(Bitmap src, float degree) {
        // create new matrix
        Matrix matrix = new Matrix();
        // setup rotation degree
        matrix.postRotate(degree);
        Bitmap bmp = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        return bmp;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_changephoto, container, false);






        //initalize the textview for starting the camera

        TextView takePhoto = view.findViewById(R.id.dialogTakePhoto);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: starting camera.");
//
//                File path = new File(getActivity().getFilesDir(), "your/path");
//                if (!path.exists()) path.mkdirs();
//                File image = new File(path, "image.jpg");
//                Uri imageUri = FileProvider.getUriForFile(getActivity(), CAPTURE_IMAGE_FILE_PROVIDER, image);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);

            }
        });


        //Initialize the textview for choosing an image from memory

        TextView selectPhoto = view.findViewById(R.id.dialogChoosePhoto);
        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: accessing phone memory.");
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICKFILE_REQUEST_CODE);
            }
        });

        // Cancel button for closing the dialog
        TextView cancelDialog = view.findViewById(R.id.dialogCancel);
        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog.");
                getDialog().dismiss();
            }
        });

//        mSelectedImagePath = null;
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICKFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();
            mOnPhotoSelectedListener.getImagePath(selectedImageUri);
//            File file = new File(selectedImageUri.toString());
//            Log.d(TAG, "onActivityResult: images: " + file.getPath() + " " + mSelectedImagePath);
//            getImagePath(file.getPath());
            getDialog().dismiss();
        }

       else if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
Bitmap bitmap;
bitmap = (Bitmap) data.getExtras().get("data");
mOnPhotoSelectedListener.getImageBitmap(bitmap);
            getDialog().dismiss();
        }



    }

//    public void getImagePath(String imagePath) {
//        Log.d(TAG, "getImagePath: got the imagePath" + imagePath);
//        if (!imagePath.equals("")) {
//            imagePath = imagePath.replace(":/", "://");
//            mSelectedImagePath = imagePath;
//            FriendActivityEditNew.mSelectedImagePath = imagePath;
//            UniversalImageLoader.setImage(imagePath, FriendActivityEditNew.mFriendImage, null, "");
//        }
//    }
//
//    public void getBitmapImage(String imagePath) {
//        Log.d(TAG, "getImagePath: got the imagePath  " + imagePath);
//        if (!imagePath.equals("")) {
//            mSelectedImagePath = imagePath;
//            FriendActivityEditNew.mSelectedImagePath = imagePath;
//            UniversalImageLoader.setImage(imagePath, FriendActivityEditNew.mFriendImage, null, "");
//        }
//    }

//    private Uri getImageUri(Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
//    }


    @Override
    public void onAttach(Context context) {
        try{
            mOnPhotoSelectedListener =  (OnPhotoSelectedListener) getActivity();
        }catch(ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
        }
        super.onAttach(context);
    }
}