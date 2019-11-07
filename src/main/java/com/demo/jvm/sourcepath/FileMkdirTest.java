package com.demo.jvm.sourcepath;


import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class FileMkdirTest {

    public static void main(String[] args) throws Exception {
        String src = "cert/alipayRootCert.crt";
        System.out.println(getAbsolutePath(src));
    }

    private static String getAbsolutePath(String sourcePath) throws Exception{
        String fileName = sourcePath.substring(sourcePath.indexOf("/") + 1);
        String configFilePath = null;
        if (isWindows()) {
            configFilePath = "C:/CacheConfig/blackTea/config/" + fileName;
        } else {
            configFilePath = "/usr/blackTea/config/" + fileName;
        }
        log.info("文件路径：path = {}", configFilePath);
        File certFile = new File(configFilePath);
        if(!certFile.exists()) {
            if(!certFile.getParentFile().exists()){
                try {
                    if(certFile.getParentFile().mkdirs()){
                        certFile.createNewFile();
                    }else {
                        log.error("目录：{}创建失败！", certFile.getParent());
                        return null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(sourcePath);
        OutputStream os = new FileOutputStream(certFile);
        try {
            byte[] temp = new byte[is.available()];
            is.read(temp);
            os.write(temp);
        } finally {
            is.close();
            os.close();
        }
        return configFilePath;
    }


    private static boolean isWindows(){
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")){
            return true;
        }
        return false;
    }
}
