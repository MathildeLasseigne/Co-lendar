package fr.ups.co_lendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import fr.ups.co_lendar.helpers.User;

public class HomeActivity extends AppCompatActivity {

    TextView greeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeUI();
        setName();
    }

    private void initializeUI() {
        greeting = findViewById(R.id.textView_greeting);
    }

    private void setName() {

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            User user = (User) extras.getSerializable("user");
            String newGreeting = greeting.getText() + user.getFirstName();
            greeting.setText(newGreeting);
        }
    }
}