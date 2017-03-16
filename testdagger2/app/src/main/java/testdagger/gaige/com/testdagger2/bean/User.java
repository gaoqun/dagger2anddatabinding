package testdagger.gaige.com.testdagger2.bean;

import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

import com.android.databinding.library.baseAdapters.BR;

import javax.inject.Inject;

/**
 * Created by asus on 2017-03-11 09:42.
 */

public class User implements Observable {

    @Bindable
    private String userName;

    private String password;

    private int age;

    private int sex;

    public User(String userName, String password, int age, int sex) {
        this.userName = userName;
        this.password = password;
        this.age = age;
        this.sex = sex;
    }

    @Inject
    public User(){}

    public void setUserName(String userName) {
        this.userName = userName;
        callback.notifyChange(this, BR.userName);
    }

    private PropertyChangeRegistry callback = new PropertyChangeRegistry();

    public int getSex() {
        return sex;
    }

    public String getUserName() {
        return userName==null?"null":userName;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback onPropertyChangedCallback) {
        callback.add(onPropertyChangedCallback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback onPropertyChangedCallback) {
        callback.remove(onPropertyChangedCallback);
    }
}
