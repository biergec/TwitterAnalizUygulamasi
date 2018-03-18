package com.mustafa.twitteranaliz.twitterRest;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public class MyTwitterApiClient extends TwitterApiClient{

    public MyTwitterApiClient(TwitterSession session) {
    super(session);
    }

    public CustomServiceUserId getCustomService() {
        return getService(CustomServiceUserId.class);
    }

    public Followers getFollowers(){
        return getService(Followers.class);
    }

    public Follow getFollow(){
        return getService(Follow.class);
    }

    public TakipBirak getTakipBirak(){
        return getService(TakipBirak.class);
    }

    public TakipEt getTakipEt(){
        return getService(TakipEt.class);
    }

    public interface TakipEt {
        @POST("/1.1/friendships/create.json")
        Call<User> takipEt(@Query("screen_name") String userName);
    }

    public interface TakipBirak {
        @POST("/1.1/friendships/destroy.json")
        Call<User> takipBirak(@Query("screen_name") String userName);
    }

    public interface CustomServiceUserId {
        @GET("/1.1/users/show.json")
        Call<User> show(@Query("user_id") long userId);
    }

    public interface Followers {
        @GET("/1.1/followers/list.json")
        Call<com.mustafa.twitteranaliz.twitterRest.Followers> show(@Query("screen_name") String userName, @Query("cursor") String cursorDefaultEksiBir, @Query("skip_status") String trueAyarla, @Query("include_user_entities") String falseAyarla, @Query("count") String count_198_Ayarla);
    }

    public interface Follow {
        @GET("/1.1/friends/list.json")
        Call<com.mustafa.twitteranaliz.twitterRest.Follow> show(@Query("screen_name") String userName, @Query("cursor") String cursorDefaultEksiBir, @Query("skip_status") String trueAyarla, @Query("include_user_entities") String falseAyarla, @Query("count") String count_198_Ayarla);
    }


}

