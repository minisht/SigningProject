package com.hellosignapi.hellosignapi;

import org.springframework.web.client.RestTemplate;

/**
 * Created by minishtera on 4/8/18.
 */
public class RestTemplateUtil {


    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
