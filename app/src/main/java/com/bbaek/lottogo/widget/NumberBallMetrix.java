package com.bbaek.lottogo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bbaek.lottogo.R;
import com.bbaek.lottogo.utils.BBLogger;

/**
 * Created by woonsungbaek on 2016. 1. 20..
 */
public class NumberBallMetrix extends LinearLayout {
    private BBLogger logger = new BBLogger(NumberBallMetrix.class.getSimpleName());
    public static int METRIX_SIZE = 25;
    Context context;
    LayoutInflater Inflater;

    NumberBall[] balls = new NumberBall[METRIX_SIZE];

    public NumberBallMetrix(Context context) {
        super(context);
        this.context = context;
        setInit();
    }

    public NumberBallMetrix(final Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        setInit();
    }

    private void setInit() {
        Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = Inflater.inflate(R.layout.widget_number_ball_metrix, null);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 0);
        this.setLayoutParams(layoutParams);

        balls[0] = (NumberBall) view.findViewById(R.id.ball1);
        balls[1] = (NumberBall) view.findViewById(R.id.ball2);
        balls[2] = (NumberBall) view.findViewById(R.id.ball3);
        balls[3] = (NumberBall) view.findViewById(R.id.ball4);
        balls[4] = (NumberBall) view.findViewById(R.id.ball5);
        balls[5] = (NumberBall) view.findViewById(R.id.ball6);
        balls[6] = (NumberBall) view.findViewById(R.id.ball7);
        balls[7] = (NumberBall) view.findViewById(R.id.ball8);
        balls[8] = (NumberBall) view.findViewById(R.id.ball9);
        balls[9] = (NumberBall) view.findViewById(R.id.ball10);
        balls[10] = (NumberBall) view.findViewById(R.id.ball11);
        balls[11] = (NumberBall) view.findViewById(R.id.ball12);
        balls[12] = (NumberBall) view.findViewById(R.id.ball13);
        balls[13] = (NumberBall) view.findViewById(R.id.ball14);
        balls[14] = (NumberBall) view.findViewById(R.id.ball15);
        balls[15] = (NumberBall) view.findViewById(R.id.ball16);
        balls[16] = (NumberBall) view.findViewById(R.id.ball17);
        balls[17] = (NumberBall) view.findViewById(R.id.ball18);
        balls[18] = (NumberBall) view.findViewById(R.id.ball19);
        balls[19] = (NumberBall) view.findViewById(R.id.ball20);
        balls[20] = (NumberBall) view.findViewById(R.id.ball21);
        balls[21] = (NumberBall) view.findViewById(R.id.ball22);
        balls[22] = (NumberBall) view.findViewById(R.id.ball23);
        balls[23] = (NumberBall) view.findViewById(R.id.ball24);
        balls[24] = (NumberBall) view.findViewById(R.id.ball25);

        initBall();

        LayoutParams elementParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(view, elementParams);
    }

    public void initBall() {
        for (NumberBall ball : balls) {
            ball.setValue(0);
            ball.setVisibility(View.INVISIBLE);
        }
    }

    public void setBall(int index, int value) {
        balls[index].setValue(value);
        balls[index].setVisibility(VISIBLE);
    }
}