package com.example.demo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

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
	
	@PostMapping("/fileUpload")
	@ResponseBody
    public Object testVideo(@RequestParam("files") MultipartFile[] uploadfiles, @RequestParam("msg") String msg) {
		Gson gson = new Gson();
		try {
			saveUploadedFiles(Arrays.asList(uploadfiles));
			
			ResponseStatus r = new ResponseStatus("accept","", msg);
			return gson.toJson(r);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			ResponseStatus r = new ResponseStatus("error","file error or msg not found", "");
			return gson.toJson(r);
		}
		
    }
	
    private void saveUploadedFiles(List<MultipartFile> files) throws IOException {

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue; //繼續下一個檔案
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get("./data/" + file.getOriginalFilename());
            Files.write(path, bytes);

        }

    }
}
