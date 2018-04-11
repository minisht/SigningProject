package com.hellosignapi.hellosignapi;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hellosign.sdk.HelloSignClient;
import com.hellosign.sdk.resource.SignatureRequest;

import java.io.File;

/**
 * Created by minishtera on 4/8/18.
 */
public class SignatureNormal {
    public static final String url = "https://api.github.com/orgs/HelloFax/repos";
    RestTemplateUtil template = null;
    HelloSignClient client = null;
    JsonObject jsonObject = null;

    public SignatureNormal(RestTemplateUtil template, HelloSignClient client, JsonObject jsonObject){
        this.template = template;
        this.client = client;
        this.jsonObject = jsonObject;
    }

    public static void main(String[] args) throws Exception{
        SignatureNormal normal = new SignatureNormal(new RestTemplateUtil(), new HelloSignClient("7a51006e81f1087e3240d029d569c15857a0d39af4ff5bfbf3b21196fc59357a"), new JsonObject());
        normal.sendSignature("Andy", "andy.hyderabad@mailinator.com", "nda.pdf");
    }

    public void sendSignature(String name, String emailAddress, String filePath) throws Exception{
        String json = template.getRestTemplate().getForObject(url, String.class);

        JsonParser jsonParser = new JsonParser();
        JsonArray array = jsonParser.parse(json).getAsJsonArray();
        int max = 0;


        for(int i=0; i<array.size(); i++) {
            if(array.get(i).getAsJsonObject().get("html_url").getAsString().endsWith("-sdk")){
                int current = array.get(i).getAsJsonObject().get("open_issues_count").getAsInt();
                if(current > max) {
                    max = current;
                    jsonObject = array.get(i).getAsJsonObject();
                }
            }
        }

       signRequest(jsonObject, client, name, emailAddress, filePath);
    }

    public void signRequest(JsonObject jsonObject, HelloSignClient client, String name, String emailAddress, String filePath) throws Exception{


        SignatureRequest request = new SignatureRequest();
        request.setTitle(jsonObject.get("name").getAsString());
        request.setMessage(jsonObject.get("description").getAsString());
        request.addSigner(emailAddress, name);
        request.setTestMode(true);
        request.addFile(new File(filePath));

        SignatureRequest response = client.sendSignatureRequest(request);
        System.out.println(response.toString());
    }
}
