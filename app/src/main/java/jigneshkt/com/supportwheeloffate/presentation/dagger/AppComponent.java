package jigneshkt.com.supportwheeloffate.presentation.dagger;

import javax.inject.Singleton;

import dagger.Component;
import jigneshkt.com.supportwheeloffate.SupportWheelOfFate;
import jigneshkt.com.supportwheeloffate.presentation.dagger.module.ApplicationModule;
import jigneshkt.com.supportwheeloffate.presentation.dagger.module.RepositoryModule;
import jigneshkt.com.supportwheeloffate.presentation.ui.home.HomeActivity;

@Singleton
@Component(modules = {ApplicationModule.class, RepositoryModule.class})
public interface AppComponent {
        void inject(SupportWheelOfFate supportWheelOfFate);
        void inject(HomeActivity homeActivity);
}
