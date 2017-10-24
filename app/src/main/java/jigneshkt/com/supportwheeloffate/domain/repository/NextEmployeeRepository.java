package jigneshkt.com.supportwheeloffate.domain.repository;


import java.util.List;

import jigneshkt.com.supportwheeloffate.domain.model.Shift;
import rx.Observable;

public interface NextEmployeeRepository {

    public Observable<Boolean> getNextEmployeesForShift();
    public Observable<List<Shift>> getAllShiftDetails();
    public Observable<Boolean> resetEmployeeShift();

}
