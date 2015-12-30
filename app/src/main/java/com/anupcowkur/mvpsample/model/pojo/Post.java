package com.anupcowkur.mvpsample.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {
    private  Integer userId;
    private  Integer id;
    private  String title;
    private  String body;

    public Post() {

    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    // Parcelling part

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.body);
        dest.writeInt(this.id);
        dest.writeInt(this.userId);



    }

    protected Post(Parcel in) {
        this.title = in.readString();
        this.body = in.readString();
        this.id = in.readInt();
        this.userId = in.readInt();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

}
