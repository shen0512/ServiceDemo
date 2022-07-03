package com.example.demo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
public class TestREST {
	public class ResponseStatus{
		private String status;
		private String msg;
		private String msgFromClient;
		
		public ResponseStatus(String status, String msg, String msgFromClient) {
			this.status = status;
			this.msg = msg;
			this.msgFromClient = msgFromClient;
		}
	}
	
	@PostMapping("/test")
	@ResponseBody
    public Object getTest(@RequestBody Object requestBody) {
		Gson gson = new Gson();
		String requestBodyStr = gson.toJson(requestBody);
		
		JsonObject jsonObject = JsonParser.parseString(requestBodyStr).getAsJsonObject();
		try {
			String tmp = jsonObject.get("msg").getAsString();
			ResponseStatus r = new ResponseStatus("accept","", tmp);
			return gson.toJson(r);
		}catch(Exception e) {
			ResponseStatus r = new ResponseStatus("error","msg not found", "");
			return gson.toJson(r);
		}
		
    }
}
