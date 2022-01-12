package com.snail.config;

import com.snail.utils.Executor;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class ClientSchedule {

    @Scheduled(cron = "0 20 0 * * ?")
    public void schedule() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("http://42.192.164.50:9999/proxy/node/getConfiguration");
        HttpPost httpPost = new HttpPost("http://42.192.164.50:9999/proxy/traffic/save");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == 200) {
                String configuration = EntityUtils.toString(httpEntity);
                BufferedWriter out = new BufferedWriter(new FileWriter("/usr/local/etc/xray/config.json", false));
                out.write(configuration);
                out.close();
                String status = Executor.execute("xray api statsquery --server=127.0.0.1:10085  'pattern: \"\" reset: true'");
                BufferedWriter out1 = new BufferedWriter(new FileWriter("/usr/local/etc/xray/1.json", false));
                out1.write(status);
                out1.close();
                StringEntity stringEntity = new StringEntity(status, "UTF-8");
                httpPost.setEntity(stringEntity);
                httpPost.setHeader("Content-Type", "application/json;charset=utf8");
                httpClient.execute(httpPost);
                Executor.execute("systemctl restart xray");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
