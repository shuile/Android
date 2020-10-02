package com.cyt.testbinder.testBundle;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author 122668
 */
public class UserP implements Parcelable {

    public int userId;
    public String userName;
    public boolean isMale;

    public UserP(int userId, String userName, boolean isMale) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    public UserP(Parcel source) {
        userId = source.readInt();
        userName = source.readString();
        isMale = source.readInt() == 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeInt(isMale ? 1 : 0);
    }

    public static final Creator<UserP> CREATOR = new Creator<UserP>() {

        @Override
        public UserP createFromParcel(Parcel source) {
            return new UserP(source);
        }

        @Override
        public UserP[] newArray(int size) {
            return new UserP[size];
        }
    };

    @Override
    public String toString() {
        return "UserP{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", isMale=" + isMale +
                '}';
    }
}
