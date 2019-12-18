package com.example.cinematrix.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cinematrix.R;
import com.example.cinematrix.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginFragment extends Fragment {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView toRegister;
    private Context context;
    private BottomNavigationView bottomNavigationView;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    public LoginFragment() {
    }

    public LoginFragment(Context context, BottomNavigationView bottomNavigationView) {
        this.context = context;
        this.bottomNavigationView = bottomNavigationView;
    }

    public LoginFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.login_fragment_layout, container, false);

        emailEditText = view.findViewById(R.id.loginEmailEditText);
        passwordEditText = view.findViewById(R.id.loginPasswordEditText);
        loginButton = view.findViewById(R.id.loginButton);
        toRegister = view.findViewById(R.id.loginToRegister);

        loginButton.setOnClickListener(v -> login());

        toRegister.setOnClickListener(v -> startRegister());

        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPreferences.edit();



        return view;
    }

    private void login() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (!email.isEmpty() && !password.isEmpty()) {

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference users = database.getReference("users");

            String hashEmail = passwordHash(email, "salt".getBytes());
            String hashPassword = passwordHash(password, email.getBytes());

            users.child(hashEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User user = dataSnapshot.getValue(User.class);

                        if (user.getPassword().equals(hashPassword)) {
                            Toast.makeText(getContext(), "Login Success!", Toast.LENGTH_SHORT).show();
                            bottomNavigationView.setVisibility(View.VISIBLE);
                            mEditor.putString("userKey", hashEmail);
                            mEditor.commit();
                            startHomeFragment();

                        } else {
                            Toast.makeText(getContext(), "Invalid email or password!", Toast.LENGTH_SHORT).show();

                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }

    private void startHomeFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment(getContext());
        transaction.replace(R.id.container, homeFragment);
//        transaction.addToBackStack(null);
        transaction.commit();

    }

    private void startRegister() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        RegisterFragment registerFragment = new RegisterFragment(getContext(),this.bottomNavigationView);
        transaction.replace(R.id.container, registerFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    /**
     * Generate a password hash by using (email as) salt.
     *
     * @param password, salt
     * @return hashed password
     */
    public String passwordHash(String password, byte[] salt) {

        String generatedPassword = null;

        try {
            // Create MessageDigest instance for MD5
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // Add password bytes to digest
            messageDigest.update(salt);
            // Get the hash's bytes
            byte[] bytes = messageDigest.digest(password.getBytes());
            // This bytes[] has bytes in decimal format;
            // Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            // Get complete hashed password in hex format
            generatedPassword = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedPassword;

    }
}
