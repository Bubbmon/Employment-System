package com.system.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class DownloadUtil{
    public static String download(String fileName,String path, HttpServletResponse response){
        String message = null;
        response.setHeader("content-type","application/octet-stream");
        response.setContentType("application/octet-stream");
        try {
            /*告知浏览器以何种方式显示响应返回的文件，attachment附件下载，inline在线打开
             * 网络传输只支持URL编码，需要将文件名URL编码后传输*/
            response.setHeader("Content-Disposition","attachment;fileName="+java.net.URLEncoder.encode(fileName,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //实现文件下载
        File file = new File(path);
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try{
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while(i != -1){
                os.write(buffer,0,i);
                i = bis.read(buffer);
            }
        }catch(Exception e){
            message = "Failed to download";
            e.printStackTrace();
        }finally {
            if(bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fis != null){
                try{
                    fis.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        message = "Succeeded in downloading";
        return message;
    }
}
