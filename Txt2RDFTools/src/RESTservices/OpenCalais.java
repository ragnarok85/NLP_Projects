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
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class OpenCalais {

    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {

        OpenCalais http = new OpenCalais();

        //  System.out.println("Testing 1 - Send Http GET request");
        //  http.sendGet();
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

        HttpPost post = new HttpPost("http://api.opencalais.com/tag/rs/enrich");
        HttpClient cliente = new DefaultHttpClient();

        post.setHeader("x-calais-licenseID", "jxurqk6j9jgn2vheh95retyr");
     

        
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();

        formparams.add(new BasicNameValuePair("contentType", "TEXT/RAW"));
        formparams.add(new BasicNameValuePair("outputFormat", "text/N3"));
        formparams.add(new BasicNameValuePair("content", texto));
        
        //para mas parametros
        //http://www.opencalais.com/documentation/calais-web-service-api/forming-api-calls/input-parameters
        
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);

        post.setEntity(entity);

        HttpResponse respons = cliente.execute(post);

        BufferedReader rd = new BufferedReader(new InputStreamReader(respons.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
            System.out.println(line);
        }
    }

}
