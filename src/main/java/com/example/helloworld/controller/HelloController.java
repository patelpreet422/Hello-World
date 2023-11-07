package com.example.helloworld.controller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController("/")
public class HelloController {

    @GetMapping("/tibco/home")
    public String hello(){
        CloseableHttpClient httpClient = HttpClients.createDefault();

        System.out.println("Request landing inside home");

        String aprUrl = "https://10.10.31.52:9755/rest/vmx/v3/accountInquiry";
        String responseString = "Default response";
        String body ="<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<VMX_ROOT>\n" +
                "\t<VMX_HEADER>\n" +
                "\t\t<MSGID>VMX.ACCT.INQ.EMEA</MSGID>\n" +
                "\t\t<VERSION>I8VCB</VERSION>\n" +
                "\t\t<CLIENTID>06146</CLIENTID>\n" +
                "\t\t<CORRELID>12345678901234567890</CORRELID>\n" +
                "\t\t<CONTEXT>00000KOTAKVMX</CONTEXT>\n" +
                "\t\t<NAME>00000KOTAKVMX</NAME>\n" +
                "\t</VMX_HEADER>\n" +
                "\t<VMX_MSGIN>\n" +
                "\t\t<CONTEXT>00000KOTAKVMX</CONTEXT>\n" +
                "\t\t<NAME>00000KOTAKVMX</NAME>\n" +
                "\t\t<ORG>406</ORG>\n" +
                "\t\t<ACCOUNT>4166441509514194</ACCOUNT>\n" +
                "\t\t<DUAL_IND></DUAL_IND>\n" +
                "\t\t<SCHEME></SCHEME>\n" +
                "\t\t<MOBILE_NBR></MOBILE_NBR>\n" +
                "\t\t<CC_MASKED></CC_MASKED>\n" +
                "\t\t<CRN></CRN>\n" +
                "\t</VMX_MSGIN>\n" +
                "</VMX_ROOT>";

        HttpPost httpPost = new HttpPost(aprUrl);
        try {
            StringEntity requestEntity = new StringEntity(body);
            System.out.println("enity build successfully");
            httpPost.setHeader("Content-Type","application/xml");
            httpPost.setHeader("MSGID","VMX.ACCT.INQ.EMEA");
            httpPost.setHeader("NAME","00000KOTAKVMX");
            httpPost.setHeader("CONTEXT","00000KOTAKVMX");
            httpPost.setHeader("CORRELID","12345678901234567890");
            httpPost.setHeader("CLIENTID","06146");
            httpPost.setHeader("VERSION","I8VCB");
            httpPost.setEntity(requestEntity);
            HttpResponse response = httpClient.execute(httpPost);
            System.out.println("after execute");
            HttpEntity entity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == 200) {
                responseString  = EntityUtils.toString(entity);
                System.out.printf("response from account enquiry %s: \n", responseString);
            } else{
                System.out.printf("Something went wrong, status code %s\n",response.getStatusLine().getStatusCode());
            }
        } catch (ClientProtocolException e) {
            System.out.printf("ClientProtocolException %s\n",e.toString());
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.printf("IOException %s\n",e.toString());
            throw new RuntimeException(e);
        }
        return responseString;
    }
    @GetMapping("/greet/{stub}")
    private String greet(@PathVariable String stub) {
        return String.format("Hello, %s", stub);
    }
}
