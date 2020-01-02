package com.example.cinematrix.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cinematrix.R;

public class ProfileFragment extends Fragment {

    private Context context;
    private TextView curentPassword;
    private TextView changePassword;
    private TextView editProfileImage;
    private ImageView profilePicture;
    private Button saveBtn;

    public ProfileFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        changePassword = view.findViewById(R.id.profileChangePassword);
        curentPassword = view.findViewById(R.id.profileCurentPassword);
        profilePicture = view.findViewById(R.id.profileImage);
        editProfileImage = view.findViewById(R.id.profileEdit);
        saveBtn = view.findViewById(R.id.profileSaveButton);



        return view;
    }
}
