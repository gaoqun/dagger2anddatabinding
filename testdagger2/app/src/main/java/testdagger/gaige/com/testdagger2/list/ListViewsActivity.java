package testdagger.gaige.com.testdagger2.list;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import testdagger.gaige.com.testdagger2.R;
import testdagger.gaige.com.testdagger2.adapters.UserViewHolder;
import testdagger.gaige.com.testdagger2.base.BaseActivity;
import testdagger.gaige.com.testdagger2.bean.User;
import testdagger.gaige.com.testdagger2.databinding.ListviewsBinding;

/**
 * Created by gaige on 17/3/13.
 */

public class ListViewsActivity extends BaseActivity {
    private List<User> users = new ArrayList<>();
    private ListviewsBinding listviewsBinding;
    private BaseQuickAdapter<User, UserViewHolder> baseQuickAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < 10; i++) {
            User user = new User("" + i, "lll", 1, 1);
            users.add(user);
        }
        listviewsBinding = DataBindingUtil.setContentView(this, R.layout.listviews);
        recyclerView = listviewsBinding.recycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (baseQuickAdapter == null) {
            baseQuickAdapter = new BaseQuickAdapter<User, UserViewHolder>(R.layout.item_recycler, users) {
                @Override
                protected void convert(UserViewHolder helper, User item) {

                }

                @Override
                public void onBindViewHolder(UserViewHolder holder, int positions) {
                    holder.bind(users.get(positions));
                }
            };
            recyclerView.setAdapter(baseQuickAdapter);
        }

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(ListViewsActivity.this, ""+position, Toast.LENGTH_SHORT).show();
                users.get(position).setUserName(""+(position+1));
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
