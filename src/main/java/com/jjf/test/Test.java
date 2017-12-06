package com.jjf.test;

import org.junit.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 模拟Ca多线程下的解密方式
 * 
 * @author jjf_lenovo
 * 
 */
public class Test{

  @org.junit.Test
  public void testParse(){
    System.out.println(Long.parseLong(null));
  }

  @org.junit.Test
  public void testEquals(){
//    Assert.assertTrue("123".equals(123));
    Assert.assertTrue(Long.valueOf(123L).equals("123"));
  }

  @org.junit.Test
    public void testSpilt(){
      String content = "./39801930\n"
                       + "./39782236\n"
                       + "./39866492\n"
                       + "./39806903\n"
                       + "./39770145\n"
                       + "./39770101\n"
                       + "./39764234\n"
                       + "./49863480\n"
                       + "./39775043\n"
                       + "./39775107\n"
                       + "./39775216\n"
                       + "./45236363\n"
                       + "./39764053\n"
                       + "./38557733\n"
                       + "./46850045\n"
                       + "./39770242\n"
                       + "./46844569\n"
                       + "./39765127\n"
                       + "./39770143\n"
                       + "./39765243\n"
                       + "./39770146\n"
                       + "./44487722\n"
                       + "./39765143\n"
                       + "./46837724\n"
                       + "./39785881\n"
                       + "./39777981\n"
                       + "./38481608\n"
                       + "./39775189\n"
                       + "./39764208\n"
                       + "./39153158\n"
                       + "./39790509\n"
                       + "./39775279\n"
                       + "./46844796\n"
                       + "./39782963\n"
                       + "./65072186\n"
                       + "./46846106\n"
                       + "./39785263\n"
                       + "./39765215\n"
                       + "./39764186\n"
                       + "./46852451\n"
                       + "./39775214\n"
                       + "./39775258\n"
                       + "./61955039\n"
                       + "./2088821500125550\n"
                       + "./39764217\n"
                       + "./39775228\n"
                       + "./39765111\n"
                       + "./39765232\n"
                       + "./39765031\n"
                       + "./46855040\n"
                       + "./39764147\n"
                       + "./49870417\n"
                       + "./39821428\n"
                       + "./39775148\n"
                       + "./39805954\n"
                       + "./38432727\n"
                       + "./61893277\n"
                       + "./46848318\n"
                       + "./38553181\n"
                       + "./39775336\n"
                       + "./46844993\n"
                       + "./39770338\n"
                       + "./39777831\n"
                       + "./39770266\n"
                       + "./45280637\n"
                       + "./46846035\n"
                       + "./39765227\n"
                       + "./39775221\n"
                       + "./48812331\n"
                       + "./44471813\n"
                       + "./39810643";
      String[] kdtids = content.replace("./","")
        .split("\n");
    System.out.println(kdtids.length);
    List<String[]> data =  FileUtil.readCsvFile("/Users/jijianfeng/baseData.csv");
    List<String[]> list = data.parallelStream()
        .filter(ss->contains(kdtids,ss[2])).collect(Collectors.toList());
    list.forEach(s->{
      System.out.println(s[2]+s[3]);
    });
  }

  private boolean contains(String[] datas,String obj){
    for(String data :datas){
      if(data.equals(obj)){
        return true;
      }
    }
    return false;
  }

  @org.junit.Test
  public void testCSVFile(){
    List<String[]> data =  FileUtil.readCsvFile("/Users/jijianfeng/baseData.csv");
    int count = 0;
    for(String[] ss :data){
      for(int i=0;i<ss.length;i++) {
        if ((ss[i].contains("kdt-private") ||
          ss[i].contains("kdt-img.qbox.me") ||
          ss[i].contains("qiniucdn.com") ||
          ss[i].contains("img.yzcdn.cn") ||
          ss[i].contains("imgqn.koudaitong.com")
        )) {
            count++;
            break;
        }
      }
    }
    System.out.println("count:"+count);
  }

  @org.junit.Test
  public void tes(){
    String ss="\"{\"\"license\"\":\"\"kdt-private-2015-12-23-Ftf-NgBK0bF5ebaY_dhLQgKb0sXK\"\",\"\"identity_front\"\":\"\"kdt-private-2015-12-23-Fqditmhb5kyE8wEJMYpBOxM4PRLq\"\",\"\"identity_back\"\":\"\"kdt-private-2015-12-23-FhEsIcub9s24-fbvBgatAaiSlXzm\"\"}\"";
    System.out.println(ss.replace("\"{","{")
    .replace("}\"","}").replace("\"\"","\""));
  }
}
