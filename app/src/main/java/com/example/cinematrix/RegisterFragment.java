package com.example.cinematrix;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterFragment extends Fragment {

    private EditText fullNameText;
    private EditText emailText;
    private EditText passwordText;
    private EditText passwordAgainText;
    private EditText phoneNumberText;
    private Button registerButton;
    private TextView registerToLogin;
    private BottomNavigationView bottomNavigationView;

    private Context context;

    public RegisterFragment() {
    }

    public RegisterFragment(Context context) {
        this.context = context;
    }

    public RegisterFragment( Context context,BottomNavigationView bottomNavigationView) {
        this.bottomNavigationView = bottomNavigationView;
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.register_fragment_layout, container, false);

        fullNameText = view.findViewById(R.id.registerFullName);
        emailText = view.findViewById(R.id.registerEmail);
        passwordText = view.findViewById(R.id.registerPassword1);
        passwordAgainText = view.findViewById(R.id.registerPassword2);
        phoneNumberText = view.findViewById(R.id.registerPhone);
        registerButton = view.findViewById(R.id.registerButton);
        registerToLogin = view.findViewById(R.id.registerToLogin);

        registerButton.setOnClickListener( v -> register());

        registerToLogin.setOnClickListener(v -> startLogin());

        return view;
    }

    private void register() {
        String name = fullNameText.getText().toString();
        String email = emailText.getText().toString();
        String password1 = passwordText.getText().toString();
        String password2 = passwordAgainText.getText().toString();
        String phoneNumber = phoneNumberText.getText().toString();

        if (!name.isEmpty() && !email.isEmpty() && !password1.isEmpty() && !password2.isEmpty() && password1.equals(password2)) {

            String hashPassword = passwordHash(password1,email.getBytes());
            String hashEmail = passwordHash(email,"salt".getBytes());

            User user = new User();
            user.setEmail(email);
            user.setFullName(name);
            user.setPassword(hashPassword);
            user.setPhoneNumber(phoneNumber.isEmpty() ? "" : phoneNumber);
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference dbUsers = database.getReference("users");

            dbUsers.child(hashEmail).setValue(user);


        }

    }

    private void startLogin() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        LoginFragment loginFragment = new LoginFragment(context);
        transaction.replace(R.id.container, loginFragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * Generate a password hash by using (email as) salt.
     *
     * @param password, salt
     *
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

