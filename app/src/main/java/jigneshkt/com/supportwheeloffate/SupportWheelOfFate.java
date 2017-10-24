package jigneshkt.com.supportwheeloffate;

import android.app.Application;

import jigneshkt.com.supportwheeloffate.presentation.dagger.AppComponent;
import jigneshkt.com.supportwheeloffate.presentation.dagger.DaggerAppComponent;
import jigneshkt.com.supportwheeloffate.presentation.dagger.module.ApplicationModule;
import jigneshkt.com.supportwheeloffate.presentation.dagger.module.RepositoryModule;


public class SupportWheelOfFate extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
        getAppComponent().inject(this);
    }

    private void initAppComponent(){
        appComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .repositoryModule(new RepositoryModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
