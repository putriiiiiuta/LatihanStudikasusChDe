package putriiiiiuta.androidlima.latihanstudikasuschde.network

import putriiiiiuta.androidlima.latihanstudikasuschde.model.GetAllUserResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("user")
    fun getDataUser(
        @Query("username") username : String,
        @Query ("password") password : String
    ) : Call<List<GetAllUserResponseItem>>


    @POST("user")
    fun postDataUser(
        postRequest : PostUserResponseItem
    ) : Call<GetAllUserResponseItem>
}