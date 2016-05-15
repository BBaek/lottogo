package com.b2soft.lottogo;

import com.b2soft.lottogo.model.my.DrwtNos;
import com.b2soft.lottogo.model.my.ResultHistoryNo;
import com.b2soft.lottogo.utils.BBLogger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.RealmList;

/**
 * Created by woonsungbaek on 2016. 5. 2..
 */
public class LottoQRParser {
    BBLogger logger = new BBLogger(getClass().getSimpleName());

    ResultHistoryNo resultHistoryNo;

    public LottoQRParser(String urlValue) {
        pasing(urlValue);
    }

    public int getDrwNo() {
        return  resultHistoryNo.getDrwNo();
    }

    public ResultHistoryNo getResultHistoryNo() {
        return resultHistoryNo;
    }

    protected void pasing(String url) {
        Pattern urlPattern = Pattern.compile("^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$");
        Matcher mc = urlPattern.matcher(url);
        if (mc.find()) {
            resultHistoryNo = new ResultHistoryNo();
            if (mc.matches()) {
                for (int i = 0; i <= mc.groupCount(); i++) {
                    logger.debug("group[" + i + "]\t: " + mc.group(i));
                }
            } else {
                logger.debug("not found");
            }

            String[] temp = mc.group(9).split("=");
            logger.debug("key: " + temp[1]);
            resultHistoryNo.setKey(temp[1]);

            String[] value = temp[1].split("m");
            logger.debug("drwtNo: " + Integer.parseInt(value[0]));
            resultHistoryNo.setDrwNo(Integer.parseInt(value[0]));

            String[] numbers = value[1].split("q");
            resultHistoryNo.setDrwtNoList(new RealmList<DrwtNos>());
            for (int i = 0; i < numbers.length; i++) {
                String number = numbers[i].substring(0, 12);
                logger.debug("numbers: " + number);
                DrwtNos drwtNos = new DrwtNos();
                int k = 0;
                drwtNos.setDrwtNo1(Integer.parseInt(number.substring(k, k = k + 2)));
                drwtNos.setDrwtNo2(Integer.parseInt(number.substring(k, k = k + 2)));
                drwtNos.setDrwtNo3(Integer.parseInt(number.substring(k, k = k + 2)));
                drwtNos.setDrwtNo4(Integer.parseInt(number.substring(k, k = k + 2)));
                drwtNos.setDrwtNo5(Integer.parseInt(number.substring(k, k = k + 2)));
                drwtNos.setDrwtNo6(Integer.parseInt(number.substring(k, k = k + 2)));
                resultHistoryNo.getDrwtNoList().add(drwtNos);
            }
        } else {
            resultHistoryNo = null;
        }
    }
}
