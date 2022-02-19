package kr.co.today.server

import io.reactivex.Single
import org.bson.Document
import org.json.JSONObject
import retrofit2.http.*


interface RetrofitInterface {

    //회원가입
    @GET("getVilageFcst")
    fun tempData(
        @Query("ServiceKey")   ServiceKey: String,
        @Query("pageNo")   pageNo: String,
        @Query("numOfRows")   numOfRows: String,
        @Query("dataType")   dataType: String,
        @Query("base_date")   base_date: String,
        @Query("base_time")   base_time: String,
        @Query("nx")   nx: String,
        @Query("ny")   ny: String,
    ): Single<Document>

    //회원가입
    @GET("1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1")
    fun tempFoodData(
        @Query("ServiceKey")   ServiceKey: String,
        @Query("desc_kor")   desc_kor: String,
        @Query("bgn_year")   bgn_year: String,
        @Query("pageNo")   pageNo: String,
        @Query("numOfRows")   numOfRows: String,
        @Query("type")   type: String
    ): Single<Document>

    //회원가입
    @GET("http://openapi.foodsafetykorea.go.kr/api/7aeb97bd33864730b9bd/COOKRCP01/json/1/10")
    fun tempRecipeData(): Single<Document>

}