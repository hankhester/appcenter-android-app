package com.example.appcenterdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.auth.Auth;
import com.microsoft.appcenter.auth.SignInResult;
import com.microsoft.appcenter.crashes.Crashes;
import com.microsoft.appcenter.data.Data;
import com.microsoft.appcenter.push.Push;
import com.microsoft.appcenter.utils.async.AppCenterConsumer;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;

import java.text.ParseException;

public class MainActivity extends AppCompatActivity {
    TextView nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Push.setListener(new MyPushListener());
        AppCenter.start(getApplication(), "097c990d-1b63-4a16-a4f6-b2bb8250983a", Analytics.class, Crashes.class, Auth.class, Push.class, Data.class);
        nameText = findViewById(R.id.userName);
        signIn();
    }

    public void goToNotes(View view) {
        startActivity(new Intent(this, NotesActivity.class));
    }

    private void signIn() {
        Auth.signIn().thenAccept(new AppCenterConsumer<SignInResult>() {
            @Override
            public void accept(SignInResult signInResult) {
                if (signInResult.getException() == null) {
                    // success
                    try {
                        String idToken = signInResult.getUserInformation().getIdToken();
                        JWT parsedToken = JWTParser.parse(idToken);
                        String name = (String) parsedToken.getJWTClaimsSet().getClaim("given_name");
                        TextView signingInText = findViewById(R.id.signingInText);
                        ProgressBar progressBar = findViewById(R.id.signingInProgress);
                        signingInText.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        nameText.setText("Hi, " + name);
                        Button signOutButton = findViewById(R.id.signOutButton);
                        signOutButton.setVisibility(View.VISIBLE);
                        Button notesButton = findViewById(R.id.notesButton);
                        notesButton.setVisibility(View.VISIBLE);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    // failure
                    signInResult.getException().printStackTrace();
                }
            }
        });
    }

    public void signOut(View view) {
        Auth.signOut();
        nameText.setText("");
    }
}
