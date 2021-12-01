package fr.ups.co_lendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.regex.Pattern;

import fr.ups.co_lendar.helpers.User;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    EditText firstNameInput;
    EditText lastNameInput;
    EditText emailInput;
    EditText usernameInput;
    EditText passwordInput;
    EditText passwordConfirmInput;
    ImageButton profilePictureButton;
    Button registerButton;
    TextView backToLoginButton;
    ProgressBar progressBar;

    private static final String TAG = "RegisterActivity";
    String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeUI();
        mAuth = FirebaseAuth.getInstance();
    }

    private void initializeUI() {
        firstNameInput = findViewById(R.id.editText_firstName);
        lastNameInput = findViewById(R.id.editText_lastName);
        emailInput = findViewById(R.id.editText_email);
        usernameInput = findViewById(R.id.editText_username);
        passwordInput = findViewById(R.id.editText_password);
        passwordConfirmInput = findViewById(R.id.editText_passwordConfirm);

        profilePictureButton = findViewById(R.id.imageButton_profilePicture);
        profilePictureButton.setOnClickListener(this);
        registerButton = findViewById(R.id.button_register);
        registerButton.setOnClickListener(this);
        backToLoginButton = findViewById(R.id.textView_backToLogin);
        backToLoginButton.setOnClickListener(this);

        progressBar = findViewById(R.id.progressBar);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button_register:
                register();
                break;
            case R.id.textView_backToLogin:
                startActivity(new Intent(this, MainLoginActivity.class));
                break;
        }
    }

    private void register() {
        String firstName = firstNameInput.getText().toString().trim();
        String lastName = lastNameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String passwordConfirm = passwordConfirmInput.getText().toString().trim();

        //TODO: verification of username uniqueness
        if (firstName.isEmpty()) {
            firstNameInput.setError("First name is required");
            firstNameInput.requestFocus();
        } else if (lastName.isEmpty()) {
            lastNameInput.setError("Last name is required");
            lastNameInput.requestFocus();
        } else if (email.isEmpty()) {
            emailInput.setError("Email is required");
            emailInput.requestFocus();
        } else if (!Pattern.compile(emailRegex).matcher(email).matches()) {
            emailInput.setError("Email is not valid");
            emailInput.requestFocus();
        } else if (username.isEmpty()) {
            usernameInput.setError("Username is required");
            usernameInput.requestFocus();
        } else if (password.isEmpty()) {
            passwordInput.setError("Password is required");
            passwordInput.requestFocus();
        } else if (password.length() < 6) {
            passwordInput.setError("Password needs to be at least 6 characters long");
            passwordInput.requestFocus();
        } else if (passwordConfirm.isEmpty()) {
            passwordConfirmInput.setError("Confirm password");
            passwordConfirmInput.requestFocus();
        } else if (!password.equals(passwordConfirm)) {
            passwordInput.setError("Passwords do not match");
            passwordConfirmInput.setError("Passwords do not match");
            passwordInput.requestFocus();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            User user = new User(firstName, lastName, email, username, password);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                    .setValue(user).addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_LONG)
                                                    .show();
                                            progressBar.setVisibility(View.GONE);
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "Registration failed. Try again!", Toast.LENGTH_LONG)
                                                    .show();
                                        }
                                    });
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Registration failed. Try again!", Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
        }
    }
}