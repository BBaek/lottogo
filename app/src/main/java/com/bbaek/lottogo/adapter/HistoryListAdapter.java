package com.bbaek.lottogo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bbaek.lottogo.LottoUtils;
import com.bbaek.lottogo.R;
import com.bbaek.lottogo.widget.NumberBall;

import java.util.List;
import java.util.Map;


/**
 * Created by woonsungbaek on 2016. 4. 26..
 */
public class HistoryListAdapter extends BaseAdapter {
    private Context context;
    private int layoutId;
    private List<Map<Integer, Integer>> lottoList;
    private LottoUtils lottoUtils;
    private LayoutInflater Inflater;

    public HistoryListAdapter(Context context, List<Map<Integer, Integer>> lottoList) {
        this.lottoList = lottoList;
        this.context = context;
        this.layoutId = R.layout.layout_history_list;
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
        final RankListViewHoler holder;
        if (view == null) {
            view = Inflater.inflate(layoutId, parent, false);
            holder = new RankListViewHoler();
            holder.drwtNo1 = (NumberBall) view.findViewById(R.id.drwtNo1HisList);
            holder.drwtNo2 = (NumberBall) view.findViewById(R.id.drwtNo2HisList);
            holder.drwtNo3 = (NumberBall) view.findViewById(R.id.drwtNo3HisList);
            holder.drwtNo4 = (NumberBall) view.findViewById(R.id.drwtNo4HisList);
            holder.drwtNo5 = (NumberBall) view.findViewById(R.id.drwtNo5HisList);
            holder.drwtNo6 = (NumberBall) view.findViewById(R.id.drwtNo6HisList);
            view.setTag(holder);
        } else {
            holder = (RankListViewHoler) view.getTag();
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

    class RankListViewHoler {
        NumberBall drwtNo1;
        NumberBall drwtNo2;
        NumberBall drwtNo3;
        NumberBall drwtNo4;
        NumberBall drwtNo5;
        NumberBall drwtNo6;
    }
}
