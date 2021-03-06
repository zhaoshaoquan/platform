package com.stone.config.commons;

import java.util.Comparator;

import com.stone.config.HeadItem;

public class HeadComparator implements Comparator<HeadItem> {

	@Override
	public int compare(HeadItem o1, HeadItem o2) {
		Integer h1 = o1.getHeadOrder();
		Integer h2 = o2.getHeadOrder();
		if(h1==null){
			h1 = Integer.MAX_VALUE;
		}
		if(h2==null){
			h2 = Integer.MAX_VALUE;
		}
		return h1.compareTo(h2);
	}

}
