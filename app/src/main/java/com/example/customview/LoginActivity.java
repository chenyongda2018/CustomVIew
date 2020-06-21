package com.example.customview;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.customview.loginPageView.LoginPageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private LoginPageView mLoginPageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_page);
        mLoginPageView = findViewById(R.id.login_page_view);
        mLoginPageView.setOnLoginPageViewClickedListener(new LoginPageView.OnLoginPageViewClickedListener() {
            @Override
            public void onGetVerifyCodeClicked(String verifyCode) {

            }

            @Override
            public void onAgreementClickedAction() {
                //打开协议
            }

            @Override
            public void onLoginConfirmClicked(String phoneNum, String verifyCode) {

            }
        });
    }
}
