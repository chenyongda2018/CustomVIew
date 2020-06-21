package com.example.customview.loginPageView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.customview.R;

import androidx.annotation.Nullable;

public class LoginKeyBoard extends LinearLayout implements View.OnClickListener {

    private static final String TAG = LoginKeyBoard.class.getSimpleName();
    private OnKeyPadBtnClickListener mOnKeyPadBtnClickListener;

    public LoginKeyBoard(Context context) {
        this(context, null);
    }

    public LoginKeyBoard(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoginKeyBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.login_key_pad_layout, this);

        initView();
    }

    private void initView() {
        findViewById(R.id.key_pad_num_0).setOnClickListener(this);
        findViewById(R.id.key_pad_num_1).setOnClickListener(this);
        findViewById(R.id.key_pad_num_2).setOnClickListener(this);
        findViewById(R.id.key_pad_num_3).setOnClickListener(this);
        findViewById(R.id.key_pad_num_4).setOnClickListener(this);
        findViewById(R.id.key_pad_num_5).setOnClickListener(this);
        findViewById(R.id.key_pad_num_6).setOnClickListener(this);
        findViewById(R.id.key_pad_num_7).setOnClickListener(this);
        findViewById(R.id.key_pad_num_8).setOnClickListener(this);
        findViewById(R.id.key_pad_num_9).setOnClickListener(this);
        findViewById(R.id.key_pad_num_delete).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (v instanceof TextView) {
            Log.d(TAG, "clicked --> " + ((TextView) v).getText());
        }
        if (mOnKeyPadBtnClickListener != null) {
            if (viewId == R.id.key_pad_num_delete) {
                mOnKeyPadBtnClickListener.onKeyDeleteClicked();
            } else {
                String num = ((TextView) v).getText().toString();
                mOnKeyPadBtnClickListener.onKeyNumberClicked(Integer.parseInt(num));
            }

        }

    }


    public interface OnKeyPadBtnClickListener {
        void onKeyNumberClicked(int number);

        void onKeyDeleteClicked();

    }

    public OnKeyPadBtnClickListener getOnKeyPadBtnClickListener() {
        return mOnKeyPadBtnClickListener;
    }

    public void setOnKeyPadBtnClickListener(OnKeyPadBtnClickListener onKeyPadBtnClickListener) {
        mOnKeyPadBtnClickListener = onKeyPadBtnClickListener;
    }
}
