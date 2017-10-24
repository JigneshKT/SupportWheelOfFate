package jigneshkt.com.supportwheeloffate.presentation.dagger.module;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import jigneshkt.com.supportwheeloffate.R;
import jigneshkt.com.supportwheeloffate.presentation.dagger.qualifier.Employees;


@Module
public class ApplicationModule {

    private Application mContext;

    public ApplicationModule(Application context) {
        this.mContext = context;
    }

    @Provides
    @Employees
    @Singleton
    Map<String,String> provideEmployees() {
        String[] stringArray = mContext.getResources().getStringArray(R.array.employees);
        Map<String,String> outputArray = new HashMap<>();
        for (String entry : stringArray) {
            String[] splitResult = entry.split(",", 2);
            outputArray.put(splitResult[0], splitResult[1]);
        }
        return outputArray;
    }





}
