package com.bbaek.lottogo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bbaek.lottogo.R;
import com.bbaek.lottogo.model.Lotto;
import com.bbaek.lottogo.utils.PreferenceUtils;
import com.bbaek.lottogo.widget.NumberBall;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by woonsungbaek on 2016. 4. 26..
 */
public class RankListAdapter  extends BaseAdapter {
    private Context context;
    private int layoutId;
    private List<Lotto> lottoList;
    private List<Integer> lottoNo;
    private LayoutInflater Inflater;

    public RankListAdapter(Context context, List<Lotto> lottoList) {
        this.lottoList = lottoList;
        this.context = context;
        this.lottoNo = new ArrayList<>();
        setNo();
        this.layoutId = R.layout.layout_rank_list;
        Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    protected void setNo() {
        lottoNo.add(PreferenceUtils.instance(context).getDrwtNo1());
        lottoNo.add(PreferenceUtils.instance(context).getDrwtNo2());
        lottoNo.add(PreferenceUtils.instance(context).getDrwtNo3());
        lottoNo.add(PreferenceUtils.instance(context).getDrwtNo4());
        lottoNo.add(PreferenceUtils.instance(context).getDrwtNo5());
        lottoNo.add(PreferenceUtils.instance(context).getDrwtNo6());
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
            holder = new RankListViewHoler();
            holder.rank = (TextView) view.findViewById(R.id.rankList);
            holder.drwNo = (TextView) view.findViewById(R.id.drwNoList);
            holder.drwNoDate = (TextView) view.findViewById(R.id.drwDateList);
            holder.drwtNo1 = (NumberBall) view.findViewById(R.id.drwtNo1List);
            holder.drwtNo2 = (NumberBall) view.findViewById(R.id.drwtNo2List);
            holder.drwtNo3 = (NumberBall) view.findViewById(R.id.drwtNo3List);
            holder.drwtNo4 = (NumberBall) view.findViewById(R.id.drwtNo4List);
            holder.drwtNo5 = (NumberBall) view.findViewById(R.id.drwtNo5List);
            holder.drwtNo6 = (NumberBall) view.findViewById(R.id.drwtNo6List);
            holder.drwtBunsNo = (NumberBall) view.findViewById(R.id.drwtBunsNoList);
            view.setTag(holder);
        } else {
            holder = (RankListViewHoler) view.getTag();
        }

        holder.rank.setText("" + lottoList.get(position).getRank());
        holder.drwNo.setText("" + lottoList.get(position).getDrwNo());
        holder.drwNoDate.setText("" + lottoList.get(position).getDrwNoDate());
        boolean chkNo1 = lottoNo.contains(lottoList.get(position).getDrwtNo1());
        boolean chkNo2 = lottoNo.contains(lottoList.get(position).getDrwtNo2());
        boolean chkNo3 = lottoNo.contains(lottoList.get(position).getDrwtNo3());
        boolean chkNo4 = lottoNo.contains(lottoList.get(position).getDrwtNo4());
        boolean chkNo5 = lottoNo.contains(lottoList.get(position).getDrwtNo5());
        boolean chkNo6 = lottoNo.contains(lottoList.get(position).getDrwtNo6());
        boolean chkNoBuns = lottoNo.contains(lottoList.get(position).getBnusNo());
        holder.drwtNo1.setValue(lottoList.get(position).getDrwtNo1(), chkNo1);
        holder.drwtNo2.setValue(lottoList.get(position).getDrwtNo2(), chkNo2);
        holder.drwtNo3.setValue(lottoList.get(position).getDrwtNo3(), chkNo3);
        holder.drwtNo4.setValue(lottoList.get(position).getDrwtNo4(), chkNo4);
        holder.drwtNo5.setValue(lottoList.get(position).getDrwtNo5(), chkNo5);
        holder.drwtNo6.setValue(lottoList.get(position).getDrwtNo6(), chkNo6);
        holder.drwtBunsNo.setValue(lottoList.get(position).getBnusNo(), chkNoBuns);
        holder.drwtNo1.setSelected(chkNo1);
        holder.drwtNo2.setSelected(chkNo2);
        holder.drwtNo3.setSelected(chkNo3);
        holder.drwtNo4.setSelected(chkNo4);
        holder.drwtNo5.setSelected(chkNo5);
        holder.drwtNo6.setSelected(chkNo6);
        holder.drwtBunsNo.setSelected(chkNoBuns);

        return view;
    }

    class RankListViewHoler {
        NumberBall drwtNo1;
        NumberBall drwtNo2;
        NumberBall drwtNo3;
        NumberBall drwtNo4;
        NumberBall drwtNo5;
        NumberBall drwtNo6;
        NumberBall drwtBunsNo;
        TextView drwNoDate;
        TextView drwNo;
        TextView rank;
    }
}
