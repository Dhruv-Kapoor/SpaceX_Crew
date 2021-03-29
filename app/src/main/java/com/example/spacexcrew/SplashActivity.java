package com.example.spacexcrew;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            ImageView launcherIcon = findViewById(R.id.ivLauncherIcon);
            startActivity(
                    new Intent(this, MainActivity.class),
                    ActivityOptions.makeSceneTransitionAnimation(
                            (Activity) this,
                            launcherIcon,
                            getString(R.string.launcher_transition)
                    ).toBundle()
            );
        }, 1000);
    }

}