package com.boxinghub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String projectRoot = System.getProperty("user.dir");

        File subFolder = new File(projectRoot, "boxinghub");
        if (subFolder.exists() && subFolder.isDirectory()) {
            projectRoot = subFolder.getAbsolutePath();
        }

        String uploadPath = projectRoot + File.separator + "src" + File.separator + "main" +
                File.separator + "resources" + File.separator + "static" +
                File.separator + "uploads" + File.separator;

        System.out.println("--- WEB CONFIG PATH: " + uploadPath);

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///" + uploadPath.replace("\\", "/"));
    }
}