/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RESTservices;

/**
 *
 * @author jose
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class FOX {

    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {

        FOX http = new FOX();

        //  System.out.println("Testing 1 - Send Http GET request");
        //  http.sendGet();
        System.out.println("\nTesting 2 - Send Http POST request");
        String text = "Miles Davis was an american jazz musician";
        http.sendPost(text);

    }

    // HTTP GET request
    private void sendGet() throws Exception {

        String url = "http://www.google.com/search?q=mkyong";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

    // HTTP POST request
    private void sendPost(String texto) throws Exception {

        String url = "http://139.18.2.164:4444/api";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.addRequestProperty("Accept", "text/turtle");
        con.addRequestProperty("User-Agent", USER_AGENT);
        con.addRequestProperty("Accept-Language", "en-US,en;q=0.5");

        List<NameValuePair> params = new LinkedList<NameValuePair>();
        params.add(new BasicNameValuePair("input", texto));
        params.add(new BasicNameValuePair("type", "text"));//{text|url}
        params.add(new BasicNameValuePair("task", "NER"));//{NER}
        params.add(new BasicNameValuePair("output", "Turtle"));//{ JSON-LD | N-Triples | RDF/{ JSON | XML } | Turtle | TriG | N-Quads}

        
        //para mas parametros http://139.18.2.164:4444/#!/doc
        
        String urlParameters = URLEncodedUtils.format(params, "utf-8");
        String urlsinespacio = urlParameters.replaceAll("\\+", " ");
        System.out.println("URLparameters " + urlsinespacio);
        
        
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
       // wr.writeBytes(urlParameters);
        wr.writeBytes(urlsinespacio);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(java.net.URLDecoder.decode(response.toString(), "UTF-8"));

    }

}
