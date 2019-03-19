package dmitrybelykh.test.goodsbrowser.services

import android.content.Context

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitServices(
    context: Context,
    login: String,
    password: String
) {

    var skladService: MoySkladService? = null
        private set

    val authToken: String

    init {
        authToken = Credentials.basic(login, password)
        createRetrofitInstance(context, authToken)
    }

    private fun createRetrofitInstance(
        context: Context,
        authToken: String
    ) {
        val httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor();
        //val chuckInterceptor: ChuckInterceptor = ChuckInterceptor(context);
        val headerAuthorizationInterceptor: Interceptor = Interceptor { chain ->
            var request: Request = chain.request()
            val headers: Headers = request.headers().newBuilder().add("Authorization", authToken).build()
            request = request.newBuilder().headers(headers).build()
            chain.proceed(request)
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(headerAuthorizationInterceptor)
            .addNetworkInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
            //.addNetworkInterceptor(chuckInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://online.moysklad.ru/api/remap/1.1/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        skladService = retrofit.create(MoySkladService::class.java)
    }

}