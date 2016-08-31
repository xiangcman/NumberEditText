package com.library.dynamicinput;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 * Created by xiangcheng on 16/8/29.
 */
public class DynamicInputView extends RelativeLayout {

    private NumberView mNumberView;
    private EditText mEditText;
    private int maxNum;
    private String mHint;
    private float mTextSize;
    private int mTextColor;

    public DynamicInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray arrays = context.obtainStyledAttributes(attrs, R.styleable.DynamicInputView);
        maxNum = arrays.getInt(R.styleable.DynamicInputView_max_number, 100);
        mHint = arrays.getString(R.styleable.DynamicInputView_hint_text);
        mTextSize = arrays.getDimension(R.styleable.DynamicInputView_text_size, 12);
        mTextColor = arrays.getColor(R.styleable.DynamicInputView_text_color, Color.parseColor("#88000000"));
        initViews();
    }

    public DynamicInputView(Context context) {
        super(context);
        initViews();
    }

    private void initViews() {
        setBackgroundResource(R.drawable.edittext_back);
        /**init numberView*/
        mNumberView = new NumberView(getContext(), maxNum);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        layoutParams.setMargins(Utils.dp2px(getContext(), 4), Utils.dp2px(getContext(), 4),
                Utils.dp2px(getContext(), 4), Utils.dp2px(getContext(), 4));
        addView(mNumberView, layoutParams);
        mNumberView.setLeftNum(0);
        /**init editText*/
        mEditText = new EditText(getContext());
        RelativeLayout.LayoutParams editParams = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        editParams.setMargins(0, 0, 0, getMinHeight() + Utils.dp2px(getContext(), 3));
        mEditText.setPadding(Utils.dp2px(getContext(), 3), Utils.dp2px(getContext(), 3),
                Utils.dp2px(getContext(), 3), Utils.dp2px(getContext(), 3));
        mEditText.setBackground(null);
        mEditText.setGravity(Gravity.TOP);
        mEditText.setHint(mHint);
        mEditText.setTextSize(mTextSize);
        mEditText.setTextColor(mTextColor);
        mEditText.addTextChangedListener(mTextWatcher);
        addView(mEditText, editParams);
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        private int start;
        private int end;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            start = mEditText.getSelectionStart();
            end = mEditText.getSelectionEnd();
            mEditText.removeTextChangedListener(mTextWatcher);
            while (calculateLength(s.toString()) > maxNum) {
                s.delete(start - 1, end);
                start--;
                end--;
            }
            mEditText.setText(s);
            mEditText.setSelection(end);
            mEditText.addTextChangedListener(mTextWatcher);
            mNumberView.setLeftNum((int) calculateLength(s.toString()));
        }
    };

    /**
     * 最小的宽度以右下角显示的文本的宽度为标准
     *
     * @return
     */
    private int getMinWidth() {
        return mNumberView.getMeWidth();
    }

    private int getMinHeight() {
        return mNumberView.getMeHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(setMeasure(widthMeasureSpec, true), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(setMeasure(heightMeasureSpec, false), MeasureSpec.EXACTLY));
    }

    private int setMeasure(int measureSpec, boolean isWidth) {
        int mode = MeasureSpec.getMode(measureSpec);
        int result = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            if (isWidth) {
                //如果比右下角的宽度还要窄
                if (result < getMinWidth() * 6) {
                    return getMinWidth() * 6;
                } else {
                    return result;
                }
            } else {
                //如果比右下角的宽度还要窄
                if (result < getMinHeight() * 3) {
                    return getMinHeight() * 3;
                } else {
                    return result;
                }
            }
        } else {
            if (isWidth) {
                result = getMinWidth() * 6;
            } else {
                result = getMinHeight() * 3;
            }
        }
        return result;
    }

    private long calculateLength(CharSequence str) {
        double len = 0;
        for (int i = 0; i < str.length(); i++) {
            len++;
        }
        return Math.round(len);
    }

    public String getText() {
        if (mEditText != null) {
            return mEditText.getText().toString();
        }
        return "";
    }
}
