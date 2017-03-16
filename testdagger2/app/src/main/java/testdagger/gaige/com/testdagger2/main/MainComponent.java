package testdagger.gaige.com.testdagger2.main;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by gaige on 17/3/16.
 */
@Singleton
@Component(modules = {MainModule.class})
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
