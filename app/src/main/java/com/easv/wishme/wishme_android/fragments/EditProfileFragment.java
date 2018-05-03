package com.easv.wishme.wishme_android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easv.wishme.wishme_android.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileFragment extends Fragment {

    private static final String TAG = "CreateUserFragment2";
    private CircleImageView profileImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_step_2, container, false);

        profileImage = (CircleImageView) view.findViewById(R.id.profileImage);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             changePic();
            }
        });

        return view;
    }

    private void changePic() {
       /* for (int i = 0; i < Init.PERMISSIONS.length; i++) {
            String[] permission = {Init.PERMISSIONS[i]};
            if (checkPermission(permission)) {
                if (i == Init.PERMISSIONS.length - 1) {
                    Log.d(TAG, "onClick: opening the 'image selection dialog box'.");
                    ChangePhotoDialog dialog = new ChangePhotoDialog();
                    dialog.show(getSupportFragmentManager(), getString(R.string.change_photo_dialog));
                }
            } else {
                verifyPermissions(permission);
            }
        }*/
    }


}
