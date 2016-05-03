package com.bbaek.lottogo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bbaek.lottogo.R;
import com.bbaek.lottogo.model.Lotto;
import com.bbaek.lottogo.model.my.DrwtNos;
import com.bbaek.lottogo.utils.PreferenceUtils;
import com.bbaek.lottogo.widget.NumberBall;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by woonsungbaek on 2016. 4. 26..
 */
public class ScanResultListAdapter extends BaseAdapter {
    private Context context;
    private int layoutId;
    private List<DrwtNos> lottoList;
    private Lotto lotto;
    private LayoutInflater Inflater;

    public ScanResultListAdapter(Context context, Lotto lotto, List<DrwtNos> lottoList) {
        this.lottoList = lottoList;
        this.context = context;
        this.lotto = lotto;
        this.layoutId = R.layout.layout_scan_result_list;
        Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lottoList.size();
    }

    @Override
    public DrwtNos getItem(int position) {
        return lottoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lottoList.get(position).getRank();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final RankListViewHoler holder;
        if (view == null) {
            view = Inflater.inflate(layoutId, parent, false);
            holder = new RankListViewHoler();
            holder.rank = (TextView) view.findViewById(R.id.scanResultRankTxt);
            holder.drwtNo1 = (NumberBall) view.findViewById(R.id.scanResultDrwtNo1);
            holder.drwtNo2 = (NumberBall) view.findViewById(R.id.scanResultDrwtNo2);
            holder.drwtNo3 = (NumberBall) view.findViewById(R.id.scanResultDrwtNo3);
            holder.drwtNo4 = (NumberBall) view.findViewById(R.id.scanResultDrwtNo4);
            holder.drwtNo5 = (NumberBall) view.findViewById(R.id.scanResultDrwtNo5);
            holder.drwtNo6 = (NumberBall) view.findViewById(R.id.scanResultDrwtNo6);
            view.setTag(holder);
        } else {
            holder = (RankListViewHoler) view.getTag();
        }

        int rank = lottoList.get(position).getRank();
        String rankStr = "꽝";
        if (rank != 0) {
            rankStr = rank + "등";
        }
        holder.rank.setText(rankStr);
        holder.drwtNo1.setValue(lottoList.get(position).getDrwtNo1());
        holder.drwtNo2.setValue(lottoList.get(position).getDrwtNo2());
        holder.drwtNo3.setValue(lottoList.get(position).getDrwtNo3());
        holder.drwtNo4.setValue(lottoList.get(position).getDrwtNo4());
        holder.drwtNo5.setValue(lottoList.get(position).getDrwtNo5());
        holder.drwtNo6.setValue(lottoList.get(position).getDrwtNo6());
        int bunsNo = lotto.getBnusNo();
        holder.drwtNo1.setSelected((lotto.getDrwtNo1() == lottoList.get(position).getDrwtNo1() || bunsNo == lottoList.get(position).getDrwtNo1()) ? true : false);
        holder.drwtNo2.setSelected((lotto.getDrwtNo2() == lottoList.get(position).getDrwtNo2() || bunsNo == lottoList.get(position).getDrwtNo2()) ? true : false);
        holder.drwtNo3.setSelected((lotto.getDrwtNo3() == lottoList.get(position).getDrwtNo3() || bunsNo == lottoList.get(position).getDrwtNo3()) ? true : false);
        holder.drwtNo4.setSelected((lotto.getDrwtNo4() == lottoList.get(position).getDrwtNo4() || bunsNo == lottoList.get(position).getDrwtNo4()) ? true : false);
        holder.drwtNo5.setSelected((lotto.getDrwtNo5() == lottoList.get(position).getDrwtNo5() || bunsNo == lottoList.get(position).getDrwtNo5()) ? true : false);
        holder.drwtNo6.setSelected((lotto.getDrwtNo6() == lottoList.get(position).getDrwtNo6() || bunsNo == lottoList.get(position).getDrwtNo6()) ? true : false);

        return view;
    }

    class RankListViewHoler {
        NumberBall drwtNo1;
        NumberBall drwtNo2;
        NumberBall drwtNo3;
        NumberBall drwtNo4;
        NumberBall drwtNo5;
        NumberBall drwtNo6;
        TextView rank;
    }
}
