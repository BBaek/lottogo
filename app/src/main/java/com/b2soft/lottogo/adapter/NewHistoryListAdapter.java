package com.b2soft.lottogo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.b2soft.lottogo.R;
import com.b2soft.lottogo.activity.setting.history.NewHistoryModel;
import com.b2soft.lottogo.model.my.NewHistoryNo;
import com.b2soft.lottogo.utils.BBLogger;
import com.b2soft.lottogo.widget.NumberBall;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.List;


/**
 * Created by woonsungbaek on 2016. 5. 31..
 */
public class NewHistoryListAdapter extends BaseSwipeAdapter {
    BBLogger logger = new BBLogger(getClass().getSimpleName());

    private Context context;
    private List<NewHistoryNo> newHistoryNoList;
    private NewHistoryModel newHistoryModel;

    public NewHistoryListAdapter(Context context) {
        this.context = context;
        this.newHistoryModel = new NewHistoryModel(context);
        this.newHistoryNoList = newHistoryModel.getAllNewHistory();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.layout_newhistory_list, null);
    }

    public void updateUI(int position) {
        closeAllItems();
        notifyDataSetChanged();
    }

    @Override
    public void fillValues(final int position, View convertView) {
        final NewHistoryNo result = newHistoryNoList.get(position);
        ImageView btnBookmarkItem = (ImageView) convertView.findViewById(R.id.star);
        btnBookmarkItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logger.debug("bookmarked");
                newHistoryModel.toggleMark(result);
                newHistoryNoList = newHistoryModel.getAllNewHistory();
                updateUI(position);
            }
        });

        ImageView btnDeleteItem = (ImageView) convertView.findViewById(R.id.trash);
        btnDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logger.debug("deleted");
                if (result.isBookmark()) {
                    Toast.makeText(context, "마크 되어 있는 목록은 지울수 없습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    newHistoryModel.deleteNewHistory(result);
                    newHistoryNoList.remove(position);
                    updateUI(position);
                }
            }
        });

        ImageView marker = (ImageView) convertView.findViewById(R.id.newHistoryMarker);
        if (result.isBookmark()) {
            marker.setImageResource(R.drawable.star_on);
        } else {
            marker.setImageResource(R.drawable.star_off);
        }

        TextView drwDate = (TextView) convertView.findViewById(R.id.newHistoryListDate);
        drwDate.setText(result.getGenDate());

        NumberBall[] arrNos = new NumberBall[6];
        arrNos[0] = (NumberBall) convertView.findViewById(R.id.newHistoryDrwtNo1);
        arrNos[1] = (NumberBall) convertView.findViewById(R.id.newHistoryDrwtNo2);
        arrNos[2] = (NumberBall) convertView.findViewById(R.id.newHistoryDrwtNo3);
        arrNos[3] = (NumberBall) convertView.findViewById(R.id.newHistoryDrwtNo4);
        arrNos[4] = (NumberBall) convertView.findViewById(R.id.newHistoryDrwtNo5);
        arrNos[5] = (NumberBall) convertView.findViewById(R.id.newHistoryDrwtNo6);
        arrNos[0].setValue(result.getNos().getDrwtNo1());
        arrNos[1].setValue(result.getNos().getDrwtNo2());
        arrNos[2].setValue(result.getNos().getDrwtNo3());
        arrNos[3].setValue(result.getNos().getDrwtNo4());
        arrNos[4].setValue(result.getNos().getDrwtNo5());
        arrNos[5].setValue(result.getNos().getDrwtNo6());
    }

    @Override
    public int getCount() {
        return newHistoryNoList.size();
    }

    @Override
    public Object getItem(int position) {
        return newHistoryNoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void deleteAllData() {
        newHistoryNoList = newHistoryModel.getAllNewHistory();
    }
}