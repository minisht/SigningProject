package com.hellosignapi.hellosignapi;

import com.google.gson.JsonObject;
import com.hellosign.sdk.HelloSignClient;
import com.hellosign.sdk.resource.SignatureRequest;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
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

    @Mock
    HelloSignClient client;

//    HelloSignClient client = new HelloSignClient("somekey");

    JsonObject jsonObject = new JsonObject();

    @Mock
    SignatureRequest signatureRequest;

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
        Mockito.when(client.sendSignatureRequest(Mockito.any(SignatureRequest.class))).thenReturn(signatureRequest);
        signatureNrml.sendSignature("abc", "abc@xyz.com", "asdf");
        Assert.assertNotNull(jsonObject);
    }

}
