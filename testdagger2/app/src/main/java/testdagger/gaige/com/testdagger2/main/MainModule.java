package testdagger.gaige.com.testdagger2.main;

import dagger.Module;
import dagger.Provides;
import testdagger.gaige.com.testdagger2.Type;
import testdagger.gaige.com.testdagger2.bean.User;

/**
 * Created by gaige on 17/3/16.
 */

@Module
public class MainModule {

    @Type("lisi")
    @Provides
    User provideUser(){
        return new User("Lisi","",0,0);
    }

    @Type("null")
    @Provides
    User provideDefaultUser(){
        return new User();
    }
}
