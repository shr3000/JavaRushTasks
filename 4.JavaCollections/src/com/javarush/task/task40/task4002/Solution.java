package com.javarush.task.task40.task4002;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

/* 
Опять POST, а не GET
*/

public class Solution {
    public static void main(String[] args) throws Exception {
        Solution solution = new Solution();
        solution.sendPost("http://requestb.in/vmzlejvn", "name=zapp&mood=good&locale=&id=777");
    }

    public void sendPost(String url, String urlParameters) throws Exception {
        /*
       public class SimpleHttpPut {
    public static void main(String[] args) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://vogellac2dm.appspot.com/register");
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("registrationid",
                    "123456789"));
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
         */
        HttpClient client = getHttpClient();
//        HttpGet request = new HttpGet(url);
        HttpPost request = new HttpPost(url);
        request.addHeader("User-Agent", "Mozilla/5.0");

        List<NameValuePair> nameValuePairs = URLEncodedUtils.parse(urlParameters, Charset.forName("UTF-8"));
//
//        String[] strings = urlParameters.split("&");
//        for (String s: strings) {
//            String[] str = s.split("=");
//            nameValuePairs.add(new BasicNameValuePair(str[0], str.length == 1 ? "": str[1]));
//        }
        request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        HttpResponse response = client.execute(request);

        System.out.println("Response Code: " + response.getStatusLine().getStatusCode());

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String responseLine;
        while ((responseLine = bufferedReader.readLine()) != null) {
            result.append(responseLine);
        }

        System.out.println("Response: " + result.toString());
    }

    protected HttpClient getHttpClient() {
        return HttpClientBuilder.create().build();
    }
}
