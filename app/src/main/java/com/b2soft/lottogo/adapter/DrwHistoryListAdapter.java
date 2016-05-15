package com.b2soft.lottogo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.b2soft.lottogo.R;
import com.b2soft.lottogo.model.Lotto;
import com.b2soft.lottogo.utils.PreferenceUtils;
import com.b2soft.lottogo.widget.NumberBall;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by woonsungbaek on 2016. 4. 26..
 */
public class DrwHistoryListAdapter extends BaseAdapter {
    private Context context;
    private int layoutId;
    private List<Lotto> lottoList;
    private LayoutInflater Inflater;

    public DrwHistoryListAdapter(Context context, List<Lotto> lottoList) {
        this.lottoList = lottoList;
        this.context = context;
        this.layoutId = R.layout.layout_drwhistory_list;
        Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lottoList.size();
    }

    @Override
    public Lotto getItem(int position) {
        return lottoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lottoList.get(position).getDrwNo();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final RankListViewHoler holder;
        if (view == null) {
            view = Inflater.inflate(layoutId, parent, false);
            holder = new RankListViewHoler(view);
            view.setTag(holder);
        } else {
            holder = (RankListViewHoler) view.getTag();
        }

        holder.drwNo.setText("" + lottoList.get(position).getDrwNo());
        holder.drwNoDate.setText("" + lottoList.get(position).getDrwNoDate());
        holder.drwtNo1.setValue(lottoList.get(position).getDrwtNo1());
        holder.drwtNo2.setValue(lottoList.get(position).getDrwtNo2());
        holder.drwtNo3.setValue(lottoList.get(position).getDrwtNo3());
        holder.drwtNo4.setValue(lottoList.get(position).getDrwtNo4());
        holder.drwtNo5.setValue(lottoList.get(position).getDrwtNo5());
        holder.drwtNo6.setValue(lottoList.get(position).getDrwtNo6());
        holder.drwtBunsNo.setValue(lottoList.get(position).getBnusNo());

        return view;
    }

    class RankListViewHoler {
        @Bind(R.id.drwtNo1List) NumberBall drwtNo1;
        @Bind(R.id.drwtNo2List) NumberBall drwtNo2;
        @Bind(R.id.drwtNo3List) NumberBall drwtNo3;
        @Bind(R.id.drwtNo4List) NumberBall drwtNo4;
        @Bind(R.id.drwtNo5List) NumberBall drwtNo5;
        @Bind(R.id.drwtNo6List) NumberBall drwtNo6;
        @Bind(R.id.drwtBunsNoList) NumberBall drwtBunsNo;
        @Bind(R.id.drwDateList) TextView drwNoDate;
        @Bind(R.id.drwNoList) TextView drwNo;

        public RankListViewHoler(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
