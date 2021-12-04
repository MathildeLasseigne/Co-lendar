package fr.ups.co_lendar;
import java.io.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);

    }
    HomeFragment homeFragment = new HomeFragment();
    CalendarFragment calendarFragment = new CalendarFragment();
    GroupsFragment groupsFragment = new GroupsFragment();
    NotificationsFragment notificationsFragment = new NotificationsFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();
                return true;

            case R.id.calendar:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, calendarFragment).commit();
                return true;

            case R.id.groups:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, groupsFragment).commit();
                return true;

            case R.id.notifications:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, notificationsFragment).commit();
                return true;
        }
        return false;
    }
}

