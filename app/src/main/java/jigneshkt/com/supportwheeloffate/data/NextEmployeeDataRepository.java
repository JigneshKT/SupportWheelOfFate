package jigneshkt.com.supportwheeloffate.data;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import jigneshkt.com.supportwheeloffate.domain.model.Shift;
import jigneshkt.com.supportwheeloffate.domain.repository.NextEmployeeRepository;
import jigneshkt.com.supportwheeloffate.presentation.dagger.qualifier.Employees;
import rx.Observable;

import static jigneshkt.com.supportwheeloffate.utils.DateUtil.dayOfDate;
import static jigneshkt.com.supportwheeloffate.utils.DateUtil.getCurrentDate;
import static jigneshkt.com.supportwheeloffate.utils.DateUtil.incrementDate;


public class NextEmployeeDataRepository implements NextEmployeeRepository {

    Map<String,String> employees;
    private DatabaseDataRepository databaseDataRepository;
    private Context mContext;



    @Inject
    public NextEmployeeDataRepository(@Employees Map<String,String> employees, Context context){
        this.employees=employees;
        this.mContext = context;
        getDataBaseRepository();
    }

    @Override
    public Observable<List<Shift>> getAllShiftDetails() {
        return getDataBaseRepository().getAllShift();
    }

    @Override
    public Observable<Boolean> getNextEmployeesForShift() {

        List<String> last4DaysEmployees=null;
        String lastDate=null;
        try {
            last4DaysEmployees = getDataBaseRepository().getLastFourRecordsWithoutHoliday();
        }catch (SQLException e){
            Log.e("NextEmployeeRepository",e.getLocalizedMessage());
        }


        try {
            lastDate = getDataBaseRepository().getLastDate();
        }catch (SQLException e){

        }


        if(lastDate==null){
            lastDate = getCurrentDate();
        }else{
            lastDate = incrementDate(lastDate);
            if(lastDate==null){
                lastDate = getCurrentDate();
            }
        }


        if(dayOfDate(lastDate).equalsIgnoreCase("SUNDAY") || dayOfDate(lastDate).equalsIgnoreCase("SATURDAY")){
            try {
                getDataBaseRepository().addHolidayInShift(lastDate,dayOfDate(lastDate));
            }catch (SQLException e){
                Log.e("NextEmployeeRepository",e.getLocalizedMessage());
            }


        }else{
            List<String> next2Employees = supportWheelOfFate(getAllEmployeesId(last4DaysEmployees));
            if(next2Employees==null || next2Employees.size()<2){
                return  Observable.just(false);
            }
            getDataBaseRepository().addEmployeeInShift(next2Employees.get(0),next2Employees.get(1),lastDate);
        }

        return Observable.just(true);

    }

    private List<String> getAllEmployeesId(List<String> last4DaysEmployees){

        List<String>remainingEmployeesId = new ArrayList<>();

        for (String key : employees.keySet()) {
            remainingEmployeesId.add(key);
        }

        if(last4DaysEmployees==null){
            return remainingEmployeesId;
        }

        if(last4DaysEmployees.size()==0){
            return remainingEmployeesId;
        }

        for (String Id : last4DaysEmployees) {
            remainingEmployeesId.remove(Id);
        }

        return remainingEmployeesId;

    }

    private List<String> supportWheelOfFate(List<String>remainingEmployeesId){

        if(remainingEmployeesId!=null && remainingEmployeesId.size()>=2){
            Collections.shuffle(remainingEmployeesId);
            return remainingEmployeesId.subList(0, 2);
        }
        return null;
    }


    private DatabaseDataRepository getDataBaseRepository(){
        if(databaseDataRepository==null){
            databaseDataRepository = new DatabaseDataRepository(mContext);
        }
        return databaseDataRepository;
    }


    @Override
    public Observable<Boolean> resetEmployeeShift() {
        try {
            return Observable.just(databaseDataRepository.deleteAllRecords());
        }catch (SQLException e){
            return Observable.just(false);
        }

    }
}
