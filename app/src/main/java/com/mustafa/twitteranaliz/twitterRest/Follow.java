package com.mustafa.twitteranaliz.twitterRest;


import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;
import java.util.List;

public class Follow {

    public static ArrayList<Follow> UserFollowList;

    @SerializedName("users")
    private final List<User> users;

    private String next_cursor;
    private String previous_cursor_str;
    private String previous_cursor;
    private String next_cursor_str;

    public Follow(List<User> users) {
        this.users = users;
    }

    public static ArrayList<Follow> getUserFollowList() {
        return UserFollowList;
    }

    public static void setUserFollowList(ArrayList<Follow> userFollowList) {
        UserFollowList = userFollowList;
    }

    public List<User> getUsers() {
        return users;
    }

    public String getNext_cursor() {
        return next_cursor;
    }

    public void setNext_cursor(String next_cursor) {
        this.next_cursor = next_cursor;
    }

    public String getPrevious_cursor_str() {
        return previous_cursor_str;
    }

    public void setPrevious_cursor_str(String previous_cursor_str) {
        this.previous_cursor_str = previous_cursor_str;
    }

    public String getPrevious_cursor() {
        return previous_cursor;
    }

    public void setPrevious_cursor(String previous_cursor) {
        this.previous_cursor = previous_cursor;
    }

    public String getNext_cursor_str() {
        return next_cursor_str;
    }

    public void setNext_cursor_str(String next_cursor_str) {
        this.next_cursor_str = next_cursor_str;
    }


}
