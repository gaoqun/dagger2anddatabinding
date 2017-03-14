package testdagger.gaige.com.testdagger2.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

import testdagger.gaige.com.testdagger2.bean.User;
import testdagger.gaige.com.testdagger2.databinding.ItemRecyclerBinding;

/**
 * Created by gaige on 17/3/14.
 */

public class UserViewHolder extends BaseViewHolder {
    private ItemRecyclerBinding itemRecyclerBinding;
    public UserViewHolder(View itemView) {
        super(itemView);
        itemRecyclerBinding =  DataBindingUtil.bind(itemView);
    }

    public void bind(@NonNull User user){
        itemRecyclerBinding.setUser(user);
    }


}
