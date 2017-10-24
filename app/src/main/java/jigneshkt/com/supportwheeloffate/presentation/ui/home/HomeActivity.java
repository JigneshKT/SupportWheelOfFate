package jigneshkt.com.supportwheeloffate.presentation.ui.home;


import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import jigneshkt.com.supportwheeloffate.R;
import jigneshkt.com.supportwheeloffate.base.BaseActivity;
import jigneshkt.com.supportwheeloffate.domain.model.Shift;
import jigneshkt.com.supportwheeloffate.presentation.ui.home.adapter.ShiftRecyclerAdapter;

public class HomeActivity extends BaseActivity<HomeActivityPresenter> implements HomeActivityView {

    @Inject
    HomeActivityPresenter homeActivityPresenter;

    private ShiftRecyclerAdapter shiftRecyclerAdapter;

    @BindView(R.id.shift_recycler_view)
    RecyclerView shiftRecyclerView;

    @BindView(R.id.tv_no_employee_shift)
    TextView tv_no_employee_shift;

    @Override
    protected void inject() {
        getAppComponent().inject(this);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_home;
    }

    @NonNull
    @Override
    protected HomeActivityPresenter getPresenter() {
        return homeActivityPresenter;
    }

    @Override
    protected void configureViews() {
        super.configureViews();
    }

    @OnClick(R.id.tv_add_next_employee)
    public void getNextEmployeeForShift(){
        getPresenter().getNextEmployeesForShift();
    }

    @OnClick(R.id.tv_reset_employee_shift)
    public void resetEmployeeShift(){
        getPresenter().reset();
    }


    @Override
    public void onShiftsSuccess(final List<Shift> shiftList) {
        if(shiftList==null || shiftList.size()==0)
            return;

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                tv_no_employee_shift.setVisibility(View.GONE);
                shiftRecyclerView.setVisibility(View.VISIBLE);
                if(shiftRecyclerAdapter==null) {
                    shiftRecyclerAdapter = new ShiftRecyclerAdapter(HomeActivity.this, R.layout.layout_shift_recycler, shiftList,getPresenter().getEmployees());
                    shiftRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                    shiftRecyclerView.setAdapter(shiftRecyclerAdapter);
                }else{
                    shiftRecyclerAdapter.setShiftList(shiftList);
                    shiftRecyclerAdapter.notifyDataSetChanged();
                }
            }
        });

    }


    @Override
    public void onReset() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                tv_no_employee_shift.setVisibility(View.VISIBLE);
                shiftRecyclerView.setVisibility(View.GONE);
                if(shiftRecyclerAdapter!=null) {
                    shiftRecyclerAdapter.setShiftList(null);
                    shiftRecyclerAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
