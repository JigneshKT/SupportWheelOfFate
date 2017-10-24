package jigneshkt.com.supportwheeloffate.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import jigneshkt.com.supportwheeloffate.SupportWheelOfFate;
import jigneshkt.com.supportwheeloffate.presentation.dagger.AppComponent;

public abstract class BaseActivity <Presenter extends BaseActivityPresenter> extends AppCompatActivity implements BaseActivityView {

    private static final int NO_STUB_VIEW = -1;

    protected AppComponent getAppComponent() {
        return ((SupportWheelOfFate) getApplication()).getAppComponent();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        inject();
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayoutId());
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            getPresenter().onReCreate(savedInstanceState, this);
        } else {
            getPresenter().onCreate(getIntent().getExtras(), this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onResume(this);
        configureViews();
        configureSubscriptions();
    }

    protected void configureSubscriptions() {
    }

    protected void configureViews() {

    }

    protected abstract void inject();

    @NonNull
    protected abstract Presenter getPresenter();

    @LayoutRes
    protected abstract int getActivityLayoutId();
}
