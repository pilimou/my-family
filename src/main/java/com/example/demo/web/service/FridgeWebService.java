package com.example.demo.web.service;

import com.example.demo.web.vo.in.DeleteFridgeItemListIn;
import com.example.demo.web.vo.in.QueryFridgeListIn;
import com.example.demo.web.vo.out.QueryFridgeListOut;
import com.example.demo.web.vo.out.QueryItemNameListOut;

public interface FridgeWebService {

	QueryFridgeListOut queryFridgeList(QueryFridgeListIn queryFridgeListIn);
	QueryItemNameListOut queryItemList();
	String deleteItemList(DeleteFridgeItemListIn deleteFridgeItemListIn);
}
