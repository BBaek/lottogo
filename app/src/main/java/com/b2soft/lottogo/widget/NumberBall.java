package com.b2soft.lottogo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.b2soft.lottogo.R;
import com.b2soft.lottogo.utils.BBLogger;

/**
 * Created by woonsungbaek on 2016. 1. 20..
 */
public class NumberBall extends LinearLayout {
    private BBLogger logger = new BBLogger(NumberBall.class.getSimpleName());
    Context context;
    LayoutInflater Inflater;

    TypedArray attr;

    int value = 0;
    boolean selected = true;
    boolean districted = false;
    TextView number;
    ImageView background;

    public NumberBall(Context context, int value) {
        super(context);
        this.context = context;
        this.value = value;
        setInit();
    }

    public NumberBall(final Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        attr = context.obtainStyledAttributes(attributeSet, R.styleable.NumberBall);
        setInit();
    }

    private void setInit() {
        Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;

        switch (attr.getInt(R.styleable.NumberBall_ballSize, 0)) {
            case 1:
                view = Inflater.inflate(R.layout.widget_number_ball_small, null);
                break;
            case 2:
                view = Inflater.inflate(R.layout.widget_number_ball_large, null);
                break;
            default:
                view = Inflater.inflate(R.layout.widget_number_ball, null);
                break;
        }

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 0);
        this.setLayoutParams(layoutParams);

        // init the individual elements
        number = (TextView) view.findViewById(R.id.ballNumber);
        background = (ImageView) view.findViewById(R.id.ballBg);
        setValue(0);

        LayoutParams elementParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(view, elementParams);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        if (selected) {
            setValue(getValue());
        } else {
            background.setBackground(context.getResources().getDrawable(R.drawable.bg_round_gray));
        }
    }

    public boolean isDistricted() {
        return districted;
    }

    public void setDistricted(boolean districted) {
        this.districted = districted;
        if (districted) {
            background.setImageDrawable(context.getDrawable(R.drawable.ic_district_line));
        } else {
            background.setImageDrawable(null);
        }
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        setValue(value, false);
    }

    public void setValue(int value, boolean bold) {
        this.value = value;
        number.setText("" + this.value);
        if (bold) {
            number.setTypeface(null, Typeface.BOLD);
        } else {
            number.setTypeface(null, Typeface.NORMAL);
        }

        if (value < 11) {
            background.setBackground(context.getResources().getDrawable(R.drawable.bg_round_yellow));
        } else if (value >= 11 && value < 21) {
            background.setBackground(context.getResources().getDrawable(R.drawable.bg_round_blue));
        } else if (value >= 21 && value < 31) {
            background.setBackground(context.getResources().getDrawable(R.drawable.bg_round_red));
        } else if (value >= 31 && value < 41) {
            background.setBackground(context.getResources().getDrawable(R.drawable.bg_round_purple));
        } else {
            background.setBackground(context.getResources().getDrawable(R.drawable.bg_round_green));
        }
    }
}