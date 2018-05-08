package com.easv.wishme.wishme_android.dialogfragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.activities.MainActivity;
import com.easv.wishme.wishme_android.utils.ImageHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ChangePhotoDialog extends DialogFragment {
    private static final String TAG = "ChangePhotoDialog";
    private ImageHandler imgHandler;


    private static final int CAMERA_REQUEST_CODE = 4321;
    private static final int PICKFILE_REQUEST_CODE = 1234;
    private String picturePath;

//
//    public interface OnPhotoSelectedListener{
//        void getImageBitmap(Bitmap bitmap);
//    }
//    OnPhotoSelectedListener mOnPhotoSelectedListener;

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

        imgHandler = new ImageHandler();
        TextView takePhoto = view.findViewById(R.id.dialogTakePhoto);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: starting camera.");

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
            try{
            final Uri selectedImageUri = data.getData();
            final String imagePath = imgHandler.getFilePath(  getContext(),selectedImageUri);
            final InputStream imageStream = getContext().getContentResolver().openInputStream(selectedImageUri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            final Bitmap imgRotated = imgHandler.modifyOrientation(selectedImage, imagePath);
//            SignUpStep2.mSelectedImage = imgRotated;
//            EditProfileFragment.mSelectedImage = imgRotated;
                MainActivity.mSelectedImage = imgRotated;
                Log.d(TAG, "Image sent to main activity");

            Log.d(TAG, imgRotated.toString());

            getDialog().dismiss();
        } catch(FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Log.d(TAG, "You haven't chosen a picture");
            }

    }

//    @Override
//    public void onAttach(Context context) {
//        try{
//            mOnPhotoSelectedListener =  (OnPhotoSelectedListener) getActivity();
//            Log.d("abc", "onAttach - ref to mainactivity set");
//        }catch(ClassCastException e){
//            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
//        }
//        super.onAttach(context);
//    }

}