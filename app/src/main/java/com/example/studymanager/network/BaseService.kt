package com.example.studymanager.network

import be.hogent.teamoverkill.fietsapp.util.converters.DateAdapter
import be.hogent.teamoverkill.fietsapp.util.converters.TimeAdapter
import com.example.studymanager.App
import com.example.studymanager.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object BaseService {

    private val userHelper = App.getUserHelper()

    private val BASE_URL: String
        get() {
            // De 10.0.2.2 of "localhost" url werkt enkel op emulator
            return "https://10.0.2.2:5001/api/"
        }

    private val logInterceptor: HttpLoggingInterceptor
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            interceptor.redactHeader("Authorization")

            return interceptor
        }

    private val moshi = Moshi.Builder()
        .add(DateAdapter())
        .add(TimeAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()

    val RETROFIT: Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .client(createHttpClient())
        .build()

    /** Maak een OkHttpClient op basis van debugging status */
    private fun createHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()

        // Source: https://www.vogella.com/tutorials/Retrofit/article.html
        okHttpClient.addInterceptor { chain ->
            val originalRequest: Request = chain.request()
            val builder = originalRequest
                .newBuilder()
                .header("content-type", "application/json")

            if (userHelper.isSignedIn()) {
                val authToken = userHelper.authToken
                builder.addHeader("Authorization", "Bearer $authToken")
            }

            val newRequest: Request = builder.build()
            chain.proceed(newRequest)
        }

        if (BuildConfig.DEBUG) {
            // Log all communication while debugging
            okHttpClient.addInterceptor(logInterceptor)

            // Accept all certs for hostnames while debugging
            try {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts: Array<TrustManager> = arrayOf(object : X509TrustManager {
                    override fun checkClientTrusted(
                        chain: Array<out X509Certificate>?,
                        authType: String?
                    ) = Unit

                    override fun checkServerTrusted(
                        chain: Array<out X509Certificate>?,
                        authType: String?
                    ) = Unit

                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                })

                // Install the all-trusting trust manager
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, SecureRandom())

                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = sslContext.socketFactory
                if (trustAllCerts.isNotEmpty() && trustAllCerts.first() is X509TrustManager) {
                    okHttpClient.sslSocketFactory(
                        sslSocketFactory,
                        trustAllCerts.first() as X509TrustManager
                    )
                    okHttpClient.hostnameVerifier(HostnameVerifier { _, _ -> true })
                }
            } catch (e: Exception) {
            }
        }

        return okHttpClient.build()
    }
}