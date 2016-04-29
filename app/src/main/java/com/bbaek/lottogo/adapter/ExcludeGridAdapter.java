package com.bbaek.lottogo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bbaek.lottogo.R;
import com.bbaek.lottogo.activity.setting.InExcludeModel;
import com.bbaek.lottogo.model.my.InExcludeNo;
import com.bbaek.lottogo.utils.BBLogger;
import com.bbaek.lottogo.widget.NumberBall;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by woonsungbaek on 2016. 4. 28..
 */
public class ExcludeGridAdapter extends BaseAdapter {
    private BBLogger logger = new BBLogger(getClass().getSimpleName());

    Context context;
    private LayoutInflater inflater;
    private int layoutId;
    private Map<Integer, InExcludeNo> datas;
    private InExcludeModel inExcludeModel;

    public ExcludeGridAdapter(Context context) {
        this.context = context;
        this.layoutId = R.layout.layout_ball_grid;
        datas = new HashMap<>();
        inExcludeModel = new InExcludeModel(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initData();
    }

    public void initData() {
        datas = inExcludeModel.getInitData();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position + 1);
    }

    @Override
    public long getItemId(int position) {
        return datas.get(position + 1).getNo();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ExcludeGridViewHolder holder;
        if (view == null) {
            view = inflater.inflate(layoutId, parent, false);
            holder = new ExcludeGridViewHolder();
            holder.ball = (NumberBall) view.findViewById(R.id.gridBall);
            view.setTag(holder);
        } else {
            holder = (ExcludeGridViewHolder) view.getTag();
        }

        final int pos = position + 1;
        holder.ball.setValue(pos);
        holder.ball.setSelected(datas.get(pos).isExclude());
        holder.ball.setDistricted(datas.get(pos).isInclude());
        holder.ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!datas.get(pos).isInclude()) {
                    boolean nowSelected = holder.ball.isSelected();
                    logger.debug("onClick " + pos + "/" + nowSelected);
                    inExcludeModel.updateExcludedBalls(pos, !nowSelected);
                    datas.put(pos, inExcludeModel.selectInExcludeNo(pos));
                    notifyDataSetChanged();
                }
            }
        });

        return view;
    }

    class ExcludeGridViewHolder {
        NumberBall ball;
    }
}
