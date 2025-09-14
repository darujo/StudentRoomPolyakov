package ru.daru_jo.assistant.helper;


import java.text.SimpleDateFormat;
import java.util.Date;

public class DataHelper {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    public static String dateToDDMMYYYY(Date date){
        if (date == null){
            return null;
        }
        return sdf.format(date);
    }
    private static final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    public static String dateToTime(Date date){

        if (date == null){
            return null;
        }
        return formatter.format(date);
    }

}
