package com.example.needlework.NetWork.Service;

import com.example.needlework.NetWork.Models.discussions.CreateDiscussionRequestBody;
import com.example.needlework.NetWork.Models.discussions.GetDiscussionByCritetionResponseBody;
import com.example.needlework.NetWork.Models.knittingPatterns.CategoriesOfPatternResponseBody;
import com.example.needlework.NetWork.Models.knittingPatterns.GetAllKnittingPatterns;
import com.example.needlework.NetWork.Models.lessons.LessonResponseBody;
import com.example.needlework.NetWork.Models.messages.CreateMessageRequestBody;
import com.example.needlework.NetWork.Models.messages.CreateMessageResponseBody;
import com.example.needlework.NetWork.Models.messages.GetMessageByDiscussionResponseBody;
import com.example.needlework.NetWork.Models.user.ChangeAvatarRequestBody;
import com.example.needlework.NetWork.Models.user.ChangeLoginRequestBody;
import com.example.needlework.NetWork.Models.user.ChangeNickNameRequestBody;
import com.example.needlework.NetWork.Models.discussions.DiscussionsResponseBody;
import com.example.needlework.NetWork.Models.user.ChangePasswordRequestBody;
import com.example.needlework.NetWork.Models.knittingPatterns.KnittingPatternResponseBody;
import com.example.needlework.NetWork.Models.user.GetUserResponseBody;
import com.example.needlework.NetWork.Models.user.LoginRequestBody;
import com.example.needlework.NetWork.Models.user.LoginResponseBody;
import com.example.needlework.NetWork.Models.user.RegistrationRequestBody;
import com.example.needlework.NetWork.Models.user.RegistrationResponseBody;
import com.example.needlework.NetWork.Models.user.SignOutRequestBody;
import com.example.needlework.NetWork.Models.user.UserUpdateResponseBody;
import com.example.needlework.NetWork.Models.userBookmarks.CreateUserBookmarkRequestBody;
import com.example.needlework.NetWork.Models.userBookmarks.UserBookmarksResponseBody;
import com.example.needlework.NetWork.Models.userRatingForDiscussions.CreateUserRatingForDiscussionRequestBody;
import com.example.needlework.NetWork.Models.userRatingForDiscussions.GetAverageUserRatingForDiscussionResponseBody;
import com.example.needlework.NetWork.Models.userRatingForDiscussions.UserRatingsForDiscussionsResponseBody;
import com.example.needlework.NetWork.Models.userRatingForPatterns.CreateUserRatingForPatternRequestBody;
import com.example.needlework.NetWork.Models.userRatingForPatterns.GetAverageUserRatingForPatternResponseBody;
import com.example.needlework.NetWork.Models.userRatingForPatterns.UserRatingForPatternsResponseBody;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @POST("users/sign-in")
    Call<LoginResponseBody> doLogin(@Body LoginRequestBody registerBody);
    @POST("users/sign-up")
    Call<RegistrationResponseBody> doRegistration(@Body RegistrationRequestBody registrationBody);
    @POST("users/sign-out")
    Call<ResponseBody> doSignOut(@Body SignOutRequestBody body);
    @GET("users/user/{token}")
    Call<GetUserResponseBody> getUser(@Path("token") String token);
    @PUT("users/changeNickName")
    Call<UserUpdateResponseBody> changeNickName(@Body ChangeNickNameRequestBody body);
    @PUT("users/changeAvatar")
    Call<UserUpdateResponseBody> changeAvatar(@Body ChangeAvatarRequestBody body);
    @PUT("users/changePassword")
    Call<UserUpdateResponseBody> changePassword(@Body ChangePasswordRequestBody body);
    @PUT("users/changeLogin")
    Call<UserUpdateResponseBody> changeLogin(@Body ChangeLoginRequestBody body);

    @GET("patterns-type/get-all")
    Call<List<CategoriesOfPatternResponseBody>> getCategoriesOfPattern();
    @GET("knittingPatterns/get-popular")
    Call<List<KnittingPatternResponseBody>> getPopularKnittingPatterns();
    @GET("knittingPatterns/get-one/{id}")
    Call<KnittingPatternResponseBody> getKnittingPattern(@Path("id") long id);
    @GET("knittingPatterns/get-all")
    Call<List<GetAllKnittingPatterns>> getAllPatterns();

    @GET("discussions-type/get-all")
    Call<List<CategoriesOfPatternResponseBody>> getCategoriesOfDiscussion();
    @POST("discussions/create")
    Call<DiscussionsResponseBody> createDiscussion(@Body CreateDiscussionRequestBody body);
    @GET("discussions/by-criterion/{criterion}")
    Call<GetDiscussionByCritetionResponseBody> getDiscussionsByCriterion(@Path("criterion") String criterion);
    @GET("discussions/get-one/{id}")
    Call<DiscussionsResponseBody> getDiscussionsById(@Path("id") long id);
    @GET("discussions/get-all")
    Call<List<DiscussionsResponseBody>> getAllDisc();

    @GET("lessons/get-all")
    Call<List<LessonResponseBody>> getLessons();

    @POST("messages/create")
    Call<CreateMessageResponseBody> createMessage(@Body CreateMessageRequestBody body);
    @GET("messages/get-discussion/{id}")
    Call<GetMessageByDiscussionResponseBody> getMessageByDiscussion(@Path("id") long id);

    @POST("userBookmarks/create")
    Call<UserBookmarksResponseBody> createBookmark(@Body CreateUserBookmarkRequestBody body);
    @GET("userBookmarks/get-all/{token}")
    Call<List<UserBookmarksResponseBody>> getBookmarks(@Path("token") String token);

    @POST("userRatingForDiscussion/create")
    Call<UserRatingsForDiscussionsResponseBody> createRatingForDiscussion(@Body CreateUserRatingForDiscussionRequestBody body);
    @GET("userRatingForDiscussion/rating/{id}")
    Call<GetAverageUserRatingForDiscussionResponseBody> getAverageRatingForDiscussion(@Path("id") long id);

    @POST("userRatingForPattern/create")
    Call<UserRatingForPatternsResponseBody> createRatingForPattern(@Body CreateUserRatingForPatternRequestBody body);
    @GET("userRatingForPattern/rating/{id}")
    Call<GetAverageUserRatingForPatternResponseBody> getAverageRatingForPattern(@Path("id") long id);
}
