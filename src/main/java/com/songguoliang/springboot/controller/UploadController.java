package com.songguoliang.springboot.controller;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Controller
@RequestMapping("test")
public class UploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件!!!";
        }

        String fileName = file.getOriginalFilename();

        String filePath = "C:/Users/m1882/Documents/upload/";
        File dest = new File(filePath + fileName);

        try {
            file.transferTo(dest);
//            MyThread d = new MyThread();
//            Thread t1 = new Thread(d);
//            Thread t2 = new Thread(d);
//            t1.start();
//            t2.start();
//            ExecutorService batchTaskPool = Executors.newSingleThreadExecutor();
//            for(int i=0; i<10; i++){
//                batchTaskPool.execute(new MyThread());
//            }
//            ExecutorService batchTaskPool = Executors.newCachedThreadPool();
//            for(int i=0; i<10; i++){
//                batchTaskPool.execute(new MyThread());
//            }
            ExecutorService batchTaskPool = Executors.newFixedThreadPool(2);
            for(int i=0; i<10; i++){
                batchTaskPool.execute(new MyThread(dest));
            }
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//            for(int i=0; i<100; i++){
//                String str = httpClientUploadFile(dest,i);
//                LOGGER.info("上传成功");
//            }
            return "上传成功";
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
        }
        return "上传失败！";
    }

}
