package com.creative_share_apps.wow.activities_fragments.activity_splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.creative_share_apps.wow.R;
import com.creative_share_apps.wow.activities_fragments.activity_home.client_home.activity.ClientHomeActivity;
import com.creative_share_apps.wow.activities_fragments.activity_sign_in.activity.SignInActivity;
import com.creative_share_apps.wow.language.Language_Helper;
import com.creative_share_apps.wow.models.UserModel;
import com.creative_share_apps.wow.preferences.Preferences;
import com.creative_share_apps.wow.singletone.UserSingleTone;
import com.creative_share_apps.wow.tags.Tags;

public class SplashActivity extends AppCompatActivity {

    private FrameLayout fl;
    private Preferences preferences;

    private String current_lang;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(Language_Helper.updateResources(base,Language_Helper.getLanguage(base)));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        preferences = Preferences.getInstance();
        fl = findViewById(R.id.fl);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.fade);
        fl.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                String session = preferences.getSession(SplashActivity.this);

                if (session.equals(Tags.session_login))
                {
                    UserModel userModel = preferences.getUserData(SplashActivity.this);
                    UserSingleTone userSingleTone = UserSingleTone.getInstance();
                    userSingleTone.setUserModel(userModel);

                    Intent intent = new Intent(SplashActivity.this, ClientHomeActivity.class);
                    startActivity(intent);
                    finish();
                }else
                    {
                        Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                        startActivity(intent);
                        finish();
                    }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
