/**
 * 
 */
package com.example.demo.algorithm.ztl.contentbasedrecommend;

import java.util.Comparator;
import java.util.Map.Entry;


class MapValueComparator implements Comparator<Entry<Long, Double>> {

	@Override
	public int compare(Entry<Long, Double> me1, Entry<Long, Double> me2) {

		return me1.getValue().compareTo(me2.getValue());
	}
}