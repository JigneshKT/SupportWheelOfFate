package jigneshkt.com.supportwheeloffate.presentation.ui.home.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import jigneshkt.com.supportwheeloffate.R;
import jigneshkt.com.supportwheeloffate.domain.model.Shift;


public class ShiftRecyclerAdapter extends RecyclerView.Adapter<ShiftRecyclerAdapter.ViewHolder> {


    Context mContext;
    int layoutResourceId;
    List<Shift> data = null;
    Map<String,String>employees;

    public ShiftRecyclerAdapter(Context mContext, int layoutResourceId, List<Shift> data, Map<String,String> employees) {

        //super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
        this.employees = employees;
    }

    public void setShiftList(List<Shift> data){
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResourceId, parent, false);




        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(data.size()>position) {

            holder.itemView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));


            Shift shift = data.get(position);

            if (shift != null) {
                holder.tv_date.setText(shift.getDate());
                if (shift.getHoliday().equalsIgnoreCase("true")) {
                    holder.tv_employee.setText(mContext.getString(R.string.hurry) + " " + shift.getHolidayname());
                } else {
                    holder.tv_employee.setText(employees.get(shift.getEmployee()));
                }

            }
        }

    }



    @Override
    public int getItemCount() {
        if(data==null){
            return 0;
        }else {
            return data.size();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.tv_date)
        TextView tv_date;

        @Nullable
        @BindView(R.id.tv_employee)
        TextView tv_employee;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }



}

