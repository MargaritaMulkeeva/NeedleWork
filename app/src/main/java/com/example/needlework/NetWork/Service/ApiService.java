package com.example.needlework.NetWork.Service;

import com.example.needlework.NetWork.Models.discussions.CreateDiscussionRequestBody;
import com.example.needlework.NetWork.Models.discussions.GetDiscussionByCritetionRequestBody;
import com.example.needlework.NetWork.Models.discussions.GetDiscussionByCritetionResponseBody;
import com.example.needlework.NetWork.Models.discussions.GetDiscussionByIdRequestBody;
import com.example.needlework.NetWork.Models.knittingPatterns.CategoriesOfPatternResponseBody;
import com.example.needlework.NetWork.Models.lessons.LessonResponseBody;
import com.example.needlework.NetWork.Models.messages.CreateMessageRequestBody;
import com.example.needlework.NetWork.Models.messages.CreateMessageResponseBody;
import com.example.needlework.NetWork.Models.messages.GetMessageByDiscussionRequestBody;
import com.example.needlework.NetWork.Models.messages.MessageResponseBody;
import com.example.needlework.NetWork.Models.user.ChangeAvatarRequestBody;
import com.example.needlework.NetWork.Models.user.ChangeLoginRequestBody;
import com.example.needlework.NetWork.Models.user.ChangeNickNameRequestBody;
import com.example.needlework.NetWork.Models.discussions.DiscussionsResponseBody;
import com.example.needlework.NetWork.Models.knittingPatterns.GetKnittingPatternRequestBody;
import com.example.needlework.NetWork.Models.user.ChangePasswordRequestBody;
import com.example.needlework.NetWork.Models.user.GetUserRequestBody;
import com.example.needlework.NetWork.Models.knittingPatterns.KnittingPatternResponseBody;
import com.example.needlework.NetWork.Models.user.LoginRequestBody;
import com.example.needlework.NetWork.Models.user.LoginResponseBody;
import com.example.needlework.NetWork.Models.user.RegistrationRequestBody;
import com.example.needlework.NetWork.Models.user.RegistrationResponseBody;
import com.example.needlework.NetWork.Models.user.SignOutRequestBody;
import com.example.needlework.NetWork.Models.user.UserResponseBody;
import com.example.needlework.NetWork.Models.user.UserUpdateResponseBody;
import com.example.needlework.NetWork.Models.userBookmarks.CreateUserBookmarkRequestBody;
import com.example.needlework.NetWork.Models.userBookmarks.GetUserBookmarkRequestBody;
import com.example.needlework.NetWork.Models.userBookmarks.UserBookmarksResponseBody;
import com.example.needlework.NetWork.Models.userRatingForDiscussions.CreateUserRatingForDiscussionRequestBody;
import com.example.needlework.NetWork.Models.userRatingForDiscussions.GetAverageUserRatingForDiscussionRequestBody;
import com.example.needlework.NetWork.Models.userRatingForDiscussions.GetAverageUserRatingForDiscussionResponseBody;
import com.example.needlework.NetWork.Models.userRatingForDiscussions.UserRatingsForDiscussionsResponseBody;
import com.example.needlework.NetWork.Models.userRatingForPatterns.CreateUserRatingForPatternRequestBody;
import com.example.needlework.NetWork.Models.userRatingForPatterns.GetAverageUserRatingForPatternRequestBody;
import com.example.needlework.NetWork.Models.userRatingForPatterns.GetAverageUserRatingForPatternResponseBody;
import com.example.needlework.NetWork.Models.userRatingForPatterns.UserRatingForPatternsResponseBody;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiService {
    @POST("users/sign-in")
    Call<LoginResponseBody> doLogin(@Body LoginRequestBody registerBody);
    @POST("users/sign-up")
    Call<RegistrationResponseBody> doRegistration(@Body RegistrationRequestBody registrationBody);
    @POST("users/sign-out")
    Call<ResponseBody> doSignOut(@Body SignOutRequestBody body);
    @GET("users/user")
    Call<UserResponseBody> getUser(@Body GetUserRequestBody body);
    @PUT("/users/changeNickName")
    Call<UserUpdateResponseBody> changeNickName(@Body ChangeNickNameRequestBody body);
    @PUT("/users/changeAvatar")
    Call<UserUpdateResponseBody> changeAvatar(@Body ChangeAvatarRequestBody body);
    @PUT("/users/changePassword")
    Call<UserUpdateResponseBody> changePassword(@Body ChangePasswordRequestBody body);
    @PUT("/users/changeLogin")
    Call<UserUpdateResponseBody> changeLogin(@Body ChangeLoginRequestBody body);

    @GET("patterns-type/get-all")
    Call<List<CategoriesOfPatternResponseBody>> getCategoriesOfPattern();
    @GET("knittingPatterns/get-popular")
    Call<List<KnittingPatternResponseBody>> getPopularKnittingPatterns();
    @GET("knittingPatterns/get-one")
    Call<KnittingPatternResponseBody> getKnittingPattern(@Body GetKnittingPatternRequestBody body);

    @GET("discussions-type/get-all")
    Call<List<CategoriesOfPatternResponseBody>> getCategoriesOfDiscussion();
    @POST("discussions/create")
    Call<DiscussionsResponseBody> createDiscussion(@Body CreateDiscussionRequestBody body);
    @GET("discussions/by-criterion")
    Call<GetDiscussionByCritetionResponseBody> getDiscussionsByCritetion(@Body GetDiscussionByCritetionRequestBody body);
    @GET("discussions/get-one")
    Call<DiscussionsResponseBody> getDiscussionsById(@Body GetDiscussionByIdRequestBody body);

    @GET("lessons/get-all")
    Call<List<LessonResponseBody>> getLessons();

    @POST("messages/create")
    Call<CreateMessageResponseBody> createMessage(@Body CreateMessageRequestBody body);
    @GET("message/get-discussion")
    Call<GetDiscussionByCritetionResponseBody> getMessageByDiscussion(@Body GetMessageByDiscussionRequestBody body);

    @POST("userBookmarks/create")
    Call<UserBookmarksResponseBody> createBookmark(@Body CreateUserBookmarkRequestBody body);
    @GET("userBookmarks/get-all")
    Call<List<UserBookmarksResponseBody>> getBookmarks(@Body GetUserBookmarkRequestBody body);

    @POST("userRatingForDiscussion/create")
    Call<UserRatingsForDiscussionsResponseBody> createRatingForDiscussion(@Body CreateUserRatingForDiscussionRequestBody body);
    @GET("userRatingForDiscussion/rating")
    Call<GetAverageUserRatingForDiscussionResponseBody> getAverageRatingForDiscussion(@Body GetAverageUserRatingForDiscussionRequestBody body);

    @POST("userRatingForPattern/create")
    Call<UserRatingForPatternsResponseBody> createRatingForPattern(@Body CreateUserRatingForPatternRequestBody body);
    @GET("userRatingForPattern/rating")
    Call<GetAverageUserRatingForPatternResponseBody> getAverageRatingForDiscussion(@Body GetAverageUserRatingForPatternRequestBody body);
}
