package com.jjf.collection;

import com.jjf.resource.HashMap;
import java.util.Collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 2017年2月17日18:30:22
 * @author jjf_lenovo
 *
 */
public class HashMapTest {
	public static void main(String args[]){
		HashMap<String, String> hashMap = new HashMap<String, String>();
//		HashMap map = (HashMap)hashMap.clone();
//		Map<String, String> hashMap = Collections.synchronizedMap(hashMap);//new HashMap<String, String>();
		while(true){
//			Map<String, String> map = new HashMap<String, String>();
			hashMap.put("hash", "三星");
			new putOne(hashMap).start();;
		}
	}
}

class putOne extends Thread{
	Map<String, String> map = null;
	public putOne(Map<String, String> obj){
		map = obj ;
	}
	@Override
	public void run(){
		Long time = System.currentTimeMillis();
		map.put(String.valueOf(time), "1");
		String ss = map.get("hash");
		if(ss==null){
			System.out.println("不安全的HashMap:"+time);
		}
	}
}

