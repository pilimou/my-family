package com.example.demo.web.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.stereotype.Service;

import com.example.demo.line.entity.FridgeEntity;
import com.example.demo.line.repository.FridgeRepository;
import com.example.demo.web.service.FridgeWebService;
import com.example.demo.web.vo.FridgeItemNameVO;
import com.example.demo.web.vo.in.DeleteFridgeItemListIn;
import com.example.demo.web.vo.in.QueryFridgeListIn;
import com.example.demo.web.vo.out.QueryFridgeListOut;
import com.example.demo.web.vo.out.QueryItemNameListOut;

@Service
public class FridgeWebServiceImpl implements FridgeWebService{
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private FridgeRepository fridgeRepository;
	
	//select
	@Override
	public QueryFridgeListOut queryFridgeList(QueryFridgeListIn queryFridgeListIn) {
		List<FridgeEntity> queryFridgeList = new ArrayList<>();
		QueryFridgeListOut out = new QueryFridgeListOut();
		if(StringUtils.equals("all", queryFridgeListIn.getItemName())) {
			queryFridgeList = fridgeRepository.findAll();
		} else {
			queryFridgeList = fridgeRepository.findByItemName(queryFridgeListIn.getItemName());	
		}
		out.setQueryFridgeVOList(queryFridgeList);
		return out;
	}
	
	//groud by itemName
	@Override
	public QueryItemNameListOut queryItemList() {
		QueryItemNameListOut out = new QueryItemNameListOut();
		TypedAggregation<FridgeEntity> fridgeEntityAggregation = 
			Aggregation.newAggregation(FridgeEntity.class, Aggregation.group("itemName").first("itemName").as("itemName"));
		
		AggregationResults<FridgeItemNameVO> results = mongoTemplate.
	             aggregate(fridgeEntityAggregation, FridgeItemNameVO.class);
		
		List<FridgeItemNameVO> resultsList = results.getMappedResults();
		out.setQueryItemNameListOut(resultsList);
		return out;
	}
	
	//delete by ids
	@Override
	public String deleteItemList(DeleteFridgeItemListIn deleteFridgeItemListIn) {
		List<String> idList = new ArrayList<>();
		String process = "f";
		if(null != deleteFridgeItemListIn && null != deleteFridgeItemListIn.getId()) {
			if(deleteFridgeItemListIn.getId().contains(",")) {
				idList = new ArrayList<String>(Arrays.asList(deleteFridgeItemListIn.getId().split(",")));
			} else {
				idList.add(deleteFridgeItemListIn.getId());
			}
			for(String id : idList) {				
				fridgeRepository.deleteFridgeEntityById(id);
			}
		}
		process = "s";
		return process;
	}

}
