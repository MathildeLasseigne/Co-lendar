package fr.ups.co_lendar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import fr.ups.co_lendar.fragments.CalendarFragment;
import fr.ups.co_lendar.fragments.GroupsFragment;
import fr.ups.co_lendar.fragments.HomeFragment;
import fr.ups.co_lendar.fragments.NotificationsFragment;
import fr.ups.co_lendar.helpers.User;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    User loggedInUser;

    HomeFragment homeFragment = new HomeFragment();
    CalendarFragment calendarFragment = new CalendarFragment();
    GroupsFragment groupsFragment = new GroupsFragment();
    NotificationsFragment notificationsFragment = new NotificationsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        getLoggedInUser();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);

    }

    private void getLoggedInUser() {

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            loggedInUser = (User) extras.getSerializable("user");
        }
    }

    //

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("user", loggedInUser);

        switch (item.getItemId()) {
            case R.id.home:
                homeFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();
                return true;

            case R.id.calendar:
                calendarFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, calendarFragment).commit();
                return true;

            case R.id.groups:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, groupsFragment).commit();
                return true;

            case R.id.notifications:
                notificationsFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, notificationsFragment).commit();
                return true;
        }
        return false;
    }
}

