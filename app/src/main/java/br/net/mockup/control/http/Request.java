package br.net.mockup.control.http;

import com.github.kevinsawicki.http.HttpRequest;
import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static br.net.mockup.control.util.Printlog.debug;
import static br.net.mockup.control.util.Printlog.info;

/**
 * Created by Opera House on 30/01/2015.
 */
public abstract class Request<T> {

    // Servidor
    private String URL = "URL DO SERVIDOR";

    /**
     * Retornos de possiveis erros
     */
    public static final int NENHUM_ERROR = 0; // HttpException
    public static final int ERROR_CONEXAO = 1; // HttpException
    public static final int ERROR_INESPERADO = 2; // RunntimeException, NullpointerException
    public static final int ERROR_SINTAXE_JSON = 3; // JSONException

    public static final int POST = 1;
    public static final int GET = 2;
    // Timeout de resposta do servidor
    protected int TIMEOUT = 20000;
    // Tipo de requisicao que sera executada
    private int request;
    // Execeção lancada caso ocorra algum erro
    private Exception exception;
    // Tipo de erro ocorrido
    private int error;
    // Response Handle da requisicao
    private HttpCallback.PostJSONListener callbackJSON;
    // Api client para conexao
    private AsyncHttpClient client;

    private final Map<String, Object> data;
    private String response;

    public Request(String URL) {
        this.URL = URL;
        data = new HashMap<>();
    }

    public Request(String URL, int timeout) {
        this.URL = URL;
        this.TIMEOUT = timeout;
        data = new HashMap<>();
    }

    public abstract T config();

    /**
     * Ativa um callback para a conexao informando se a requisicao foi bem sucediada ou não
     * @param callbackJSON referencia do callback
     * @return instancia de Request
     */
    public Request<T> setCallbackJSON(HttpCallback.PostJSONListener callbackJSON) {
        this.callbackJSON = callbackJSON;
        return this;
    }

    public Request<T> setClient(AsyncHttpClient client) {
        this.client = client;
        return this;
    }

    public Request<T> request(int tipo) {
        this.request = tipo;
        return this;
    }

    public final int getRequest() {
        return request;
    }

    public JSONObject send(int method){

        info("#### EXECUTANDO UMA REQUISICAO HTTP ####");

        JSONObject json = null;
        exception = null;
        error = NENHUM_ERROR;

        try {
            // Recupera a localizacao ( latitude, longitude )
            //getLocalizacao();

            print(); // Imprimir todos parametros da requisição

            response = "";

            switch (method){
                case GET:  // Se o método é GET
                    get();
                break;
                case POST :  // Se o método é POST
                    post();
                break;
            }

            info("[RESPONSE] : " + response);

            if (!response.isEmpty()) {
                json = new JSONObject(response);
            } else {
                json = null;
            }

        }catch (Exception e){
            exception = e;

            if(error == NENHUM_ERROR){
                if(e instanceof JSONException)
                    error = ERROR_SINTAXE_JSON;
                else
                    error = ERROR_INESPERADO;
            }

            e.printStackTrace();
            json = null;
        }

        return json;
    }

    private void get() throws Exception{
        HttpRequest request = HttpRequest.get(getUrl(), data, true).connectTimeout(TIMEOUT);

        info("#### Enviando via GET #### \n [URL] " + URL);

        if (!request.ok()) {
            error = ERROR_CONEXAO;
            throw new UnsupportedOperationException("Página não encontrada");
        }

        response = request.body();
    }

    private void post() throws Exception{
        HttpClient client = new HttpClient(getUrl());
        client.connectForMultipart();
        //Log.d(Constante.LOG, "[parametros] " + data.toString());
        // Adiciona os parametros na conexao POST
        for (Map.Entry<String, Object> entrada : data.entrySet()) {
            //Log.d(Constante.LOG, "[VALORES] " + entrada.toString());

            if (entrada.getValue() instanceof HashMap) { // Se é arquivo ( byte ) Map<String, Object> => key, value ( Map<String, Object> )
                Map<String, Object> arquivos = (Map<String, Object>) entrada.getValue();
                for (Map.Entry<String, Object> arquivo : arquivos.entrySet()) {
                    String nomeImagem = arquivo.getKey();
                    byte[] byteImage_photo = (byte[]) arquivo.getValue();
                    client.addFilePart(entrada.getKey(), nomeImagem, byteImage_photo);
                }
            } else {
                if (entrada.getValue() != null) {
                    client.addFormPart(entrada.getKey(), entrada.getValue().toString());
                    // Log.i(Constante.INFO, " Adicionando : " + entrada.getKey() + " : " + entrada.getValue().toString());
                }
            }
        }

        info("#### Enviando via POST #### \n" +
                " [URL] " + URL);

        client.finishMultipart();
        response = client.getResponse();
        boolean ok = client.OK();

        if (!ok) {
            error = ERROR_CONEXAO;
            throw new UnsupportedOperationException("Página não encontrada");
        }
    }

    public final Request<T> remove(String key) {
        if(data.containsKey(key))
            data.remove(key);
        return this;
    }

    public Exception getException() {
        return exception;
    }

    public String getResponse() {
        return response;
    }

    public final Request<T> add(String key, Object valor) {
        data.put(key, valor);
        return this;
    }

    public final Request<T> print()
    {
        for(Map.Entry<String, Object> entrada : this.data.entrySet())
        {
            if(entrada.getKey() != null && entrada.getValue() != null)
                debug(entrada.getKey() + ":" + entrada.getValue().toString());
            else
                debug("O valor de " + entrada.getKey() + ": não existe");
        }
        return this;
    }

    public final Request<T> clear(){
        data.clear();
        return this;
    }

    public int getError() {
        return error;
    }

    public final String getUrl() {
        return URL;
    }
}
