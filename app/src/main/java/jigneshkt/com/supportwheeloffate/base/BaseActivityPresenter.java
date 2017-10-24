package jigneshkt.com.supportwheeloffate.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public abstract class BaseActivityPresenter <View extends BaseActivityView> extends BasePresenter<View> {

    void onCreate(@Nullable Bundle arguments, View view) {
        setView(view);
    }

    void onReCreate(@NonNull Bundle savedInstanceState, View view) {
        setView(view);
    }





}
