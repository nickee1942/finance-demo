package com.nick.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class RestUtil {
    static Logger log = LoggerFactory.getLogger(RestUtil.class);

    public static <T> T postJSON(RestTemplate restTemplate, String url, Object param, Class<T> responseType) {
        HttpEntity<String> formEntity = makePostJSONEntiry(param);
        T result = restTemplate.postForObject(url, formEntity, responseType);
        log.info("rest-post-json response message:{}", JsonUtil.toJson(result));
        return result;
    }

    public static HttpEntity<String> makePostJSONEntiry(Object param) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> formEntity = new HttpEntity<String>(
                JsonUtil.toJson(param), headers);
        log.info("rest-post-json-request-param:{}", formEntity.toString());
        return formEntity;
    }


    public static HttpEntity<String> makePostTextEntiry(Map<String, ? extends Object> param) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> formEntity = new HttpEntity<String>(
                makeGetParamContent(param), headers);
        log.info("rest-post-text-request-param:{}", formEntity.toString());
        return formEntity;
    }


    public static String makeGetParamContent(Map<String, ? extends Object> param, String... excluedes) {
        StringBuilder content = new StringBuilder();
        List<String> excludeKeys = Arrays.asList(excluedes);
        param.forEach((key, v) -> {
            content.append(key).append("=").append(v).append("&");
        });
        if (content.length() > 0) {
            content.deleteCharAt(content.length() - 1);
        }
        return content.toString();
    }
}
