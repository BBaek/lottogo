package com.b2soft.lottogo;

import com.b2soft.lottogo.model.my.DrwtNos;
import com.b2soft.lottogo.model.my.ResultHistoryNo;
import com.b2soft.lottogo.utils.BBLogger;

import java.util.StringTokenizer;
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
        parsing(urlValue);
    }

    public int getDrwNo() {
        return  resultHistoryNo.getDrwNo();
    }

    public ResultHistoryNo getResultHistoryNo() {
        return resultHistoryNo;
    }

    protected void parsing(String url) {
        Pattern urlPattern = Pattern.compile("^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$");
        Matcher mc = urlPattern.matcher(url);
        if (mc.find()) {
            resultHistoryNo = new ResultHistoryNo();
            if (mc.matches()) {
                for (int i = 0; i <= mc.groupCount(); i++) {
                    logger.debug("group[" + i + "]\t: " + mc.group(i));
                }

                String[] temp = mc.group(9).split("=");
                logger.debug("key: " + temp[1]);
                resultHistoryNo.setKey(temp[1]);

            /*
                m : menual
                q : auto
                ex) temp[1] : 0702q101924263545q142630353745q031012163741q081022252740q0306363839451703707392
                value[0] : drwtNo
                value[1] : numbers 1
                value[2] : numbers 2
                value[3] : numbers 3
                value[4] : numbers 4
                value[5] : numbers 5
              */

//            StringTokenizer st = new StringTokenizer(temp[1], "m|q");
                String value[] = temp[1].split("m|q");
                int drwtNo = Integer.parseInt(value[0]);
                logger.debug("drwtNo: " + drwtNo);
                resultHistoryNo.setDrwNo(drwtNo);

                resultHistoryNo.setDrwtNoList(new RealmList<DrwtNos>());
                for (int i = 1; i < value.length; i++) {
                    String number = value[i].substring(0, 12);
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
                logger.debug("not found");
            }
        } else {
            resultHistoryNo = null;
        }
    }
}
