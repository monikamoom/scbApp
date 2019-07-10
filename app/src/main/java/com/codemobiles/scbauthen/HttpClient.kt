package com.codemobiles.scbauthen

import com.codemobiles.scbauthen.beans.YoutubeBean
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface HttpClient {
    @GET("/adhoc/youtubes/index_new.php/")
    fun feed(@Query("username") username:String,
                      @Query("password") password:String,
                      @Query("type") type:String):Call<YoutubeBean>
//    @GET("/posts")
//    fun feedType():Call<List<JsonTest>>

//    companion object{
//        val BASE_URL = "https://jsonplaceholder.typicode.com"
//
//        fun create(): HttpClient {
//
//            val retrofit = Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory
//                    .create())
//                .build()
//            return retrofit.create(HttpClient::class.java)
//
//
//        }
//    }

    companion object{
        val BASE_URL = "http://codemobiles.com"

        fun create(): HttpClient {

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(
                    GsonConverterFactory
                .create())
                .build()
            return retrofit.create(HttpClient::class.java)


        }
    }
}