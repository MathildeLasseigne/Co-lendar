package fr.ups.co_lendar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

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
                    Intent i = new Intent(this, HomeActivity.class);
                    i.putExtra("firstName", (new User(mAuth.getUid())).getFirstName());
                    startActivity(i);
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
}
