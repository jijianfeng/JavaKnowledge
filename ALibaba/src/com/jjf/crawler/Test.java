package com.jjf.crawler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;

public class Test {
	public static void main(String args[]) throws Exception{
		String prixyIp = "112.227.50.183";
    	Integer proxyPort = 9999;
    	String target = "http://jandan.net/ooxx/page-";
//    	String html =  HttpClientRequestHandler.doGet(target,prixyIp,proxyPort).html();
//    	writeString(html,"C:/result.txt");
		int page = 1537;
		for (int i = page; i > 0; i--) {
			try {
//				Thread.sleep(5000);
				String html = HttpClientRequestHandler.doGet(target+i, prixyIp, proxyPort).html();
				new Thread(new JianDanHtmlParser(html, i)).start();
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

class JianDanHtmlParser implements Runnable {
	private String html;
	private int page;
	public JianDanHtmlParser(String html,int page) {
		this.html = html;
		this.page = page;
	}
	@Override
	public void run() {
		System.out.println("==========第"+page+"页============");
		List<String> list = new ArrayList<String>();
		html = html.substring(html.indexOf("commentlist"));
		String[] images = html.split("li>");
		for (String image : images) {
			String[] ss = image.split("br");
			for (String s : ss) {
				if (s.indexOf("<img src=") > 0) {
					try{
						int i = s.indexOf("<img src=\"") + "<img src=\"".length();
						list.add(s.substring(i, s.indexOf("\"", i + 1)).replace("//", "http://"));
					}catch (Exception e) {
						System.out.println(s);
					}
				}
			}
		}
		for(String imageUrl : list){
			if(imageUrl.indexOf("sina")>0){
				new Thread(new JianDanImageCreator(imageUrl,page)).start();
			}
		}
	}
}

class JianDanImageCreator implements Runnable {
	private static int count = 0;
	private String imageUrl;
	private int page;
	 //存储路径，自定义
	private static final String basePath = "E:/jiandan"; 
	public JianDanImageCreator(String imageUrl,int page) {
		System.out.println(imageUrl);
		this.imageUrl = imageUrl;//"http://ww3.sinaimg.cn/mw600/825b611djw1evni9n1l5oj20ia0rhgr1.jpg"
		this.page = page;
	}
	@Override
	public void run() {
		File dir = new File(basePath);
		if(!dir.exists()){
			dir.mkdirs();
			System.out.println("图片存放于"+basePath+"目录下");
		}
		String imageName = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
		try {
			File file = new File( basePath+"/"+page+"--"+imageName);
			OutputStream os = new FileOutputStream(file);
			//创建一个url对象
			URL url = new URL(imageUrl);
			InputStream is = url.openStream();
			byte[] buff = new byte[1024];
			while(true) {
				int readed = is.read(buff);
				if(readed == -1) {
					break;
				}
				byte[] temp = new byte[readed];
				System.arraycopy(buff, 0, temp, 0, readed);
				//写入文件
				os.write(temp);
			}
			System.out.println("第"+(count++)+"张妹子:"+file.getAbsolutePath());
			is.close(); 
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}