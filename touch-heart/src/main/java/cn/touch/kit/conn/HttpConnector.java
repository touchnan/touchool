/*
 * cn.touch.kit.conn.HttpConnector.java
 * Aug 21, 2012 
 */
package cn.touch.kit.conn;

import java.io.IOException;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.nutz.lang.Strings;
import org.nutz.log.Logs;

import cn.touch.util.Constants;

/**
 * Aug 21, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public class HttpConnector {
    private static PoolingClientConnectionManager cm;
    private HttpClient client;
    // private int connectTimeOut;
    // private int readTimeOut;

    private ResponseHandler<String> stringResponseHandler = new BasicResponseHandler();

    private ResponseHandler<byte[]> byteResponseHandler = new ResponseHandler<byte[]>() {
        public byte[] handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            StatusLine statusLine = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            if (statusLine.getStatusCode() >= 300) {
                EntityUtils.consume(entity);
                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }
            return ((entity == null) ? null : EntityUtils.toByteArray(entity));
        }
    };

    public static ClientConnectionManager getConnManager() {
        return cm;
    }

    public HttpConnector() {
        this(60000, 120000);// 连接超时1分钟，读数据(连接后)超时2分钟
    }

    public HttpConnector(HttpHost proxy, UsernamePasswordCredentials creds) {
        this(proxy, creds, 60000, 120000);// 连接超时1分钟，读数据(连接后)超时2分钟
    }

    /**
     * @param connectTimeOut 连接超时时间
     * @param readTimeOut 读取超时时间
     */
    public HttpConnector(int connectTimeOut, int readTimeOut) {

        // this.connectTimeOut = connectTimeOut;
        // this.readTimeOut = readTimeOut;
        

        HttpParams params = new BasicHttpParams();
        // 版本
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

        // Activates 'Expect: 100-continue' handshake for the entity enclosing methods.
        // HttpProtocolParams.setUseExpectContinue(params, true);
        
        // 超时
        // 设置连接超时时间
        HttpConnectionParams.setConnectionTimeout(params, connectTimeOut);
        // 设置读取超时时间
        HttpConnectionParams.setSoTimeout(params, readTimeOut);
        
        this.client = new DefaultHttpClient(cm, params);

        //this.client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectTimeOut);
        //this.client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, readTimeOut);

        // HttpConnectionParams.setConnectionTimeout(this.client.getParams(),
        // connectTimeOut);
    }

    public HttpConnector(HttpHost proxy, UsernamePasswordCredentials creds, int connectTimeOut, int readTimeOut) {
        // HttpHost proxy = new HttpHost("proxy.tt", 8080);
        // UsernamePasswordCredentials creds = new UsernamePasswordCredentials("fttj", "ft07");
        this(connectTimeOut, readTimeOut);
        // 创建代理
        if (proxy != null) {
            this.client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        }
        // 验证信息不为空
        if (creds != null) {
            // 实例化验证
            CredentialsProvider credsProvider = new BasicCredentialsProvider();

            // 创建验证
            credsProvider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), creds);
            ((DefaultHttpClient) client).setCredentialsProvider(credsProvider);
        }
    }

    public static void setMaxConns(int maxTotal, int maxPerRoute) {
        cm.setMaxTotal(maxTotal);
        cm.setDefaultMaxPerRoute(maxPerRoute);
    }

    public String get(String url) {
        return get(url, stringResponseHandler);
    }

    public <T> T get(String url, ResponseHandler<T> responseHandler) {
        HttpGet httpget = new HttpGet(url);
        try {
            return client.execute(httpget, responseHandler);
        } catch (Exception e) {
            Logs.getLog(HttpConnector.class).error(e);
            httpget.abort();
            throw new RuntimeException(e);
        }
//        finally {
//            httpget.releaseConnection();
//        }
    }

    public byte[] getBytes(String url) {
        return get(url, byteResponseHandler);
    }

    public String get(String url, String queryStr) {
        if (!Strings.isBlank(queryStr)) {
            url = url + (url.indexOf(Constants.QUESTION_MARK) != (-1) ? Constants.AND : Constants.QUESTION_MARK) + queryStr;
        }
        return get(url);
    }

    public byte[] getBytes(String url, String queryStr) {
        if (!Strings.isBlank(queryStr)) {
            url = url + (url.indexOf(Constants.QUESTION_MARK) != (-1) ? Constants.AND : Constants.QUESTION_MARK) + queryStr;
        }
        return getBytes(url);
    }

    public String get(String url, List<NameValuePair> nvps) {
        return get(url, URLEncodedUtils.format(nvps, Consts.UTF_8));
    }

    public byte[] getBytes(String url, List<NameValuePair> nvps) {
        return getBytes(url, URLEncodedUtils.format(nvps, Consts.UTF_8));
    }

    public String post(String url) {
        return postExecute(url, null);
    }

    public byte[] postBytes(String url) {
        return postBytesExecute(url, null);
    }

    public String post(String url, List<NameValuePair> nvps) {
        return postExecute(url, new UrlEncodedFormEntity(nvps, Consts.UTF_8));
    }

    public byte[] postBytes(String url, List<NameValuePair> nvps) {
        return postBytesExecute(url, new UrlEncodedFormEntity(nvps, Consts.UTF_8));
    }

    public String post4Parameters(String url, String parameters) {
        List<NameValuePair> nvps = URLEncodedUtils.parse(parameters, Consts.UTF_8);
        return postExecute(url, new UrlEncodedFormEntity(nvps, Consts.UTF_8));
    }

    public byte[] postBytes4Parameters(String url, String parameters) {
        List<NameValuePair> nvps = URLEncodedUtils.parse(parameters, Consts.UTF_8);
        return postBytesExecute(url, new UrlEncodedFormEntity(nvps, Consts.UTF_8));
    }

    public String post4Stream(String url, String data) {
        return postExecute(url, new StringEntity(data, Consts.UTF_8));
    }

    public byte[] postBytes4Stream(String url, String data) {
        return postBytesExecute(url, new StringEntity(data, Consts.UTF_8));
    }

    public String postExecute(String url, HttpEntity entity) {
        return postExecute(url, entity, stringResponseHandler);
    }

    public byte[] postBytesExecute(String url, HttpEntity entity) {
        return postExecute(url, entity, byteResponseHandler);
    }

    public <T> T postExecute(String url, HttpEntity entity, ResponseHandler<T> responseHandler) {
        HttpPost httpost = new HttpPost(url);
        if (entity != null) {
            httpost.setEntity(entity);
        }
        try {
            return client.execute(httpost, responseHandler);
        } catch (Exception e) {
            Logs.getLog(HttpConnector.class).error(e);
            httpost.abort();
            throw new RuntimeException(e);
        }
//        finally {
//            httpost.releaseConnection();
//        }
    }

    static {
        
        /**
         * 最大连接数
         */
        int MAX_TOTAL_CONNECTIONS = 800;
        /**
         * 每个路由最大连接数
         */
        int MAX_ROUTE_CONNECTIONS = 400;

        
        cm = new PoolingClientConnectionManager();
        cm.setMaxTotal(MAX_TOTAL_CONNECTIONS);// 设置连接最大数
        cm.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);// 设置每个Route的连接最大数
    }

}
