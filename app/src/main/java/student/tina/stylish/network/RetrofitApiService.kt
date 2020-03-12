package student.tina.stylish.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import student.tina.stylish.`object`.ProductListItem
import student.tina.stylish.`object`.User
import student.tina.stylish.`object`.UserManager
import student.tina.stylish.`object`.UserProfile
import student.tina.stylish.view.MarketingHotsResult
import student.tina.stylish.view.StylishAccessResult


enum class StylishApiStatus { LOADING, ERROR, DONE }

/**
 * TODO(01)
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 *
 * implementation "com.squareup.moshi:moshi:1.8.0"
 * implementation "com.squareup.moshi:moshi-kotlin:1.8.0"
 * implementation "com.squareup.retrofit2:converter-moshi:2.5.0"
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * TODO(02)
 * Build the OkHttpClient object that Retrofit will be using, making sure to add the logging interceptor for
 * check response. Setup level to Level.BODY that we will know all information about http connect.
 *
 * implementation("com.squareup.okhttp3:logging-interceptor:4.0.1")
 */
private val client = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
    .build()

/**
 * TODO(03)
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 *
 * And using an OkHttpClient with our OkHttpClient object.
 *
 * implementation "com.squareup.retrofit2:retrofit:2.5.0"
 * implementation "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"
 *
 */
const val BASE_URL = "https://api.appworks-school.tw/api/1.0/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .client(client)
    .baseUrl(BASE_URL)
    .build()


/**
 * TODO(04)
 * A public interface that exposes the [getMarketingHots] method
 */
interface RetrofitApiService {


    /**
     * TODO(05)
     * Returns a Coroutine [Deferred] [Property Object] which can be fetched with await() if
     * in a Coroutine scope.
     *
     * Make sure our BASE_URL includes api path and api_version.
     *
     * The @GET annotation indicates that the "marketing/hots" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("marketing/hots")
    fun getMarketingHots(): Deferred<MarketingHotsResult>

    @FormUrlEncoded
    @POST("user/signin")
    fun setUserLogin(@Field("provider") provider: String = "facebook", @Field(
            "access_token"
        ) token: String
    ) : Deferred<StylishAccessResult>


    @GET("user/profile")
    @Headers( "Content-Type: application/json;charset=UTF-8")
    fun getUserProfile(@Header("Authorization") string:String) : Deferred<UserProfile>
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result

    @GET("products/{category}")
    fun getCatagoryItem(@Path("category") category: String, @Query("paging") nextPaging: Int?)
    : Deferred<ProductListItem>
}

/**
 * TODO(06)
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object RetrofitApi {
    val retrofitService: RetrofitApiService by lazy {
        retrofit.create(
            RetrofitApiService::class.java
        )
    }
}
