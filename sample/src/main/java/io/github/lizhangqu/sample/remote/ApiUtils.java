package io.github.lizhangqu.sample.remote;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.github.lizhangqu.coreprogress.ProgressListener;
import io.github.lizhangqu.coreprogress.UploadProgressInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtils {
    public static ApiService getUploadService(String baseUrl, @NotNull ProgressListener progressListener) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new UploadProgressInterceptor(progressListener))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        return retrofit.create(ApiService.class);
    }
}
