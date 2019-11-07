package com.demo.jvm.sourcepath;

import sun.misc.Resource;

import java.io.*;

public class FileMkdirTest {

    private static String getAbsolutePath(String sourcePath) throws Exception{
        String configFilePath = null;
        if (EnvUtil.isWindows()) {
            configFilePath = "C:/Program Files/blackTea/config/" + sourcePath;
        } else {
            configFilePath = "/usr/blackTea/config/" + sourcePath;
        }
        File certFile = new File(configFilePath);
        if(!certFile.exists()) {
            if(!certFile.getParentFile().exists()){
                try {
                    File parentFile = new File(certFile.getParent() + "\\/");
                    parentFile.mkdirs();
                    certFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Resource certResource = new ClassPathResource(sourcePath);
            InputStream is = certResource.getInputStream();
            OutputStream os = new FileOutputStream(certFile);
            try {
                byte[] temp = new byte[is.available()];
                is.read(temp);
                os.write(temp);
            } finally {
                is.close();
                os.close();
            }
        }
        return configFilePath;
    }
}
