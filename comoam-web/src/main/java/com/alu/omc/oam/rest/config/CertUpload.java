package com.alu.omc.oam.rest.config;

import java.io.File;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.alu.omc.oam.util.InstallCert;

@Controller
public class CertUpload {

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/upload/cert")
    public void upload(@RequestParam("file") MultipartFile file ) throws Exception {
        if (!file.isEmpty()) {
            file.transferTo(new File(InstallCert.CERTIFICATE_PATH));
        }
    }

}