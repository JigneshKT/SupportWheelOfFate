package jigneshkt.com.supportwheeloffate.presentation.ui.home;

import android.util.Log;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import jigneshkt.com.supportwheeloffate.base.BaseActivityPresenter;
import jigneshkt.com.supportwheeloffate.domain.model.Shift;
import jigneshkt.com.supportwheeloffate.domain.repository.NextEmployeeRepository;
import jigneshkt.com.supportwheeloffate.presentation.dagger.qualifier.Employees;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class HomeActivityPresenter extends BaseActivityPresenter<HomeActivityView> {

    private static final String TAG = HomeActivityPresenter.class.getSimpleName();
    private final NextEmployeeRepository nextEmployeeRepository;
    private final Map<String,String>employees;

    @Override
    protected void updateViewState() {
        super.updateViewState();
        getAllShifts();
    }

    @Inject
    public HomeActivityPresenter(NextEmployeeRepository nextEmployeeRepository, @Employees Map<String,String>employees) {
        this.nextEmployeeRepository = nextEmployeeRepository;
        this.employees = employees;
    }

    public void getAllShifts() {

        subscriptions.add(nextEmployeeRepository.getAllShiftDetails()
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Shift>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error occurred: " + e.getLocalizedMessage());

                    }

                    @Override
                    public void onNext(List<Shift> shifts) {
                        if(shifts!=null)
                            view.onShiftsSuccess(shifts);
                    }
                }));

    }


    public void getNextEmployeesForShift() {

        subscriptions.add(nextEmployeeRepository.getNextEmployeesForShift()
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error occurred: " + e.getLocalizedMessage());

                    }

                    @Override
                    public void onNext(Boolean status) {
                        Log.e(TAG, "OnNext: " + status);
                        if(status)
                            getAllShifts();
                    }
                }));

    }


    public void reset() {

        subscriptions.add(nextEmployeeRepository.resetEmployeeShift()
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error occurred: " + e.getLocalizedMessage());

                    }

                    @Override
                    public void onNext(Boolean status) {
                        Log.e(TAG, "OnNext: " + status);
                        if(status)
                            view.onReset();

                    }
                }));

    }

    public Map<String, String> getEmployees() {
        return employees;
    }




}
