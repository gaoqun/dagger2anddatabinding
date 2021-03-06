package testdagger.gaige.com.testdagger2.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import javax.inject.Inject;

import testdagger.gaige.com.testdagger2.R;
import testdagger.gaige.com.testdagger2.Type;
import testdagger.gaige.com.testdagger2.base.BaseActivity;
import testdagger.gaige.com.testdagger2.bean.User;
import testdagger.gaige.com.testdagger2.cards.CardsActivity;
import testdagger.gaige.com.testdagger2.databinding.ActivityMainBinding;
import testdagger.gaige.com.testdagger2.list.ListViewsActivity;
import testdagger.gaige.com.testdagger2.photoesLoader.SelectPictures;

/**
 * Created by asus on 2017-03-11 09:15.
 */

public class MainActivity extends BaseActivity implements MainPresenter {
    private ActivityMainBinding activityMainBinding;

    @Type("lisi")
    @Inject
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerMainComponent.create().inject(this);
        Toast.makeText(this, user.getUserName(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void clickGesture(View view) {
        startActivity(new Intent(this,GestureActivity.class));
    }

    @Override
    public void clickAlbumAndTakePhotoes(View view) {
        startActivity(new Intent(this, SelectPictures.class));
    }

    @Override
    public void clickCards() {
        startActivity(new Intent(this,CardsActivity.class));
    }


}
