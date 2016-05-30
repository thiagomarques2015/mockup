package br.net.mockup.control.http;

/**
 * Created by Thiago on 18/11/2015.
 */
public class HttpCallback {
    public interface PostJSONListener{
        void onSuccess(int statusCode, String response, byte[] responseBody);
        void onFailure(int statusCode,  String response, byte[] responseBody, Throwable error);
    }
}
