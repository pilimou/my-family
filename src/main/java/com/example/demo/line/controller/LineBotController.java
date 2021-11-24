package com.example.demo.line.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.example.demo.line.service.VerificationLineService;
import com.example.demo.line.service.nike.LineNikeMessageService;
import com.example.demo.line.tasks.ProcessExpirationService;
import com.example.demo.line.vo.in.LineMessageIn;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/line")
public class LineBotController {
	
	@Value("${lineBot.nike.channelSecret}")
	private String channelSecret;
	
	@Value("${lineBot.nike.channelToken}")
	private String channelToken;
	
//	@Value("${lineBot.test.channelSecret}")
//	private String channelSecret;
//	
//	@Value("${lineBot.test.channelToken}")
//	private String channelToken;
	
	@Autowired
	private VerificationLineService verificationLineService;
	
	@Autowired
	private LineNikeMessageService lineNikeMessageService;
	
	@Autowired
	private ProcessExpirationService processExpirationService; 
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/receiveEvent")
	public ResponseEntity<String> receiveEvent(@RequestBody String requestBody,
			@RequestHeader("X-Line-Signature") String line_headers)
			throws RestClientException, JsonProcessingException {
		logger.info(requestBody);
		
		/**
		 *text
		 *
		{"destination":"U1d49ac7eee92d3818b492876623de152",
			"events":[
				{"type":"message",
				 "message":{"type":"text","id":"14800812590655","text":"123"},
				 "timestamp":1632476608449,
				 "source":{"type":"user","userId":"Uaae19ff20461f35d3db44135adfe2e56"},
				 "replyToken":"f387d247c00f4bf7bbe8e672c5f54325",
				 "mode":"active"}
			]
		}
		 
		 */
		
		/**
		 * image
		 * 
		 {"destination":"U1d49ac7eee92d3818b492876623de152",
		 	"events":[
		 		{"type":"message",
		 		"message":{"type":"image","id":"14800837669640","contentProvider":{"type":"line"}},
		 		"timestamp":1632476906984,
		 		"source":{"type":"user","userId":"Uaae19ff20461f35d3db44135adfe2e56"},
		 		"replyToken":"e3623aadcbf24f4b9d456baca2dd714e",
		 		"mode":"active"}
		 	]
		 }
		 */
		
		/**
		 * postback
		 * 
		 {"destination":"U1d49ac7eee92d3818b492876623de152",
			"events":[
				{"type":"postback",
				"postback":{
					"data":"storeId=12345",
					"params":{"datetime":"2021-10-27T12:22"}
				},
				"timestamp":1635827018443,
				"source":{
					"type":"user",
					"userId":"Uaae19ff20461f35d3db44135adfe2e56"
				},
				"replyToken":"f3ed04f7898f4039a6af2529bab33e8f",
				"mode":"active"}
			]
		 }
		 */
		
		
		if(!verificationLineService.checkFromLine(requestBody, line_headers, channelSecret)) {
			logger.error("checkFromLine Error");
			return new ResponseEntity<String>(HttpStatus.OK);
		} else {
			LineMessageIn lineMessageIn = new ObjectMapper().readValue(requestBody, LineMessageIn.class);
			try {	
				lineNikeMessageService.processLineMessage(lineMessageIn, channelToken);
				
			} catch(Exception e) {
				logger.error("processLineMessage Error ", e.getMessage());
			}
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	//定時任務
	@Scheduled(cron = "0 0 12 * * ?")
	public void pushExpirationMessage() {
		try {
			processExpirationService.pushExpirationMessage();
		} catch(Exception e) {
			logger.error("pushExpirationMessage Error " , e.getMessage());
		}
	}

}
