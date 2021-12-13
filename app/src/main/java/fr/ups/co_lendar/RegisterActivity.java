package fr.ups.co_lendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import fr.ups.co_lendar.helpers.User;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    EditText firstNameInput;
    EditText lastNameInput;
    EditText emailInput;
    EditText passwordInput;
    EditText passwordConfirmInput;
    ImageView profilePictureImageView;
    Button registerButton;
    TextView backToLoginButton;
    ProgressBar progressBar;

    private Uri imageUri;

    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeUI();
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    private void initializeUI() {
        firstNameInput = findViewById(R.id.editText_firstName);
        lastNameInput = findViewById(R.id.editText_lastName);
        emailInput = findViewById(R.id.editText_email);
        passwordInput = findViewById(R.id.editText_password);
        passwordConfirmInput = findViewById(R.id.editText_passwordConfirm);

        profilePictureImageView = findViewById(R.id.imageButton_profilePicture);
        profilePictureImageView.setOnClickListener(this);
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
            case R.id.imageButton_profilePicture:
                choosePicture();
                break;
        }
    }

    private void register() {

        String firstName = firstNameInput.getText().toString().trim();
        String lastName = lastNameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String passwordConfirm = passwordConfirmInput.getText().toString().trim();
        User user = new User(firstName, lastName, email, password, mAuth.getCurrentUser().getUid());

        if(validRegistration(firstName, lastName, email, password, passwordConfirm)) {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            user.registerUser(new FirebaseCallback() {
                                @Override
                                public void onStart() {}

                                @Override
                                public void onSuccess(Object data) {
                                    if (imageUri != null) {
                                        //if there is an image - add image
                                        uploadPicture(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
                                    } else {
                                        //if not - registration successful
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.registrationSuccessful), Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(getApplicationContext(), MainLoginActivity.class));
                                    }
                                }

                                @Override
                                public void onFailed(DatabaseError databaseError) {
                                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.registrationFailed), Toast.LENGTH_LONG)
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

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            profilePictureImageView.setImageURI(imageUri);
        }
    }

    private void uploadPicture(String UUID) {

        StorageReference profilePicsRef = storageReference.child("profilePictures/" + UUID);

        profilePicsRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    Toast.makeText(this, getResources().getString(R.string.imageUploadSuccess), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(this, MainLoginActivity.class));
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, getResources().getString(R.string.imageUploadFailure), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                })
                .addOnProgressListener(snapshot -> {
                    double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressBar.setProgress((int) Math.round(progressPercent));
                });

    }
}