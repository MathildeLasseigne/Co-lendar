package fr.ups.co_lendar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.net.URL;
import java.util.Objects;
import java.util.regex.Pattern;

import fr.ups.co_lendar.helpers.User;

public class MainLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private TextView resetPassword;
    private EditText emailInput;
    private EditText passwordInput;
    private Button loginButton;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        initializeUI();
        mAuth = FirebaseAuth.getInstance();
    }

    private void initializeUI() {
        register = findViewById(R.id.textView_register);
        register.setOnClickListener(this);
        resetPassword = findViewById(R.id.textView_resetPassword);
        resetPassword.setOnClickListener(this);
        emailInput = findViewById(R.id.editText_email);
        passwordInput = findViewById(R.id.editText_password);
        loginButton = findViewById(R.id.button_login);
        loginButton.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.textView_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.textView_resetPassword:
                break;
            case R.id.button_login:
                login();
                break;
        }
    }

    private void login() {

        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

         if (validLogin(email, password)) {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    loginWithUser(new Intent(this, HomeActivity.class));
                } else {
                    Toast.makeText(this, getResources().getString(R.string.loginFailed), Toast.LENGTH_LONG);
                }
            });
        }
    }

    private boolean validLogin(String email, String password) {

        if (email.isEmpty()) {
            emailInput.setError("Email is required");
            emailInput.requestFocus();
            return false;
        } else if (password.isEmpty()) {
            passwordInput.setError(getResources().getString(R.string.passwordRequired));
            passwordInput.requestFocus();
            return false;
        } else if (password.length() < 6) {
            passwordInput.setError(getResources().getString(R.string.passwordTooShort));
            passwordInput.requestFocus();
            return false;
        } else if (!Pattern.compile(getResources().getString(R.string.emailRegex)).matcher(email).matches()) {
            emailInput.setError(getResources().getString(R.string.emailInvalid));
            emailInput.requestFocus();
            return false;
        }
        return true;
    }

    //TODO: figure out a way that awaits the result from the User class and place this method in the user class
    private void loginWithUser(Intent i) {

        User user = new User();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");
        Query userQuery = userRef.child(Objects.requireNonNull(mAuth.getUid()));
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    user.setFirstName(snapshot.child("firstName").getValue(String.class));
                    user.setLastName(snapshot.child("lastName").getValue(String.class));
                    user.setEmail(snapshot.child("email").getValue(String.class));
                    user.setPassword(snapshot.child("password").getValue(String.class));
                    i.putExtra("user", user);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getMessage());
            }
        });

    }
}
