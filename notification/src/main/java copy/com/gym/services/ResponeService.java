package com.gym.services;

import java.util.HashMap;

import org.hibernate.mapping.List;
import org.springframework.stereotype.Service;
@Service
public class ResponeService {
     
     public HashMap<String, Object> ServiceObject(Boolean Status, String Message, Object Data){
        HashMap<String, Object> finalMap = new HashMap<>();
        finalMap.put("status", Status);
        finalMap.put("message", Message);
        finalMap.put("data", Data);
        return finalMap;
    }
    public HashMap<String, Object> GetServiceList(Boolean Status, String Message, List Data,Long Total){
        HashMap<String, Object> finalMap = new HashMap<>();
        finalMap.put("status", Status);
        finalMap.put("message", Message);
        finalMap.put("data", Data);
        finalMap.put("totalData", Total);
        return finalMap;
    }
}
