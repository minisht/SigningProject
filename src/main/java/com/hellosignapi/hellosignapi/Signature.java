package com.hellosignapi.hellosignapi;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hellosign.sdk.HelloSignClient;
import com.hellosign.sdk.resource.SignatureRequest;
import org.springframework.web.client.RestTemplate;

import java.io.File;

/**
 * Created by minishtera on 3/28/18.
 */
public class Signature {
    public static void main(String[] args) throws Exception {
        sendSignature("Andy", "andy.hyderabad@mailinator.com", "nda.pdf");
    }

    public static void sendSignature(String name, String emailAddress, String filePath) throws Exception{
        String url = "https://api.github.com/orgs/HelloFax/repos";
        RestTemplate restTemplate = new RestTemplate();
        String json = restTemplate.getForObject(url, String.class);

        JsonParser jsonParser = new JsonParser();
        JsonArray array = jsonParser.parse(json).getAsJsonArray();
        int max = 0;

        JsonObject jsonObject = new JsonObject();
        for(int i=0; i<array.size(); i++) {
            if(array.get(i).getAsJsonObject().get("html_url").getAsString().endsWith("-sdk")){
                int current = array.get(i).getAsJsonObject().get("open_issues_count").getAsInt();
                if(current > max) {
                    max = current;
                    jsonObject = array.get(i).getAsJsonObject();
                }
            }
        }
//        System.out.println(jsonObject);
        HelloSignClient client = new HelloSignClient("7a51006e81f1087e3240d029d569c15857a0d39af4ff5bfbf3b21196fc59357a");

        SignatureRequest request = new SignatureRequest();
        request.setTitle(jsonObject.get("name").getAsString());
        request.setMessage(jsonObject.get("description").getAsString());
        request.addSigner(emailAddress, name);
        request.setTestMode(true);
        request.addFile(new File(filePath));

        SignatureRequest response = client.sendSignatureRequest(request);
        System.out.println(response.toString());


    }

//    public static String executeJsonPath(String json, String jsonPath) {
//        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
//        return JsonPath.read(document, jsonPath).toString();
//
//    }
}
