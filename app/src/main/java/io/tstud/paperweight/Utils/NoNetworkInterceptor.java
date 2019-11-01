package io.tstud.paperweight.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static io.tstud.paperweight.Utils.NetworkMonitor.isConnected;

public class NoNetworkInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if (!isConnected())
            throw new NoNetworkException();

        return chain.proceed(originalRequest);
    }

    public NoNetworkInterceptor() {
    }

    public class NoNetworkException extends IOException {

        @Nullable
        @Override
        public String getMessage() {
            return "Network unavailable. Please check your connection and retry";
        }
    }
}
