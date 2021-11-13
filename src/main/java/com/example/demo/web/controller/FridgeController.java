package com.example.demo.web.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.web.service.FridgeWebService;
import com.example.demo.web.vo.in.DeleteFridgeItemListIn;
import com.example.demo.web.vo.in.QueryFridgeListIn;
import com.example.demo.web.vo.out.QueryFridgeListOut;
import com.example.demo.web.vo.out.QueryItemNameListOut;

@Controller
@RequestMapping(value = "/fridge")
public class FridgeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FridgeController.class);
	
	@Autowired
	private FridgeWebService fridgeWebService;
	
	
	@RequestMapping("/family_fridge")
	public String family_fridge() {
		return "fridge/family_fridge";
	}
	
	/**
	 * 物品清單
	 * @param queryFridgeListIn
	 * @return
	 */
	@RequestMapping(value = "/queryItemList", method = RequestMethod.POST)
	@ResponseBody
	public QueryItemNameListOut  queryItemList() {
		QueryItemNameListOut out = fridgeWebService.queryItemList();
		return out;
	}
	
	/**
	 * 查詢冰箱
	 * @param queryFridgeListIn
	 * @return
	 */
	@RequestMapping(value = "/queryFridgeListIn", method = RequestMethod.POST)
	@ResponseBody
	public QueryFridgeListOut  queryFridgeListIn(@RequestBody final QueryFridgeListIn queryFridgeListIn) {
		QueryFridgeListOut out = new QueryFridgeListOut();
		try {
			out = fridgeWebService.queryFridgeList(queryFridgeListIn);
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
		}	
		return out;
	}
	
	/**
	 * 刪除冰箱物品
	 * @param deleteFridgeItemListIn
	 * @return
	 */
	@RequestMapping(value = "/deleteFridgeItemListIn", method = RequestMethod.POST)
	@ResponseBody
	public String queryFridgeListIn(@RequestBody final DeleteFridgeItemListIn deleteFridgeItemListIn, HttpSession session) {
		String process = "f";
		if(!StringUtils.equals("4", (String)session.getAttribute("authority"))) {
			return process;
		}
		try {
			process = fridgeWebService.deleteItemList(deleteFridgeItemListIn);
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
		}	
		return process;
	}

}
