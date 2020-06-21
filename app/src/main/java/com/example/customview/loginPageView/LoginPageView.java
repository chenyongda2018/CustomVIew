package com.example.customview.loginPageView;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.customview.R;

import androidx.annotation.Nullable;

public class LoginPageView extends FrameLayout implements View.OnClickListener {

    private static final String TAG = "LoginPageView";
    private EditText mPhoneNumEt;
    private EditText mVerifyCodeEt;
    private CheckBox mProtocolCb;
    public static final int DEFAULT_VERIFY_CODE_LENGTH = 4;
    private int mMainColor;
    private int mVerifyCodeLength;
    private TextView mConfirmLoginBtn;
    private TextView mUserAgreementTv;
    private OnLoginPageViewClickedListener mOnLoginPageViewClickedListener;
    private TextView mGetVerifyCodeBtn;
    private String mVerifyCode = "";
    private LoginKeyBoard mKeyBoard;
    private boolean isPhoneOk, isVerifyCodeOk, isProtocolCheck;
    private static final int COUNTDOWN_DURATION_DEFAULT = 60 * 1000;
    private static final int COUNTDOWN_STEP_DEFAULT = 1000;
    private int mColdDownTime;
    private boolean mIsCounting;
    private CountDownTimer mCountDownTimer;

    public LoginPageView(Context context) {
        this(context, null);
    }

    public LoginPageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoginPageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs);
        initTouchEvent();
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.login_page_view, this);
        //手机号码输入框
        mPhoneNumEt = this.findViewById(R.id.login_phone_number_et);
        //获取验证码按钮
        mGetVerifyCodeBtn = this.findViewById(R.id.get_verify_code_btn);
        //验证码编辑框
        mVerifyCodeEt = this.findViewById(R.id.login_verify_code_et);
        //协议
        mProtocolCb = this.findViewById(R.id.user_protocol_check_box);
        //确定登录
        mConfirmLoginBtn = this.findViewById(R.id.login_confirm_btn);
        mUserAgreementTv = this.findViewById(R.id.user_protocol_tv);
        //数字键盘
        mKeyBoard = this.findViewById(R.id.login_view_key_pad);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LoginPageView);
        mMainColor = ta.getColor(R.styleable.LoginPageView_mainColor, getResources().getColor(R.color.loginKeyPadLightBrown));
        mVerifyCodeLength = ta.getInt(R.styleable.LoginPageView_verifyCodeLength, DEFAULT_VERIFY_CODE_LENGTH);
        mColdDownTime = ta.getInt(R.styleable.LoginPageView_verifyCodeCodeDownTime, COUNTDOWN_DURATION_DEFAULT);

        if (mMainColor != -1) {
            mUserAgreementTv.setTextColor(mMainColor);
        }

        ta.recycle();
        updateView();
    }

    private void initTouchEvent() {
        mGetVerifyCodeBtn.setOnClickListener(this);
        mConfirmLoginBtn.setOnClickListener(this);
        mUserAgreementTv.setOnClickListener(this);
        //禁止弹出软键盘
        mPhoneNumEt.setShowSoftInputOnFocus(false);
        mVerifyCodeEt.setShowSoftInputOnFocus(false);

        mKeyBoard.setOnKeyPadBtnClickListener(new LoginKeyBoard.OnKeyPadBtnClickListener() {
            @Override
            public void onKeyNumberClicked(int number) {
                EditText etView = getFocusView();
                if (etView != null) {
                    Editable text = etView.getText();
                    int selection = etView.getSelectionEnd();
                    text.insert(selection, String.valueOf(number));
                }
            }

            @Override
            public void onKeyDeleteClicked() {
                EditText etView = getFocusView();
                if (etView != null) {
                    Editable text = etView.getText();
                    int selection = etView.getSelectionEnd();
                    if (selection > 0) {
                        text.delete(selection - 1, selection);
                    }
                }
            }
        });
        mPhoneNumEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Editable editable = mPhoneNumEt.getText();
                isPhoneOk = (editable.length() == 11);
                updateView();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mVerifyCodeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Editable editable = mVerifyCodeEt.getText();
                isVerifyCodeOk = (editable.length() == DEFAULT_VERIFY_CODE_LENGTH);
                updateView();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mProtocolCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isProtocolCheck = isChecked;
                updateView();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (mOnLoginPageViewClickedListener == null) return;
        switch (v.getId()) {
            case R.id.get_verify_code_btn:
                startCountDown();
                mOnLoginPageViewClickedListener.onGetVerifyCodeClicked(getVerifyCode());
                break;
            case R.id.login_confirm_btn:
                mOnLoginPageViewClickedListener.onLoginConfirmClicked(getPhoneNum(), getVerifyCode());
                break;
            case R.id.user_protocol_tv:
                mOnLoginPageViewClickedListener.onAgreementClickedAction();
                break;
        }
    }

    private String getVerifyCode() {
        return mVerifyCodeEt.getText().toString();
    }

    private String getPhoneNum() {
        return mPhoneNumEt.getText().toString();
    }


    public interface OnLoginPageViewClickedListener {
        void onGetVerifyCodeClicked(String verifyCode);

        void onAgreementClickedAction();

        void onLoginConfirmClicked(String phoneNum, String verifyCode);
    }

    public void setOnLoginPageViewClickedListener(OnLoginPageViewClickedListener onLoginPageViewClickedListener) {
        mOnLoginPageViewClickedListener = onLoginPageViewClickedListener;
    }


    private EditText getFocusView() {
        View view = this.findFocus();
        if (view instanceof EditText) {
            return ((EditText) view);
        }
        return null;
    }

    private void updateView() {
        Log.d(TAG, "isPhoneOk ---> " + isPhoneOk);
        Log.d(TAG, "isProtocolCheck ---> " + isProtocolCheck);
        Log.d(TAG, "isVerifyCodeOk ---> " + isVerifyCodeOk);
        mGetVerifyCodeBtn.setEnabled(isPhoneOk && !mIsCounting);
        mConfirmLoginBtn.setEnabled(isPhoneOk && isProtocolCheck && isVerifyCodeOk);
    }

    private void startCountDown() {
        mCountDownTimer = new CountDownTimer(mColdDownTime, COUNTDOWN_STEP_DEFAULT) {

            @Override
            public void onTick(long millisUntilFinished) {
                //更新UI
                mIsCounting = true;
                mGetVerifyCodeBtn.setEnabled(false);
                mGetVerifyCodeBtn.setText("(剩余" + (millisUntilFinished / 1000) + "秒)");
                Log.d(TAG, "remaining time ==> " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                //倒计时完毕
                mIsCounting = false;
                mGetVerifyCodeBtn.setEnabled(true);
                mGetVerifyCodeBtn.setText("获取验证码");
            }
        }.start();
    }

    public void onVerifyCodeError() {
        if(mIsCounting && mCountDownTimer != null) {
            mVerifyCodeEt.getText().clear();
            mIsCounting = false;
            mCountDownTimer.cancel();
            mCountDownTimer.onFinish();
        }
    }

}
