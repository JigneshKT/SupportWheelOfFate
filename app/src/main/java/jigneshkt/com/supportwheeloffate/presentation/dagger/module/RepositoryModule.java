package jigneshkt.com.supportwheeloffate.presentation.dagger.module;


import android.content.Context;

import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import jigneshkt.com.supportwheeloffate.data.NextEmployeeDataRepository;
import jigneshkt.com.supportwheeloffate.domain.repository.NextEmployeeRepository;
import jigneshkt.com.supportwheeloffate.presentation.dagger.qualifier.Employees;

@Module
public class RepositoryModule {

    Context context;

    public RepositoryModule(Context context) {
        this.context = context;
    }


    @Provides
    @Singleton
    NextEmployeeRepository provideNextEmployeeRepository(@Employees Map<String,String> employees) {
        return new NextEmployeeDataRepository(employees,context);
    }



}
