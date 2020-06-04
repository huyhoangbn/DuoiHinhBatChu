package com.example.duoihinhbatchu.view;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;

import com.example.duoihinhbatchu.Question;

public class ButtonAnswer extends AppCompatButton {

    private OnItemClickedListener mOnItemClickedListener;
    String msuggest;
    private ButtonQuestion buttonQuestion;
    private boolean isFill = false;
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isFill() {
        return isFill;
    }

    public void setFill(boolean fill) {
        isFill = fill;
    }

    public ButtonQuestion getButtonQuestion() {
        return buttonQuestion;
    }

    public void setButtonQuestion(ButtonQuestion buttonQuestion) {
        this.buttonQuestion = buttonQuestion;
    }

    public ButtonAnswer(final Context context, OnItemClickedListener onItemClickedListenerm, String sugguest) {
        super(context);

        mOnItemClickedListener = onItemClickedListenerm;
        msuggest = sugguest;

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickedListener.onClick(msuggest);


            }
        });
    }


    public interface OnItemClickedListener {
        void onClick(String sugguest);
    }


}
