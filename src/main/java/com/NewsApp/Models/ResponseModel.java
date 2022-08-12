package com.NewsApp.Models;

import org.apache.logging.log4j.message.Message;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ResponseModel {
   private String code;
   private String message;
   private List<Object> json;


   public ResponseModel(String code, List<Object> json) {
       this.code = code;
       this.json = json;
   }

    public ResponseModel(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public List<Object> getJson() {
        return json;
    }

    public void setJson(List<Object> json) {
        this.json = json;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
