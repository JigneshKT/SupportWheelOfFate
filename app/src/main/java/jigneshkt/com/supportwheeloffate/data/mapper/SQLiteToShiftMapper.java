package jigneshkt.com.supportwheeloffate.data.mapper;

import android.database.Cursor;

import jigneshkt.com.supportwheeloffate.domain.model.Shift;

import static jigneshkt.com.supportwheeloffate.data.DatabaseDataRepository.KEY_DATE;
import static jigneshkt.com.supportwheeloffate.data.DatabaseDataRepository.KEY_EMPLOYEE;
import static jigneshkt.com.supportwheeloffate.data.DatabaseDataRepository.KEY_HOLIDAY_NAME;
import static jigneshkt.com.supportwheeloffate.data.DatabaseDataRepository.KEY_ID;
import static jigneshkt.com.supportwheeloffate.data.DatabaseDataRepository.KEY_ISHOLIDAY;

public class SQLiteToShiftMapper {


    public Shift convertSQLiteTOShift(Cursor mCursor){

        if(mCursor==null){
            return null;
        }

        Shift shift = new Shift();
        shift.setId(mCursor.getString(mCursor.getColumnIndexOrThrow(KEY_ID)));
        shift.setDate(mCursor.getString(mCursor.getColumnIndexOrThrow(KEY_DATE)));
        shift.setEmployee(mCursor.getString(mCursor.getColumnIndexOrThrow(KEY_EMPLOYEE)));
        shift.setHoliday(mCursor.getString(mCursor.getColumnIndexOrThrow(KEY_ISHOLIDAY)));
        shift.setHolidayname(mCursor.getString(mCursor.getColumnIndexOrThrow(KEY_HOLIDAY_NAME)));

        return shift;
    }



}
