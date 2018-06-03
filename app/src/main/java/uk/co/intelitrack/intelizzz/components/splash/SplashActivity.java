package uk.co.intelitrack.intelizzz.components.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.components.login.LoginActivity;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splash_logo)
    ImageView splashLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        Animation fadeInOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_out_animation);
        splashLogo.startAnimation(fadeInOutAnimation);

        new Handler(getMainLooper()).postDelayed(() -> {
            LoginActivity.start(SplashActivity.this);
            finish();
        }, 3000);

    }
}