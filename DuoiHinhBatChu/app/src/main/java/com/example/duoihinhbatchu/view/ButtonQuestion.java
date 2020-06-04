package com.example.duoihinhbatchu.view;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

public class ButtonQuestion extends AppCompatButton {
    String mquestion;
    private  ButtonAnswer buttonAnswer;

    public ButtonAnswer getButtonAnswer() {
        return buttonAnswer;
    }

    public void setButtonAnswer(ButtonAnswer buttonAnswer) {
        this.buttonAnswer = buttonAnswer;
    }

    private OnItemClickListener mOnClickListener;

    public ButtonQuestion(Context context, OnItemClickListener onClickListener, String question) {
        super(context);
        mquestion = question;
        mOnClickListener = onClickListener;
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.onClick(mquestion);
            }
        });
    }

    public interface OnItemClickListener{
        void onClick(String questions);
    }
}
