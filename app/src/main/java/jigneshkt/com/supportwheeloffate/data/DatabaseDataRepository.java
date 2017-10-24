package jigneshkt.com.supportwheeloffate.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import jigneshkt.com.supportwheeloffate.data.mapper.SQLiteToShiftMapper;
import jigneshkt.com.supportwheeloffate.domain.model.Shift;
import rx.Observable;

public class DatabaseDataRepository extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "EmployeeShiftRecords";
    private static final String TABLE_EMPLOYEE_SHIFT = "EmployeeShift";
    public static final String KEY_ID = "id";
    public static final String KEY_DATE = "date";
    public static final String KEY_EMPLOYEE = "employee";
    public static final String KEY_ISHOLIDAY = "holiday";
    public static final String KEY_HOLIDAY_NAME = "holidayname";

    private SQLiteToShiftMapper sqLiteToShiftMapper;


    public DatabaseDataRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        sqLiteToShiftMapper = new SQLiteToShiftMapper();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SHIFT_TABLE = "CREATE TABLE " + TABLE_EMPLOYEE_SHIFT + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_DATE + " TEXT,"
                + KEY_EMPLOYEE + " TEXT, "
                + KEY_ISHOLIDAY + " TEXT, "
                + KEY_HOLIDAY_NAME + " TEXT"
                + ")";
        db.execSQL(CREATE_SHIFT_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE_SHIFT);
        onCreate(db);
    }


    public List<String> getLastFourRecordsWithoutHoliday() throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.query(TABLE_EMPLOYEE_SHIFT, new String[] {KEY_ID, KEY_DATE, KEY_EMPLOYEE,KEY_ISHOLIDAY,KEY_HOLIDAY_NAME},
                KEY_ISHOLIDAY+"=\'false\'",null,null,null,KEY_ID + " DESC",String.valueOf(8));
        if(mCursor!=null){
            List<String> last4DayEmployees = new ArrayList<>();
            if (mCursor.moveToFirst()) {
                do {
                    last4DayEmployees.add(mCursor.getString(mCursor.getColumnIndexOrThrow(KEY_EMPLOYEE)));
                } while (mCursor.moveToNext());

                return last4DayEmployees;
            }
        }
        return null;
    }


    public Observable<List<Shift>> getAllShift() throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.query(TABLE_EMPLOYEE_SHIFT, null, null, null, null, null, KEY_ID + " DESC", null);
        if(mCursor!=null){
            List<Shift> allShifts = new ArrayList<>();
            if (mCursor.moveToFirst()) {
                do {
                   Shift shift = sqLiteToShiftMapper.convertSQLiteTOShift(mCursor);
                    if(shift!=null){
                        allShifts.add(shift);
                    }
                } while (mCursor.moveToNext());
            }

            return Observable.just(allShifts);
        }
        return Observable.just(null);
    }



    public void addEmployeeInShift(String employee1Id, String employee2Id, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values1 = new ContentValues();
        values1.put(KEY_EMPLOYEE, employee1Id);
        values1.put(KEY_DATE, date);
        values1.put(KEY_ISHOLIDAY, "false");
        values1.put(KEY_HOLIDAY_NAME, "");
        db.insert(TABLE_EMPLOYEE_SHIFT, null, values1);


        ContentValues values2 = new ContentValues();
        values2.put(KEY_EMPLOYEE, employee2Id);
        values2.put(KEY_DATE, date);
        values2.put(KEY_ISHOLIDAY, "false");
        values2.put(KEY_HOLIDAY_NAME, "");
        db.insert(TABLE_EMPLOYEE_SHIFT, null, values2);

        db.close();
    }

    public void addHolidayInShift(String date, String holidayName) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMPLOYEE, "");
        values.put(KEY_DATE, date);
        values.put(KEY_ISHOLIDAY, "true");
        values.put(KEY_HOLIDAY_NAME, holidayName);
        db.insert(TABLE_EMPLOYEE_SHIFT, null, values);

        db.close();
    }


    public String getLastDate() throws SQLException{
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_EMPLOYEE_SHIFT, null, null, null, null, null, KEY_ID + " DESC", String.valueOf(1));
        if(cursor==null){
            return null;
        }

        if(cursor.moveToFirst()){
            return cursor.getString(cursor.getColumnIndexOrThrow(KEY_DATE));
        }
        return null;
    }



    public Boolean deleteAllRecords() throws SQLException{
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEE_SHIFT,null,null);
        return true;
    }










}
