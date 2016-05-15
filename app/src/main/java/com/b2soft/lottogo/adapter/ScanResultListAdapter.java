package com.b2soft.lottogo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.b2soft.lottogo.LottoUtils;
import com.b2soft.lottogo.R;
import com.b2soft.lottogo.model.Lotto;
import com.b2soft.lottogo.model.my.DrwtNos;
import com.b2soft.lottogo.utils.PreferenceUtils;
import com.b2soft.lottogo.widget.NumberBall;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by woonsungbaek on 2016. 4. 26..
 */
public class ScanResultListAdapter extends BaseAdapter {
    private Context context;
    private int layoutId;
    private List<DrwtNos> scanResultList;
    private List<Integer> lottoNo;
    private int bunsNo;
    private LayoutInflater Inflater;

    public ScanResultListAdapter(Context context, Lotto lotto, List<DrwtNos> scanResultList) {
        this.scanResultList = scanResultList;
        this.context = context;
        this.lottoNo = new ArrayList<>();
        this.lottoNo = LottoUtils.toArrayList(lotto);
        this.bunsNo = lotto.getBnusNo();
        this.layoutId = R.layout.layout_scan_result_list;
        Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return scanResultList.size();
    }

    @Override
    public DrwtNos getItem(int position) {
        return scanResultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return scanResultList.get(position).getRank();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ScanResultListViewHoler holder;
        if (view == null) {
            view = Inflater.inflate(layoutId, parent, false);
            holder = new ScanResultListViewHoler(view);
            view.setTag(holder);
        } else {
            holder = (ScanResultListViewHoler) view.getTag();
        }

        int rank = scanResultList.get(position).getRank();
        String rankStr = "꽝";
        if (rank != 0) {
            rankStr = rank + "등";
        }
        holder.rank.setText(rankStr);
        boolean chkNo1 = lottoNo.contains(scanResultList.get(position).getDrwtNo1());
        boolean chkNo2 = lottoNo.contains(scanResultList.get(position).getDrwtNo2());
        boolean chkNo3 = lottoNo.contains(scanResultList.get(position).getDrwtNo3());
        boolean chkNo4 = lottoNo.contains(scanResultList.get(position).getDrwtNo4());
        boolean chkNo5 = lottoNo.contains(scanResultList.get(position).getDrwtNo5());
        boolean chkNo6 = lottoNo.contains(scanResultList.get(position).getDrwtNo6());
        holder.drwtNo1.setValue(scanResultList.get(position).getDrwtNo1());
        holder.drwtNo2.setValue(scanResultList.get(position).getDrwtNo2());
        holder.drwtNo3.setValue(scanResultList.get(position).getDrwtNo3());
        holder.drwtNo4.setValue(scanResultList.get(position).getDrwtNo4());
        holder.drwtNo5.setValue(scanResultList.get(position).getDrwtNo5());
        holder.drwtNo6.setValue(scanResultList.get(position).getDrwtNo6());
        boolean _rank2 = rank == 2 ? true : false;
        holder.drwtNo1.setSelected(chkNo1 || (_rank2 && bunsNo == scanResultList.get(position).getDrwtNo1()) ? true : false);
        holder.drwtNo2.setSelected(chkNo2 || (_rank2 && bunsNo == scanResultList.get(position).getDrwtNo2()) ? true : false);
        holder.drwtNo3.setSelected(chkNo3 || (_rank2 && bunsNo == scanResultList.get(position).getDrwtNo3()) ? true : false);
        holder.drwtNo4.setSelected(chkNo4 || (_rank2 && bunsNo == scanResultList.get(position).getDrwtNo4()) ? true : false);
        holder.drwtNo5.setSelected(chkNo5 || (_rank2 && bunsNo == scanResultList.get(position).getDrwtNo5()) ? true : false);
        holder.drwtNo6.setSelected(chkNo6 || (_rank2 && bunsNo == scanResultList.get(position).getDrwtNo6()) ? true : false);

        return view;
    }

    class ScanResultListViewHoler {
        @Bind(R.id.scanResultDrwtNo1) NumberBall drwtNo1;
        @Bind(R.id.scanResultDrwtNo2) NumberBall drwtNo2;
        @Bind(R.id.scanResultDrwtNo3) NumberBall drwtNo3;
        @Bind(R.id.scanResultDrwtNo4) NumberBall drwtNo4;
        @Bind(R.id.scanResultDrwtNo5) NumberBall drwtNo5;
        @Bind(R.id.scanResultDrwtNo6) NumberBall drwtNo6;
        @Bind(R.id.scanResultRankTxt) TextView rank;

        public ScanResultListViewHoler(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
