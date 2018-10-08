package com.example.pooria.boomranq.Retrofit;

import com.example.pooria.boomranq.Model.GetPost;
import com.example.pooria.boomranq.Model.Post_Inf;
import com.example.pooria.boomranq.Model.SendPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MyBoomranQAPI {
    /*@FormUrlEncoded
    @POST("checkuser.php")
    Call<checkUserResponse> checkUserExist(@Field("phone") String phone);


    @FormUrlEncoded
    @POST("register.php")
    Call<User>registerNewUser(@Field("phone") String phone,
                              @Field("name") String name,
                              @Field("address") String address,
                              @Field("birthdate") String birthdate);
    @FormUrlEncoded
    @POST("getuser.php")
    Call<User>getUserInformation(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("getdrink.php")
    Observable<List<Drink>>getDrink(@Field("menuid") String menuID);

    @GET("getbanner.php")
    Observable<List<Banner>> getBanners();


    @GET("getmenu.php")
    Observable<List<Category>> getMenu();

    @Multipart
    @POST("upload.php")
    Call<String>uploadFile(@Part("phone")String phone, @Part MultipartBody.Part file);
*/

    @FormUrlEncoded
    @POST("upload.php")
    Call<SendPost> upload_Post(@Field("title") String title, @Field("link") String link);


    @GET("showPosts.php")
    Call<List<GetPost>> show_Posts();


    @GET("ShowWithDates.php")
    Call<List<Post_Inf>> show_PostsWitDates();

    @FormUrlEncoded
    @POST("showPostsById.php")
    Call<Post_Inf> upload_Inf_Post(@Field("postid") String postid,
                                   @Field("title") String title,
                                   @Field("like") String like,
                                   @Field("comment") String comment);

}
