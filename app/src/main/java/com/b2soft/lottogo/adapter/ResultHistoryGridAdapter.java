package com.b2soft.lottogo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.b2soft.lottogo.R;
import com.b2soft.lottogo.activity.setting.history.ResultHistoryModel;
import com.b2soft.lottogo.model.my.DrwtNos;
import com.b2soft.lottogo.model.my.ResultHistoryNo;
import com.b2soft.lottogo.utils.BBLogger;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.List;


/**
 * Created by woonsungbaek on 2016. 5. 31..
 */
public class ResultHistoryGridAdapter extends BaseSwipeAdapter {
    BBLogger logger = new BBLogger(getClass().getSimpleName());

    private Context mContext;
    private List<ResultHistoryNo> resultHistoryNoList;
    private ResultHistoryModel resultHistoryModel;

    public ResultHistoryGridAdapter(Context context) {
        this.mContext = context;
        this.resultHistoryModel = new ResultHistoryModel(context);
        this.resultHistoryNoList = resultHistoryModel.getAllResultHistory();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        return LayoutInflater.from(mContext).inflate(R.layout.layout_resulthistory_list, null);
    }

    @Override
    public void fillValues(final int position, View convertView) {
        final ResultHistoryNo result = resultHistoryNoList.get(position);
        LinearLayout btnDeleteItem = (LinearLayout) convertView.findViewById(R.id.deleteItem);
        btnDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logger.debug("" + resultHistoryModel.getResultHistory(result.getKey()));
                if (resultHistoryModel.deleteResultHistory(result.getKey())) {
                    closeAllItems();
                    notifyDataSetChanged();
                }
            }
        });

        TextView drwNo = (TextView) convertView.findViewById(R.id.resultHistoryDrwNo);
        drwNo.setText(result.getDrwNo() + "회");
        TextView drwDate = (TextView) convertView.findViewById(R.id.resultHistoryDrwDate);
        drwDate.setText(result.getAnnoDate());

        TextView[] arrNos = new TextView[5];
        arrNos[0] = (TextView) convertView.findViewById(R.id.resultHistoryDrwtNos1);
        arrNos[1] = (TextView) convertView.findViewById(R.id.resultHistoryDrwtNos2);
        arrNos[2] = (TextView) convertView.findViewById(R.id.resultHistoryDrwtNos3);
        arrNos[3] = (TextView) convertView.findViewById(R.id.resultHistoryDrwtNos4);
        arrNos[4] = (TextView) convertView.findViewById(R.id.resultHistoryDrwtNos5);

        for(int i = 0; i < 5; i++) {
            if (result.getDrwtNoList().size() > i) {
                DrwtNos nos = result.getDrwtNoList().get(i);
                arrNos[i].setText(new String().format("%02d %02d %02d %02d %02d %02d", nos.getDrwtNo1(), nos.getDrwtNo2(), nos.getDrwtNo3(), nos.getDrwtNo4(), nos.getDrwtNo5(), nos.getDrwtNo6()));
            } else {
                arrNos[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int getCount() {
        return resultHistoryNoList.size();
//        return 50;
    }

    @Override
    public Object getItem(int position) {
        return resultHistoryNoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

//    class ResultHistoryGridViewHolder {
//        @Bind(R.id.deleteItem) LinearLayout btnDeleteItem;
//
//        public ResultHistoryGridViewHolder(View view) {
//            ButterKnife.bind(this, view);
//        }
//    }
}