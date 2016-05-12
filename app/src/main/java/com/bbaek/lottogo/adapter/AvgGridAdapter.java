package com.bbaek.lottogo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bbaek.lottogo.R;
import com.bbaek.lottogo.activity.setting.InExcludeModel;
import com.bbaek.lottogo.model.my.InExcludeNo;
import com.bbaek.lottogo.utils.BBLogger;
import com.bbaek.lottogo.widget.NumberBall;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by woonsungbaek on 2016. 4. 28..
 */
public class AvgGridAdapter extends BaseAdapter {
    private BBLogger logger = new BBLogger(getClass().getSimpleName());

    Context context;
    private LayoutInflater inflater;
    private int layoutId;
    private List<String[]> datas;

    public AvgGridAdapter(Context context, List<String[]> datas) {
        this.context = context;
        this.layoutId = R.layout.layout_avg_grid;
        this.datas = datas;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setItem(List<String[]> item) {
        this.datas = item;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final AvgGridViewHolder holder;
        if (view == null) {
            view = inflater.inflate(layoutId, parent, false);
            holder = new AvgGridViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (AvgGridViewHolder) view.getTag();
        }

        final int pos = position;
        holder.title.setText(datas.get(pos)[0]);
        holder.value.setText(datas.get(pos)[1]);

        return view;
    }

    class AvgGridViewHolder {
        @Bind(R.id.avgGridTitle) TextView title;
        @Bind(R.id.avgGridValue) TextView value;

        public AvgGridViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
