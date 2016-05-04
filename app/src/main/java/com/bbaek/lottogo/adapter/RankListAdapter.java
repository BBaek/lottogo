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

import butterknife.Bind;
import butterknife.ButterKnife;


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
            holder = new RankListViewHoler(view);
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
        @Bind(R.id.drwtNo1List) NumberBall drwtNo1;
        @Bind(R.id.drwtNo2List) NumberBall drwtNo2;
        @Bind(R.id.drwtNo3List) NumberBall drwtNo3;
        @Bind(R.id.drwtNo4List) NumberBall drwtNo4;
        @Bind(R.id.drwtNo5List) NumberBall drwtNo5;
        @Bind(R.id.drwtNo6List) NumberBall drwtNo6;
        @Bind(R.id.drwtBunsNoList) NumberBall drwtBunsNo;
        @Bind(R.id.drwDateList) TextView drwNoDate;
        @Bind(R.id.drwNoList) TextView drwNo;
        @Bind(R.id.rankList) TextView rank;

        public RankListViewHoler(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
