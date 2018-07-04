package org.gratitude.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.gratitude.R;
import org.gratitude.databinding.ActivityLoginBinding;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String GOOGLE_TOS_URL = "https://www.google.com/policies/terms/";
    private static final String FIREBASE_TOS_URL = "https://firebase.google.com/terms/";
    private static final String GOOGLE_PRIVACY_POLICY_URL = "https://www.google.com/policies/privacy/";
    private static final String FIREBASE_PRIVACY_POLICY_URL = "https://firebase.google.com/terms/analytics/#7_privacy";

    public static final String SKIPPED = "SKIPPED";
    private static final int RC_SIGN_IN = 343;

    ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        mBinding.skipLogin.setOnClickListener(this);
        mBinding.makeLogin.setOnClickListener(this);

        mBinding.makeLogin.setText(R.string.btn_login);
        mBinding.skipLogin.setText(R.string.btn_skip);
        mBinding.textPowered.setText(R.string.login_powered);
    }

    public void signOut(Context context) {
        AuthUI.getInstance()
                .signOut(context)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // TODO: SIGN-OUT
                        } else {
                            //Timber.w("signOut:failure", task.getException());
                        }
                    }
                });
    }

    private void showSignInScreen() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setLogo(R.drawable.g)
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.EmailBuilder().build(),
                                new AuthUI.IdpConfig.PhoneBuilder().build(),
                                new AuthUI.IdpConfig.GoogleBuilder().build()))
                        .setTosAndPrivacyPolicyUrls(getSelectedTosUrl(),
                                getSelectedPrivacyPolicyUrl())
                        .setIsSmartLockEnabled(false, true)
                        .build(),
                RC_SIGN_IN);

    }

    private String getSelectedTosUrl() {
        /*if (mUseGoogleTos.isChecked()) {
            return GOOGLE_TOS_URL;
        }*/

        return FIREBASE_TOS_URL;
    }

    private String getSelectedPrivacyPolicyUrl() {
        /*if (mUseGooglePrivacyPolicy.isChecked()) {
            return GOOGLE_PRIVACY_POLICY_URL;
        }*/

        return FIREBASE_PRIVACY_POLICY_URL;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                finish();
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    return;
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.make_login){
            showSignInScreen();
        } else if(view.getId() == R.id.skip_login){
            Intent data = new Intent();
            data.putExtra(SKIPPED, SKIPPED);
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
