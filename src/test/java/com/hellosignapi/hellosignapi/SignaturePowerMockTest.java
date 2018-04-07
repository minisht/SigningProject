package com.hellosignapi.hellosignapi;

import com.hellosign.sdk.HelloSignClient;
import com.hellosign.sdk.resource.SignatureRequest;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

/**
 * Created by minishtera on 4/7/18.
 */

@RunWith(PowerMockRunner.class)
public class SignaturePowerMockTest {



    @Test
    public void sendSignatureTest() throws Exception{
        SignaturePowerMock obj = PowerMockito.mock(SignaturePowerMock.class);

        PowerMockito.when(SignaturePowerMock.returnJSON())
                .thenReturn(FileUtils.readFileToString(new File("src/test/resources/data.json")));

        HelloSignClient client = PowerMockito.mock(HelloSignClient.class);
        PowerMockito.whenNew(HelloSignClient.class).withAnyArguments().thenReturn(client);

        PowerMockito.when(client.sendSignatureRequest(Mockito.any(SignatureRequest.class))).thenReturn(Mockito.any());
        SignaturePowerMock.sendSignature("Andy", "andy.hyderabad@mailinator.com", "nda.pdf");
    }

}
