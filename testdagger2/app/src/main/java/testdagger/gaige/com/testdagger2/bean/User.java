package testdagger.gaige.com.testdagger2.bean;

import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

/**
 * Created by asus on 2017-03-11 09:42.
 */

public class User implements Observable {

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

    private PropertyChangeRegistry callback = new PropertyChangeRegistry();

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
