/**
 * 
 */
package com.example.demo.algorithm.ztl.contentbasedrecommend;

import java.util.HashMap;
import java.util.Iterator;

public class CustomizedHashMap<K, V> extends HashMap<K,V>
{
	private static final long serialVersionUID = 1L;

	@Override
	public String toString(){
		String toString="{";
		Iterator<K> keyIte=this.keySet().iterator();
		while(keyIte.hasNext()){
			K key=keyIte.next();
			toString+="\""+key+"\":"+this.get(key)+",";
		}
		if(toString.equals("{")){
			toString="{}";
		}
		else{
			toString=toString.substring(0, toString.length()-1)+"}";
		}
		return toString;
		
	}

}

