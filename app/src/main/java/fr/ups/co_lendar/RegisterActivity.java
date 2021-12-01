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
    EditText passwordInput;
    EditText passwordConfirmInput;
    ImageButton profilePictureButton;
    Button registerButton;
    TextView backToLoginButton;
    ProgressBar progressBar;

    private static final String TAG = "RegisterActivity";

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
        String password = passwordInput.getText().toString().trim();
        String passwordConfirm = passwordConfirmInput.getText().toString().trim();

        if(validRegistration(firstName, lastName, email, password, passwordConfirm)) {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            User user = new User(firstName, lastName, email, password);
                            FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.tableNameUsers))
                                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                    .setValue(user).addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Toast.makeText(this, getResources().getString(R.string.registrationSuccessful), Toast.LENGTH_LONG)
                                                    .show();
                                            progressBar.setVisibility(View.GONE);
                                        } else {
                                            Toast.makeText(this, getResources().getString(R.string.registrationFailed), Toast.LENGTH_LONG)
                                                    .show();
                                        }
                                    });
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(this,  getResources().getString(R.string.registrationFailed), Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
        }
    }

    private boolean validRegistration(String firstName, String lastName, String email, String password,
                                      String passwordConfirm) {

        if (firstName.isEmpty()) {
            firstNameInput.setError(getResources().getString(R.string.firstNameRequired));
            firstNameInput.requestFocus();
            return false;
        } else if (lastName.isEmpty()) {
            lastNameInput.setError(getResources().getString(R.string.lastNameRequired));
            lastNameInput.requestFocus();
            return false;
        } else if (email.isEmpty()) {
            emailInput.setError(getResources().getString(R.string.emailRequired));
            emailInput.requestFocus();
            return false;
        } else if (!Pattern.compile(getResources().getString(R.string.emailRegex)).matcher(email).matches()) {
            emailInput.setError(getResources().getString(R.string.emailInvalid));
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
        } else if (passwordConfirm.isEmpty()) {
            passwordConfirmInput.setError(getResources().getString(R.string.confirmPasswordRequired));
            passwordConfirmInput.requestFocus();
            return false;
        } else if (!password.equals(passwordConfirm)) {
            passwordInput.setError(getResources().getString(R.string.passwordsDontMatch));
            passwordConfirmInput.setError(getResources().getString(R.string.passwordsDontMatch));
            passwordInput.requestFocus();
            return false;
        }
        return true;
    }
}