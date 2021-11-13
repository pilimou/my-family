package com.example.demo.web.vo.out;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.line.entity.FridgeEntity;
import com.example.demo.web.vo.FridgeItemNameVO;

public class QueryFridgeListOut {
	
	private List<FridgeEntity> queryFridgeVOList = new ArrayList<>();

	public List<FridgeEntity> getQueryFridgeVOList() {
		return queryFridgeVOList;
	}

	public void setQueryFridgeVOList(List<FridgeEntity> queryFridgeVOList) {
		this.queryFridgeVOList = queryFridgeVOList;
	}
	
	
}
