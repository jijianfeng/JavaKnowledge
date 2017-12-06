package com.jjf.test;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

;

/**
 * Created by jijianfeng on 2017/11/7.
 */
public class FileUtil {

  //连接超时时间
  public final static int CONNECT_TIMEOUT = 5000;

  //读取超时时间
  public final static int SOCKET_TIMEOUT = 5000;

  private static String PROXY_GATEWAY = "http://proxy-static.s.qima-inc.com";

  public static void downloadImageByUrl(String imageUrl, String filePath) {
    try {
      mkdir(filePath);
      File file = new File(filePath);
      OutputStream os = new FileOutputStream(file);
      //创建一个url对象
      URL url = new URL(imageUrl);
      InputStream is = url.openStream();
      byte[] buff = new byte[1024];
      while (true) {
        int readed = is.read(buff);
        if (readed == -1) {
          break;
        }
        byte[] temp = new byte[readed];
        System.arraycopy(buff, 0, temp, 0, readed);
        //写入文件
        os.write(temp);
      }
      is.close();
      os.close();
    } catch (Exception e) {

    }
  }

  /**
   * httpclient 走代理
   */
  public static void download(String url, String savePath) {
    mkdir(savePath);

    //处理代理
    int protocolEnd = url.indexOf("://");
    String protocol = url.substring(0, protocolEnd);
    String remainUrl = url.substring(protocolEnd + 3);
    if (!remainUrl.contains("/")) {
      remainUrl = remainUrl + "/";
    }
    int hostEnd = remainUrl.indexOf("/");
    String host = remainUrl.substring(0, hostEnd);

    String proxyUrl = PROXY_GATEWAY + remainUrl.substring(hostEnd);//代理地址

    RequestConfig requestConfig = RequestConfig.custom()
        .setSocketTimeout(SOCKET_TIMEOUT) //设置socket超时
        .setConnectTimeout(CONNECT_TIMEOUT) //设置connect超时
        .build();
    HttpClient httpclient = HttpClients.custom()
        .setDefaultRequestConfig(requestConfig)
        .build();
    File storeFile = null;
    try {
      HttpGet httpget = new HttpGet(proxyUrl);
      httpget.setHeader("host", host); //设置host地址
      HttpResponse response = httpclient.execute(httpget);
      storeFile = new File(savePath);
      FileOutputStream output = new FileOutputStream(storeFile);

      // 得到网络资源的字节数组,并写入文件
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        InputStream instream = entity.getContent();
        try {
          byte b[] = new byte[1024];
          int j = 0;
          while ((j = instream.read(b)) != -1) {
            output.write(b, 0, j);
          }
          output.flush();
          output.close();
        } finally {
          // Closing the input stream will trigger connection release
          try {
            instream.close();
          } catch (Exception ignore) {
          }
        }

      }

    } catch (Exception e) {

    } finally {
      httpclient.getConnectionManager().shutdown();
    }
  }

  /**
   * 创建文件路径
   */
  public static void mkdir(String absPathName) {
    File file = new File(absPathName.substring(0, absPathName.lastIndexOf("/")));
    if (!file.exists()) {
      file.mkdirs();
    }
  }

  public static List<String[]> readCsvFile(String filePath) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(filePath));//换成你的文件名
      reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
      String line = null;
      List<String[]> baseData = new ArrayList<>();
      while ((line = reader.readLine()) != null) {
        String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分

        baseData.add(item);
      }
      return baseData;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void main(String[] args){
    long kdtId = 123;
    FileUtil.mkdir("/data/project/pay-crawler/provision/unCertKdtID/" + kdtId + "/");
  }

  /**
   * 导出
   *
   * @param filePath csv文件(路径+文件名)，csv文件不存在会自动创建
   * @param dataList 数据
   * @return
   */
  public static boolean exportCsv(String filePath, List<String> dataList) throws IOException {
    boolean isSucess=false;

    FileOutputStream out=null;
    OutputStreamWriter osw=null;
    BufferedWriter bw=null;
    try {
      out = new FileOutputStream(new File(filePath));
      osw = new OutputStreamWriter(out);
      bw =new BufferedWriter(osw);
      if(dataList!=null && !dataList.isEmpty()){
        for(String data : dataList){
          bw.append(data).append("\r");
        }
      }
      isSucess=true;
    } catch (Exception e) {
      isSucess=false;
    }finally{
      if(bw!=null){
        bw.close();
        bw=null;
      }
      if(osw!=null){
        osw.close();
        osw=null;
      }
      if(out!=null){
        out.close();
        out=null;
      }
    }

    return isSucess;
  }

}
