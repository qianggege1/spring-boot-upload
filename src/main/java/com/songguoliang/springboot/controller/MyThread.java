package com.songguoliang.springboot.controller;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class MyThread implements Runnable {

    private File file;
    private static final Logger LOGGER = LoggerFactory.getLogger(MyThread.class);

    public MyThread(File file) {
        this.file = file;
    }

    public void run() {
        //File file = new File("C:/Users/m1882/Documents/upload/源代码管理规范.doc");
        for(int i=0; i<100; i++){
            httpClientUploadFile(file,i);
            LOGGER.info("i="+i+"******"+Thread.currentThread().getName());
        }
    }

    public String httpClientUploadFile(File file, int i) {
        final String remote_url = "http://localhost:8081/test/transmit";// 第三方服务器请求地址
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        try {
            String fileName = i+file.getName();
            HttpPost httpPost = new HttpPost(remote_url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(Charset.forName("utf-8"));
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            InputStream stream = new FileInputStream(file);
            builder.addBinaryBody("file", stream, ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
            HttpEntity entity = builder.build();
            httpPost.setEntity((entity));
            CloseableHttpResponse response = httpClient.execute(httpPost);// 执行提交
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字符串
                result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
                LOGGER.info("result:"+result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
