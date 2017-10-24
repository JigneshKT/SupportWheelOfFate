package jigneshkt.com.supportwheeloffate.presentation.ui.home;

import java.util.List;

import jigneshkt.com.supportwheeloffate.base.BaseActivityView;
import jigneshkt.com.supportwheeloffate.domain.model.Shift;

public interface HomeActivityView extends BaseActivityView {

    void onShiftsSuccess(List<Shift> shiftList);

    void onReset();
}
