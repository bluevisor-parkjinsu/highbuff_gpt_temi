package kr.bluevisor.robot.libs.core.di

import android.content.Context
import android.content.SharedPreferences
import com.robotemi.sdk.Robot
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.bluevisor.robot.libs.data.GptApiContract
import kr.bluevisor.robot.libs.data.GptApis
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppProvidingModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor { chain ->
                val sourceRequest = chain.request()
                val newRequest = sourceRequest.newBuilder()
                    .header("Authorization", "Bearer ${GptApiContract.API_KEY}")
                    .method(sourceRequest.method, sourceRequest.body)
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(GptApiContract.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideGptApis(retrofit: Retrofit): GptApis {
        return retrofit.create(GptApis::class.java)
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            "${context.packageName}.global_shared_preference",
            Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun providesTemiCoreRobot(): Robot {
        return Robot.getInstance()
    }
}