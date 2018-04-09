package com.hellosignapi.hellosignapi;

import com.hellosign.sdk.HelloSignClient;
import com.hellosign.sdk.resource.SignatureRequest;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.web.client.RestTemplate;

import java.io.File;

/**
 * Created by minishtera on 4/7/18.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(SignaturePowerMock.class)
public class SignaturePowerMockTest {

    @Test
    public void returnJSONTest() throws Exception{
        RestTemplate template = PowerMockito.mock(RestTemplate.class);
        PowerMockito.whenNew(RestTemplate.class).withAnyArguments().thenReturn(template);

        Mockito.when(template.getForObject(Mockito.anyString(), Mockito.any())).thenReturn("someValue");
        String returnVal = SignaturePowerMock.returnJSON();
        Assert.assertEquals(returnVal, "someValue");
    }

    @Test
    public void sendSignatureTest() throws Exception{

        PowerMockito.mockStatic(SignaturePowerMock.class);

        Mockito.when(SignaturePowerMock.returnJSON())
                .thenReturn(FileUtils.readFileToString(new File("src/test/resources/data.json")));

        HelloSignClient client = PowerMockito.mock(HelloSignClient.class);
        PowerMockito.whenNew(HelloSignClient.class).withAnyArguments().thenReturn(client);
        Mockito.when(client.sendSignatureRequest(Mockito.any(SignatureRequest.class)))
                .thenReturn(new SignatureRequest());


        SignaturePowerMock.sendSignature("Andy", "andy.hyderabad@mailinator.com", "nda.pdf");

    }

}
