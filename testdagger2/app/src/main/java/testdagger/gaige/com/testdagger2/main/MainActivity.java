package testdagger.gaige.com.testdagger2.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import testdagger.gaige.com.testdagger2.R;
import testdagger.gaige.com.testdagger2.base.BaseActivity;
import testdagger.gaige.com.testdagger2.bean.User;
import testdagger.gaige.com.testdagger2.databinding.ActivityMainBinding;

/**
 * Created by asus on 2017-03-11 09:15.
 */

public class MainActivity extends BaseActivity implements MainPresenter {
    private User user;
    private Thread thread;
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        user = new User("zhangsan", "123456", 23, 0);
        activityMainBinding.setUser(user);
        activityMainBinding.setPasswordPresenter(this);
    }

    @Override
    public void passwordClick(View view, User user) {
        if (thread == null) {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        user.setUserName("zhangsan" + System.currentTimeMillis());
                        activityMainBinding.setUser(user);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        thread.start();

    }
}
