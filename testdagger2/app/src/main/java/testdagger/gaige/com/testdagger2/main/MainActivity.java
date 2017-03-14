package testdagger.gaige.com.testdagger2.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import testdagger.gaige.com.testdagger2.list.ListViewsActivity;
import testdagger.gaige.com.testdagger2.R;
import testdagger.gaige.com.testdagger2.base.BaseActivity;
import testdagger.gaige.com.testdagger2.databinding.ActivityMainBinding;

/**
 * Created by asus on 2017-03-11 09:15.
 */

public class MainActivity extends BaseActivity implements MainPresenter {
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setPasswordPresenter(this);
//        activityMainBinding.listType布局id引用
    }

    @Override
    public void passwordClick(View view) {
        Intent intent = new Intent(this, ListViewsActivity.class);
        startActivity(intent);
    }


}
