package io.github.lizhangqu.coreprogress;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadProgressInterceptor implements Interceptor {
    private ProgressListener progressListener;

    public UploadProgressInterceptor(@NotNull ProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        RequestBody originalBody = originalRequest.body();
        if (originalBody == null) {
            return chain.proceed(originalRequest);
        }

        Request progressRequest = originalRequest.newBuilder()
                .method(originalRequest.method(),
                        new ProgressRequestBody(originalBody, progressListener))
                .build();

        return chain.proceed(progressRequest);
    }
}