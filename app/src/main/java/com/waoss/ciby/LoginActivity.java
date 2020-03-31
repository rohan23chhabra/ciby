package com.waoss.ciby;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.*;
import com.google.gson.Gson;
import com.waoss.ciby.apis.UserType;
import com.waoss.ciby.firebase.FirebaseSession;
import com.waoss.ciby.utils.PojoUser;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    EditText phoneNumberField;
    EditText passwordField;
    CheckBox chooseAuthField;
    private ProgressDialog progressDialog;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private UserType userType;
    private LatLng location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneNumberField = findViewById(R.id.username);
        passwordField = findViewById(R.id.password);
        chooseAuthField = findViewById(R.id.choose_auth);

        userType = UserType.valueOf(getIntent().getExtras().getString("user-type"));
        location = (LatLng) getIntent().getExtras().get("location");

        verifyPhoneNumberHelper();
    }

    private void verifyPhoneNumberHelper() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeAutoRetrievalTimeOut(String verificationId) {
                if (progressDialog != null) {
                    dismissProgressDialog(progressDialog);
                }
                notifyUserAndRetry("Your Phone Number Verification is failed. Retry again!");
            }

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d("onVerificationCompleted", "onVerificationCompleted:" + credential);
                if (progressDialog != null) {
                    dismissProgressDialog(progressDialog);
                }
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w("onVerificationFailed", "onVerificationFailed", e);

                if (progressDialog != null) {
                    dismissProgressDialog(progressDialog);
                }

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Log.e("Exception:", "FirebaseAuthInvalidCredentialsException" + e);
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Log.e("Exception:", "FirebaseTooManyRequestsException" + e);

                }

                notifyUserAndRetry("Your Phone Number Verification is failed.Retry again!");
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                //for low level version which doesn't do aoto verififcation save the verification code and the token

                Log.d("onCodeSent", "onCodeSent:" + verificationId);
                Log.i("Verification code:", verificationId);
            }
        };
    }

    public ProgressDialog showProgressDialog(final Context mActivity, final String message, boolean isCancelable) {
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.show();
        progressDialog.setCancelable(isCancelable);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(message);
        return progressDialog;
    }

    public final void dismissProgressDialog(ProgressDialog progressDialog) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void notifyUserAndRetry(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        showLoginActivity();
                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> showLoginActivity());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    // TODO : Implement login properly
    public void loginOnClick(View view) {
        String phoneNumber = phoneNumberField.getText().toString();
        Log.d("phone-number", phoneNumber);

        // If checkbox is checked (register)
        // Then authenticate phone number and save session data in firebase.
        // Else (login)
        // Authenticate the user credentials from data saved in firebase.
        if (chooseAuthField.isChecked()) {
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                Intent intent = new Intent(this, userType == UserType.PRODUCER ? ProducerActivity.class : ConsumerActivity.class);
                intent.putExtra("location", location);
                intent.putExtra("sign-up", true);
                intent.putExtra("username", phoneNumber);
                startActivity(intent);
//                verifyPhoneNumber(phoneNumber);
//                showProgressDialog(this, "Sending a verification code", false);
            } else {
                showToast("Please enter a valid number to continue!");
            }
        } else {
            FirebaseSession session = new FirebaseSession(false);
            // TODO : Fetch location from location activity
            //boolean loginStatus = session.login(new PojoUserCredentials(phoneNumber, passwordField.getText().toString()));
        }
        // Sign in or register accordingly.
    }

    private void verifyPhoneNumber(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showLoginActivity() {
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
        return;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                        FirebaseUser user = task.getResult().getUser();
                        Log.d("Sign in with phone auth", "Success " + user);
                        Intent intent = new Intent(this, userType == UserType.PRODUCER ? ProducerActivity.class : ConsumerActivity.class);
                        intent.putExtra("location", location);
                        intent.putExtra("sign-up", true);
                        startActivity(intent);
                    } else {
                        notifyUserAndRetry("Your Phone Number Verification is failed.Retry again!");
                    }
                });
    }
}
