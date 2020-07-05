package br.com.tecdev.btctrade.data.net

import br.com.tecdev.btctrade.BuildConfig
import br.com.tecdev.btctrade.model.AllCoinsResponse
import br.com.tecdev.btctrade.model.MbtcResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface RestApi {

    @GET("{coin}/ticker")
    suspend fun getCoin(@Path("coin") coin: String): MbtcResponse

    companion object {
        private const val TIMEOUT_READ = 40L
        private const val TIMEOUT_WRITE = 40L
        private const val TIMEOUT_CONNECT = 30L

        val okHttpClient: OkHttpClient = OkHttpClient
            .Builder()
            .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
            .addInterceptor(getInterceptor())
            .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
            .build()

        private fun getInterceptor(): HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            return interceptor
        }
    }
}