package uk.co.intelitrack.intelizzz.common.api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.data.remote.Token;
import uk.co.intelitrack.intelizzz.components.login.LoginActivity;

/**
 * Created by Oliver on 1/18/2018.
 */

public class LoggingInterceptor implements Interceptor {
    private Context activity;
    private Token token;
    private boolean logout = false;

    @SuppressLint("WrongConstant")
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String access_token = null;
        try {
            access_token = token.getJsession();
        } catch (NullPointerException e) {
        }
        request = request.newBuilder().addHeader("Authorization", "Basic " + ((access_token != null) ? access_token : "")).build();
        long t1 = System.nanoTime();
        if ((request.body() != null && request.body().contentLength() < 2048) || request.body() == null)
            Log.d("Retrofit", String.format("Sending request %s on %s%n%s", request.url(), chain
                    .connection(), request.headers()) + " Params " + bodyToString(request));
        Response response = chain.proceed(request);
        String responseBodyString = response.body().string();
        long t2 = System.nanoTime();
//        Log.d("Retrofit", String.format("Received response for %s in %.1fms%n%s", response.request
//                ().url(), (t2 - t1) / 1e6d, response.headers()) + "Response code:" + response.code() + "\n" + "Body " + responseBodyString);
//        if ((response.code() == 403 || response.code() == 401) && !(activity instanceof LoginActivity) && !response.request().url().toString().contains(Constants.USER_DETAILS) && !logout) {
//            JSONObject obj = null;
//            String message = "";
//            try {
//                obj = new JSONObject(responseBodyString);
//                if (obj.has("message"))
//                    message = obj.getString("message");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            logout = true;
//        }
        return response.newBuilder().body(ResponseBody.create(response.body().contentType(),
                responseBodyString)).build();
    }
    private String bodyToString(final Request request) {
        try {
            final Buffer buffer = new Buffer();
            request.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        } catch (NullPointerException e) {
            return "did not work nullPointer";
        } catch (OutOfMemoryError e) {
            return "OutOfMemoryError ";
        }
    }
}