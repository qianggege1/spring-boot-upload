package com.songguoliang.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Controller
@RequestMapping("test")
public class TransmitController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransmitController.class);

    @GetMapping("/transmit")
    public String transmit() {
        return "transmit";
    }

    @PostMapping("/transmit")
    @ResponseBody
    public String transmit(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "转发失败，请选择文件!";
        }
        String fileName = file.getOriginalFilename();
        LOGGER.info("fileName:"+fileName);

        String filePath = "C:/Users/m1882/Documents/upload2/";
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            LOGGER.info("上传成功");
            return "上传成功!!!";
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
        }
        return "上传失败！";
    }

}
