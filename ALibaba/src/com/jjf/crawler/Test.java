package com.jjf.crawler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {
	public static void main(String args[]) throws Exception{
		downloadFromRosi();
	}
	
	public static void downloadFromRosi() throws Exception{ 
		String prixyIp = "112.227.50.183";
		Integer proxyPort = 9999;
		String target = "http://www.77tuba.com/";//网址
		String type = "1009/";//类别
		String listType = "list_1296_";//页面列表
		int listSize =11;//首页页数
		int girlsCount = 0;
		//先获取首页信息，放置坏链接
		for(int listS = 1;listS<=listSize;listS++){
			HttpClient client = HttpConnectionManager.getHttpClient();
			String htmlAll = HttpClientRequestHandler.doGet(client,target+type+listType+listS+".shtml","gb2312").html();
			Document docAll = Jsoup.parse(htmlAll);
			//获取改类别写真总数
			Elements tableHtmlAll = docAll.getElementsByClass("lm").get(0).getElementsByTag("table").get(0).getElementsByTag("a");//..attr("href").replace("/", "");
			List<String> listAll = new ArrayList();
			for(int i = 0;i<tableHtmlAll.size();i=i+2){//有重复两次地址，图片和文字
				Element element= tableHtmlAll.get(i);
				listAll.add(element.attr("href").substring(element.attr("href").indexOf("/")+1,element.attr("href").length()));
			}
//			System.out.println(listAll.size());
//			System.out.println(listAll.toString());
			for (int i = 0; i <listAll.size(); i++) {
				try {
					girlsCount++;
					String html = HttpClientRequestHandler.doGet(client,target+listAll.get(i),"gb2312").html();
					Document doc = Jsoup.parse(html);
					String sizeHtml = doc.getElementsByClass("image").html();
					Element titleHtml = doc.getElementsByTag("H2").get(0);
					//标题和页数
					String title = titleHtml.toString().substring(titleHtml.toString().indexOf(" "), titleHtml.toString().lastIndexOf("</"));
					String size = sizeHtml.substring(sizeHtml.indexOf("共")+1,sizeHtml.indexOf("页"));
					//第一页可以开始了
					new Thread(new RosiHtmlParser(listS,girlsCount,title,html,1)).start();
					Thread.sleep(200);
					//获取页数，继续遍历
					System.out.println(title+"共"+size+"页");
					for(int j = 2;j<=Integer.valueOf(size);j++){
						String htmlSmall = HttpClientRequestHandler.doGet(client,target+"/"+listAll.get(i).replace(".shtml", "")+"_"+j+".shtml","gb2312").html();
						new Thread(new RosiHtmlParser(listS,girlsCount,title,htmlSmall,j)).start();
						Thread.sleep(200);
					}
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
}


class RosiHtmlParser implements Runnable {
	private String html;
	private int page;
	private String title;
	private int listS;
	private int girlsCount;
	public RosiHtmlParser(int listS,int girlsCount,String title,String html,int page) {
		this.html = html;
		this.page = page;
		this.title = title;
		this.listS = listS;
		this.girlsCount = girlsCount;
	}
	@Override
	public void run() {
		System.out.println("==========第"+page+"页============");
		List<String> list = new ArrayList<String>();
		Document doc = Jsoup.parse(html);
		Elements files = doc.getElementsByClass("file");
		Elements imgs = files.get(0).getElementsByTag("img");
		for(Element img : imgs) {
//			System.out.println(img.attr("src").toString());
			String url = img.attr("src").toString();//upimgs/allimg/170220/11623535361.jpg
			if(url.indexOf("http")==-1){
				url = "http://www.77tuba.com/"+url;
				list.add(url);
			}
			else{
				list.add(img.attr("src").toString());
			}
		}
		for(String imageUrl : list){
			new Thread(new RosiImageCreator(listS,girlsCount,imageUrl,title,page)).start();
		}
	}
}

class RosiImageCreator implements Runnable {
	private static int count = 0;
	private String imageUrl;
	private String title;
	private int page;
	private int listS;
	private int girlsCount;
	 //存储路径，自定义
	private static final String basePath = "E:/rosi/3"; 
	public RosiImageCreator(int listS,int girlsCount,String imageUrl,String title,int page) {
//		System.out.println(imageUrl);
		this.imageUrl = imageUrl;//http://img.77tuba.com/upimgs/allimg/160830/11615291495.jpg
		this.page = page;
		this.title = title;
		this.listS = listS;
		this.girlsCount = girlsCount;
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
//			String path = (basePath+"/"+title+"/"+page+"--"+imageName).replace(" ", "");
//			System.out.println(path);
//			File file = new File(path);
			File file = new File( basePath+"/"+listS+"-"+girlsCount+"-"+page+"-"+title+"-"+imageName);
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
//			System.out.println("第"+(count++)+"张妹子:"+file.getAbsolutePath());
			is.close(); 
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}