package kr.co.today.server

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * HTTP 통신 관련 변수 및 상수
 */
object HttpAppClient{
    private const val TIMEOUT_CONNECT: Long = 15
    private const val TIMEOUT_WRITE: Long = 15
    private const val TIMEOUT_READ: Long = 15

    const val SCHEME = "https://"

    private var mClient: OkHttpClient
    private lateinit var appRetrofit: Retrofit
    private lateinit var appRetrofitInterface: RetrofitInterface
    init{
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        mClient = OkHttpClient().newBuilder().apply {
            addInterceptor(logInterceptor)
            connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
            writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
            readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
        }.build()
    }

    fun getAppInstance(): RetrofitInterface {
        return appRetrofitInterface
    }

    fun setUrl(url: String){
        appRetrofit = Retrofit.Builder().apply {
            baseUrl("$SCHEME$url")
            client(mClient)
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            addConverterFactory(GsonConverterFactory.create())
        }.build()

        appRetrofitInterface = appRetrofit.create(RetrofitInterface::class.java)
    }

    fun checkAppRetrofitInterfaceInit(): Boolean{
        return HttpAppClient::appRetrofitInterface.isInitialized
    }
}