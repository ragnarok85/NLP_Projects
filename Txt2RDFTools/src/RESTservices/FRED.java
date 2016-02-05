package RESTservices;

/**
 *
 * @author jose
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class FRED {

    public static void main(String[] args) throws URISyntaxException {
        FRED Fred = new FRED();
        String texto = "Miles Davis was an american jazz musician";

        System.out.println("text " + Fred.processFREDText(texto));
    }

    public String processFREDText(String text) {

        String texto = text;
        String acumulate = "";
        try {

            String urlT = "http://wit.istc.cnr.it/stlab-tools/fred";

            urlT += "?";
            List<NameValuePair> params = new LinkedList<NameValuePair>();
            params.add(new BasicNameValuePair("text", texto));
            params.add(new BasicNameValuePair("wsd", "true"));

            String paramString = URLEncodedUtils.format(params, "utf-8");

            urlT += paramString;

            URL url = new URL(urlT);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.addRequestProperty("Accept", "text/turtle");

            conn.addRequestProperty("wsd", "true");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            
            // parametros de configuracion http://wit.istc.cnr.it/stlab-tools/fred/api
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;

            while ((output = br.readLine()) != null) {
                acumulate += output + "\n";
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return acumulate;
        }

    }

}
