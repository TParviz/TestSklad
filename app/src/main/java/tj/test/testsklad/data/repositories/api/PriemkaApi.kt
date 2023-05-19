package tj.test.testsklad.data.repositories.api

import retrofit2.http.*
import tj.test.testsklad.data.models.PriemkaDataResponse
import tj.test.testsklad.data.models.PriemkaDetailsResponse
import tj.test.testsklad.data.models.SendCodesRequest

interface PriemkaApi {

    @GET("hs/API/Priemki")
    suspend fun getPriemkaList(): List<PriemkaDataResponse>

    @GET("hs/Nomenklatura/code/{code}")
    suspend fun getPriemkaDetails(@Path("code") code : String) : List<PriemkaDetailsResponse>

    @POST("hs/Markirovka/product_json/")
    suspend fun sendCode(@Body request :  SendCodesRequest)
}