package com.bbaek.lottogo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
public class SettingListAdapter extends ArrayAdapter<String> {
    private Context context;
    private int layoutId;
    private List<String> settingList;
    private LayoutInflater Inflater;

    public SettingListAdapter(Context context) {
        super(context, R.layout.layout_setting_list);
        this.context = context;
        this.layoutId = R.layout.layout_setting_list;
        initData();
        Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    protected void initData() {
        settingList = new ArrayList<>();
        settingList.add("당첨 번호 조회");
        settingList.add("포함 번호 선택");
        settingList.add("제외 번호 선택");
        settingList.add("생성 이력 조회");
        settingList.add("결과 이력 조회");
    }

    @Override
    public int getCount() {
        return settingList.size();
    }

    @Override
    public String getItem(int position) {
        return settingList.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final SettingListViewHoler holder;
        if (view == null) {
            view = Inflater.inflate(layoutId, parent, false);
            holder = new SettingListViewHoler(view);
            holder.title = (TextView) view.findViewById(R.id.settingTitle);
            holder.icon = (NumberBall) view.findViewById(R.id.settingIcon);
            view.setTag(holder);
        } else {
            holder = (SettingListViewHoler) view.getTag();
        }

        holder.title.setText(settingList.get(position));
        holder.icon.setValue((position + 1) * 5);

        return view;
    }

    class SettingListViewHoler {
        @Bind(R.id.settingTitle) TextView title;
        @Bind(R.id.settingIcon) NumberBall icon;

        public SettingListViewHoler(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
