package baltamon.mx.appsharecontent;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Baltazar Rodriguez on 12/04/2017.
 */

public class User implements Parcelable{

    private String user_name;
    private int user_age;

    public User(String name, int age){
        this.user_name = name;
        this.user_age = age;
    }

    protected User(Parcel in) {
        user_name = in.readString();
        user_age = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUser_name() {
        return user_name;
    }

    public int getUser_age() {
        return user_age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(user_name);
        parcel.writeInt(user_age);
    }
}
