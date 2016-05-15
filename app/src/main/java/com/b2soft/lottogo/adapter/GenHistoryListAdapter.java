package com.b2soft.lottogo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.b2soft.lottogo.LottoUtils;
import com.b2soft.lottogo.R;
import com.b2soft.lottogo.widget.NumberBall;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by woonsungbaek on 2016. 4. 26..
 */
public class GenHistoryListAdapter extends BaseAdapter {
    private Context context;
    private int layoutId;
    private List<Map<Integer, Integer>> lottoList;
    private LottoUtils lottoUtils;
    private LayoutInflater Inflater;

    public GenHistoryListAdapter(Context context, List<Map<Integer, Integer>> lottoList) {
        this.lottoList = lottoList;
        this.context = context;
        this.layoutId = R.layout.layout_genhistory_list;
        this.lottoUtils = new LottoUtils(context);
        Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lottoList.size();
    }

    @Override
    public Map<Integer, Integer> getItem(int position) {
        return lottoList.get(position);
    }

    @Override
    public long getItemId(int position) {
//        return lottoList.get(position);
        return lottoList.get(position).size();
    }

    public void addItem(Map<Integer, Integer> lotto) {
        lottoList.add(lotto);
    }

    public void removeItem(int position) {
        lottoList.remove(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final HistoryListViewHoler holder;
        if (view == null) {
            view = Inflater.inflate(layoutId, parent, false);
            holder = new HistoryListViewHoler(view);
            view.setTag(holder);
        } else {
            holder = (HistoryListViewHoler) view.getTag();
        }
        List<Integer> list = lottoUtils.sortByValue(lottoList.get(position));
        if (list != null) {
            holder.drwtNo1.setValue(list.get(0));
            holder.drwtNo2.setValue(list.get(1));
            holder.drwtNo3.setValue(list.get(2));
            holder.drwtNo4.setValue(list.get(3));
            holder.drwtNo5.setValue(list.get(4));
            holder.drwtNo6.setValue(list.get(5));
        }

        return view;
    }

    class HistoryListViewHoler {
        @Bind(R.id.drwtNo1HisList) NumberBall drwtNo1;
        @Bind(R.id.drwtNo2HisList) NumberBall drwtNo2;
        @Bind(R.id.drwtNo3HisList) NumberBall drwtNo3;
        @Bind(R.id.drwtNo4HisList) NumberBall drwtNo4;
        @Bind(R.id.drwtNo5HisList) NumberBall drwtNo5;
        @Bind(R.id.drwtNo6HisList) NumberBall drwtNo6;

        public HistoryListViewHoler(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
