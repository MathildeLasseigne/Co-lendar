package fr.ups.co_lendar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private TextView resetPassword;
    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        initializeUI();
    }

    private void initializeUI() {
        register = findViewById(R.id.textView_register);
        register.setOnClickListener(this);
        resetPassword = findViewById(R.id.textView_resetPassword);
        resetPassword.setOnClickListener(this);
        usernameInput = findViewById(R.id.editText_username);
        passwordInput = findViewById(R.id.editText_password);
        loginButton = findViewById(R.id.button_login);
        loginButton.setOnClickListener(this);
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
        }
    }

    private void login() {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (!username.isEmpty() && !password.isEmpty()) {

        } else {
            Toast.makeText(getApplicationContext(),"Please enter username and password", Toast.LENGTH_SHORT).show();
        }
    }
}
