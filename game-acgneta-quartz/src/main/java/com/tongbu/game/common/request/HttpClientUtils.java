package com.tongbu.game.common.request;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jokin
 * @date 2018/9/20 10:20
 */
public class HttpClientUtils {
    /**
     * log 记录
     */
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    private static final String ENCODING = "UTF-8";

    /**
     * GET 数据获取
     */
    public static String get(String url,
                             Map<String, String> paramMap,
                             Map<String, String> headerMap) {
        try {
            if (paramMap != null && paramMap.size() > 0) {
                List<NameValuePair> params = new ArrayList<>();
                for (String key : paramMap.keySet()) {
                    params.add(new BasicNameValuePair(key, paramMap.get(key)));
                }
                String queryString = URLEncodedUtils.format(params, ENCODING);
                // 存在?，表示这时的URL已经带参数了
                if (url.contains("?")) {
                    url += "&" + queryString;
                } else {
                    url += "?" + queryString;
                }
            }
            // 创建httpGet.
            HttpGet httpGet = new HttpGet(url);
            if (headerMap != null && headerMap.size() > 0) {
                headerMap.forEach(httpGet::addHeader);
            }
            String responseContent = "";
            // 执行get请求.
            try (CloseableHttpResponse response = HttpClientTool.getHttpClient().execute(httpGet)) {

                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    // 获取响应实体
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        responseContent = EntityUtils.toString(entity);
                    }
                } else {
                    // 返回状态不是 200 记录信息
                    logger.error(String.format("get\nurl:%s\nmessage:%s", url, response.getStatusLine()));
                }
            }
            // 中止请求，连接被释放回连接池
            httpGet.abort();
            return responseContent;
        } catch (Exception e) {
            logger.error(String.format("get:\n%s", url), e);
        }
        return "";
    }

    /**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     *
     * @param url     请求地址
     * @param params  参数
     * @param headers 头信息
     */
    public static String post(String url,
                              Map<String, Object> params,
                              Map<String, String> headers) {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(url);
        // 设置Header
        if (headers != null && headers.size() > 0) {
            headers.forEach(httpPost::setHeader);
        }
        // 创建参数队列
        List<NameValuePair> formParams = new ArrayList<>();
        if (params != null && params.size() > 0) {
            params.forEach((key, value) -> formParams.add(new BasicNameValuePair(key, String.valueOf(value))));
        }
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formParams, ENCODING);
            httpPost.setEntity(uefEntity);
            String responseContent = "";
            try (CloseableHttpResponse response = HttpClientTool.getHttpClient().execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        responseContent = EntityUtils.toString(entity, ENCODING);
                    }
                } else {
                    logger.error(String.format("post\nurl:%s\n params:%s\n headers:%s\n message:%s",
                            url,
                            params == null || params.size() == 0 ? "" : params.toString(),
                            headers == null || headers.size() == 0 ? "" : headers.toString(),
                            response.getStatusLine()));
                }
            }
            httpPost.abort();
            return responseContent;
        } catch (Exception e) {
            logger.error(String.format("post\nurl:%s\nparams:%s\nheader:%s",
                    url,
                    params == null || params.size() == 0 ? "" : params.toString(),
                    headers == null || headers.size() == 0 ? "" : headers.toString()), e);

        }
        return "";
    }

    public static String postData(String url,
                                  Map<String, String> headers,String postBody)
    {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(url);
        // 设置Header
        if (headers != null) {
            headers.forEach(httpPost::setHeader);
        }
        try {
            StringEntity se = new StringEntity(postBody, "UTF-8");
            httpPost.setEntity(se);
            String responseContent = "";
            try (CloseableHttpResponse response = HttpClientTool.getHttpClient().execute(httpPost)) {
                if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        result.append(line);
                    }
                    responseContent = result.toString();
                }
                else {
                    logger.error(String.format("post\nurl:%s\n data:%s\n headers:%s\n message:%s",
                            url,
                            postBody,
                            headers == null || headers.size() == 0 ? "" : headers.toString(),
                            response.getStatusLine()));
                }
            }
            httpPost.abort();
            return responseContent;
        } catch (Exception e) {
            logger.error(String.format("post\nurl:%s\ndata:%s\nheader:%s",
                    url,
                    postBody,
                    headers == null || headers.size() == 0 ? "" : headers.toString()), e);
        }
        return "";
    }

    public static String postData(String url,
                                  Map<String, String> headers,
                                  byte[] postData,ContentType contentType) {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(url);
        // 设置Header
        if (headers != null) {
            headers.forEach(httpPost::setHeader);
        }
        try {
            httpPost.setEntity(new ByteArrayEntity(postData,contentType));
            String responseContent = "";
            try (CloseableHttpResponse response = HttpClientTool.getHttpClient().execute(httpPost)) {
                if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        responseContent = EntityUtils.toString(entity, ENCODING);
                    }
                }
                else {
                    logger.error(String.format("post\nurl:%s\n data:%s\n headers:%s\n message:%s",
                            url,
                            new String(postData),
                            headers == null || headers.size() == 0 ? "" : headers.toString(),
                            response.getStatusLine()));
                }
            }
            httpPost.abort();
            return responseContent;
        } catch (Exception e) {
            logger.error(String.format("post\nurl:%s\ndata:%s\nheader:%s",
                    url,
                    new String(postData),
                    headers == null || headers.size() == 0 ? "" : headers.toString()), e);
        }
        return "";
    }

    public static byte[] postData(String url,
                                  Map<String, String> headers,
                                  byte[] postData) {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(url);
        // 设置Header
        if (headers != null) {
            headers.forEach(httpPost::setHeader);
        }
        try {
            httpPost.setEntity(new ByteArrayEntity(postData));
            try (CloseableHttpResponse response = HttpClientTool.getHttpClient().execute(httpPost)) {
                if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        return EntityUtils.toByteArray(entity);
                    }
                }
                else {
                    logger.error(String.format("post\nurl:%s\n data:%s\n headers:%s\n message:%s",
                            url,
                            new String(postData),
                            headers == null || headers.size() == 0 ? "" : headers.toString(),
                            response.getStatusLine()));
                }
            }
            httpPost.abort();
            return null;
        } catch (Exception e) {
            logger.error(String.format("post\nurl:%s\ndata:%s\nheader:%s",
                    url,
                    new String(postData),
                    headers == null || headers.size() == 0 ? "" : headers.toString()), e);
        }
        return null;
    }

    /**
     * HttpClient连接SSL
     */
    public static void ssl() {
        CloseableHttpClient httpclient = null;
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            FileInputStream instream = new FileInputStream(new File("d:\\tomcat.keystore"));
            try {
                // 加载keyStore d:\\tomcat.keystore
                trustStore.load(instream, "123456".toCharArray());
            } catch (CertificateException e) {
                e.printStackTrace();
            } finally {
                try {
                    instream.close();
                } catch (Exception ignore) {
                }
            }
            // 相信自己的CA和所有自签名的证书
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
            // 只允许使用TLSv1协议
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null,
                    new DefaultHostnameVerifier());
            httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            // 创建http请求(get方式)
            HttpGet httpget = new HttpGet("https://localhost:8443/myDemo/Ajax/serivceJ.action");
            System.out.println("executing request" + httpget.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                    System.out.println(EntityUtils.toString(entity));
                    EntityUtils.consume(entity);
                }
            } finally {
                response.close();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } finally {
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
