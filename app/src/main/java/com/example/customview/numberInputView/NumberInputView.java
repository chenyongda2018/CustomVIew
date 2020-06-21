package com.example.customview.numberInputView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.customview.R;

import androidx.annotation.Nullable;

public class NumberInputView extends LinearLayout {

    private static final String TAG = "NumberInputView";
    private int mStep;
    private int mCurrentNum = 0;
    private TextView mMinusBtn, mPlusBtn;
    private EditText mNumberView;
    private OnNumberValueChangedListener mNumberValueChangedListener;
    private int mMinValue;
    private int mMaxValue;
    private boolean mDisabled;
    private int mDefaultValue;

    public interface OnNumberValueChangedListener {
        void onNumberValueChanged(int value);

        void onNumberValueMax(int value);

        void onNumberValueMin(int value);
    }

    public NumberInputView(Context context) {
        this(context, null);
    }

    public NumberInputView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public NumberInputView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        LayoutInflater.from(context).inflate(R.layout.input_number_layout, this, true);

        initValues(context, attrs);
        initView();


    }

    //获取xml中的值
    private void initValues(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NumberInputView);
        mMinValue = ta.getInteger(R.styleable.NumberInputView_min, 0);
        mMaxValue = ta.getInteger(R.styleable.NumberInputView_max, 0);
        mStep = ta.getInteger(R.styleable.NumberInputView_step, 1);
        mDisabled = ta.getBoolean(R.styleable.NumberInputView_disabled, false);
        mDefaultValue = ta.getInt(R.styleable.NumberInputView_defaultValue, 0);
        mCurrentNum = mDefaultValue;
        ta.recycle();
    }

    private void initView() {
        mMinusBtn = this.findViewById(R.id.number_input_view_minus_btn);
        mPlusBtn = this.findViewById(R.id.number_input_view_plus_btn);
        mNumberView = this.findViewById(R.id.number_input_view_number_et);
        mNumberView.setText(String.valueOf(mDefaultValue));
        mMinusBtn.setEnabled(!mDisabled);
        mPlusBtn.setEnabled(!mDisabled);
        updateNumberText();
        setTouchEvent();
    }

    private void setTouchEvent() {
        mMinusBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentNum -= mStep;
                mPlusBtn.setEnabled(true);
                if (mMinValue != 0 && mCurrentNum <= mMinValue) {
                    mMinusBtn.setEnabled(false);
                    mCurrentNum = mMinValue;
                    mNumberValueChangedListener.onNumberValueMin(mMinValue);
                }
                updateNumberText();
            }
        });

        mPlusBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentNum += mStep;
                mMinusBtn.setEnabled(true);
                if (mMaxValue != 0 && mCurrentNum >= mMaxValue) {
                    mPlusBtn.setEnabled(false);
                    mCurrentNum = mMaxValue;
                    mNumberValueChangedListener.onNumberValueMax(mMaxValue);
                }
                updateNumberText();
            }
        });
    }


    private void updateNumberText() {
        mNumberView.setText(String.valueOf(mCurrentNum));
        if (mNumberValueChangedListener != null) {
            mNumberValueChangedListener.onNumberValueChanged(mCurrentNum);
        }

    }

    public int getNum() {
        return mCurrentNum;
    }

    public void setNum(int currentNum) {
        mCurrentNum = currentNum;
    }

    public int getStep() {
        return mStep;
    }

    public void setStep(int step) {
        mStep = step;
    }

    public int getMinValue() {
        return mMinValue;
    }

    public void setMinValue(int minValue) {
        mMinValue = minValue;
    }

    public int getMaxValue() {
        return mMaxValue;
    }

    public void setMaxValue(int maxValue) {
        mMaxValue = maxValue;
    }

    public boolean isDisabled() {
        return mDisabled;
    }

    public void setDisabled(boolean disabled) {
        mDisabled = disabled;
    }

    public int getDefaultValue() {
        return mDefaultValue;
    }

    public void setDefaultValue(int defaultValue) {
        mDefaultValue = defaultValue;
    }

    public int getCurrentNum() {
        return mCurrentNum;
    }

    public void setCurrentNum(int currentNum) {
        mCurrentNum = currentNum;
        updateNumberText();
    }

    public OnNumberValueChangedListener getNumberValueChangedListener() {
        return mNumberValueChangedListener;
    }

    public void setNumberValueChangedListener(OnNumberValueChangedListener numberValueChangedListener) {
        mNumberValueChangedListener = numberValueChangedListener;
    }
}
