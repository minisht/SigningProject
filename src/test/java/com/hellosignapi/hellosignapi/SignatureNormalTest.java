package com.hellosignapi.hellosignapi;

import com.google.gson.JsonObject;
import com.hellosign.sdk.HelloSignClient;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.io.File;

/**
 * Created by minishtera on 4/10/18.
 */
public class SignatureNormalTest {

    @Mock
    RestTemplateUtil template;

    HelloSignClient client = new HelloSignClient("somekey");

    JsonObject jsonObject = new JsonObject();

    @Mock
    RestTemplate restTemp;

    @InjectMocks
    SignatureNormal signatureNrml;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        signatureNrml = new SignatureNormal(template, client, jsonObject);
    }

    @Test
    public void sendSignatureTest() throws Exception{
        Mockito.when(template.getRestTemplate()).thenReturn(restTemp);
        Mockito.when(restTemp.getForObject(Mockito.anyString(), Mockito.any()))
                .thenReturn(FileUtils.readFileToString(new File("src/test/resources/data.json")));
        Mockito.doNothing().when(signatureNrml).signRequest(jsonObject, client, "abc", "abc@xyz.com", "asdf");
        signatureNrml.sendSignature("abc", "abc@xyz.com", "asdf");

    }

}
