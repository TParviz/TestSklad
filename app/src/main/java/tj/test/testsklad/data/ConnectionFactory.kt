package tj.test.testsklad.data

import android.app.Application
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tj.test.testsklad.collectUrl
import tj.test.testsklad.getCredentials

open class ConnectionFactory {

    fun getRetrofit(application: Application): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(collectUrl(application))
        .client(
            OkHttpClient.Builder()
                .addInterceptor(Interceptor {
                    it.proceed(
                        it.request().newBuilder()
                            .addHeader(name = "Authorization", value = getCredentials(application))
                            .addHeader("Accept", "application/json").build()
                    )
                })
                .addInterceptor(
                    HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                .build()
        )
        .build()
}

