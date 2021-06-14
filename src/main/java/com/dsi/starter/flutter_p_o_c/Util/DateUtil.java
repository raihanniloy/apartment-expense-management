package com.dsi.starter.flutter_p_o_c.Util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtil {

    public final String MONTH_SUFFIX = "-01";
    public final String YEAR_START_SUFFIX = "-01-01";
    public final String YEAR_END_SUFFIX = "-12-31";

    public String localDateToString(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        String formattedString = date.format(formatter);
        return formattedString;
    }

    public LocalDate stringToLocalDate(String strDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        LocalDate localDate = LocalDate.parse(strDate, formatter);
        return localDate;
    }
}
