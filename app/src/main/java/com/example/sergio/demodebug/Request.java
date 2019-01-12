package com.example.sergio.demodebug;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private String uri;
    private String method="GET";
    private Map<String,String> params = new HashMap<>();

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void setParams(String key, String value) {
        this.params.put(key,value);
    }

    public String getEncodeParams(){
        StringBuilder stringBuilder = new StringBuilder();

        for (String key: params.keySet() ) {
            String value = null;
            try {
                // utf-8 para codificar la url
                value= URLEncoder.encode(params.get(key),"UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(stringBuilder.length()>0)
                stringBuilder.append("&");

            stringBuilder.append(key+"="+value);
        }

        return stringBuilder.toString();
    }
}
