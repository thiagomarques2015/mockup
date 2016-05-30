package br.net.mockup.control.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

import static br.net.mockup.control.util.Printlog.debug;
import static br.net.mockup.control.util.Printlog.erro;

/**
 * Created by Thiago on 17/11/2015.
 */
public class HttpClientJSON {

    private static final HttpClientJSON instance = new HttpClientJSON();

    private HttpCallback.PostJSONListener callbackJSON;
    private Context context;

    private HttpClientJSON() {
    }

    public synchronized static HttpClientJSON getInstance() {
        return instance;
    }

    public HttpClientJSON setCallbackJSON(HttpCallback.PostJSONListener callbackJSON) {
        this.callbackJSON = callbackJSON;
        return this;
    }

    public HttpClientJSON setContext(Context context) {
        this.context = context;
        return this;
    }

    public void connect(String url, JSONObject jsonParams, AsyncHttpClient client) throws Exception{
        if(context == null) throw new RuntimeException("Não é possível iniciar uma conexão sem o contexto da aplicacao");
        StringEntity entity = new StringEntity(jsonParams.toString());
        debug("=> Enviando o JSON : " + jsonParams.toString());
        client.post(context, url, entity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = getResponse(responseBody);

                if (callbackJSON != null)
                    callbackJSON.onSuccess(statusCode, response, responseBody);

                console(responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                String response = getResponse(responseBody);

                if (callbackJSON != null)
                    callbackJSON.onFailure(statusCode, response, responseBody, error);

                console(responseBody);
            }
        });
    }

    private String getResponse(byte[] responseBody){
        String response = "";

        try {
            response = new String(responseBody, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return response;
    }

    private static void console(byte[] responseBody){
        try {
            String str = new String(responseBody, "UTF-8");
            debug("=> Response : " + str);
        } catch (Exception e) {
            //e.printStackTrace();
            erro("=> Não foi possivel printar o resultado do response JSON");
        }
    }
}
