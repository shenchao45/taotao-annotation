package com.taotao.web.service;

import com.taotao.common.HttpResult;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by shenchao on 2017/2/15.
 */
@Service
public class ApiServiceBack {
    @Autowired
    private RequestConfig requestConfig;
    @Autowired
    public CloseableHttpClient closeableHttpClient;

    public String doGet(String url, Map<String, String> params) throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder(url);
        if (params != null) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                builder.setParameter(param.getKey(), param.getValue());
            }
        }
        return doGet(builder.build().toString());
    }

    public String doGet(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        try {
            response = closeableHttpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    public HttpResult doPost(String url) throws IOException {
        return doPost(url, null);
    }

    public HttpResult doPost(String url, Map<String, String> params) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        if (params != null) {
            List<NameValuePair> listParams = new ArrayList<>();
            for (Map.Entry<String, String> param : params.entrySet()) {
                listParams.add(new BasicNameValuePair(param.getKey(), param.getValue()));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(listParams);
            httpPost.setEntity(entity);
        }
        httpPost.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        try {
            response =closeableHttpClient.execute(httpPost);
            return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), "UTF-8"));
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

}
