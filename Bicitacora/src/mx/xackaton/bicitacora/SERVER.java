package mx.xackaton.bicitacora;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Toast;

/**
 *
 * @author xianur0
 */
public class SERVER {

    String server = "http://bicitacora.developingo.webfactional.com";

    public class UDP{
    	public int CODIGO;
    	public String MSG;
    	public String TOKEN;
    }
    
    public UDP login(String email, String password) {
        String url = server + "/api/login";

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("correo", email));
        pairs.add(new BasicNameValuePair("password", password));
        try {
            httpost.setEntity(new UrlEncodedFormEntity(pairs));
            HttpResponse response = httpclient.execute(httpost);
            StringBuilder a = inputStreamToString(response.getEntity().getContent());
            UDP u = new UDP();
            JSONObject jObject = new JSONObject(a.toString());
            u.CODIGO = jObject.getInt("error");
            if (u.CODIGO != 0){
            	u.MSG = jObject.getString("msg");
            }else{
            	u.TOKEN = jObject.getString("token");
            }
            System.out.println(u.CODIGO);
            return u;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }

    private StringBuilder inputStreamToString(InputStream is) {
        String line = "";
        StringBuilder total = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException ex) {
            //Logger.getLogger(SERVER.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    public void register(String nombre, String sexo, String fechanac, String email, String password) {
        String url = server + "/api/registro";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpost = new HttpPost(url);
        byte buffer[] = new byte[255];
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("nombre", nombre));
        pairs.add(new BasicNameValuePair("sexo", sexo));
        pairs.add(new BasicNameValuePair("fecha_nacimiento", fechanac));
        pairs.add(new BasicNameValuePair("correo", email));
        pairs.add(new BasicNameValuePair("password", password));
        try {
            httpost.setEntity(new UrlEncodedFormEntity(pairs));
            HttpResponse response = httpclient.execute(httpost);
            StringBuilder a = inputStreamToString(response.getEntity().getContent());
            System.out.println("Tacos: "+a.toString());
            JSONObject jObject = new JSONObject(a.toString());
            System.out.println(jObject.getString("error"));
            global.token = jObject.getString("token");
        } catch (JSONException ex) {
            //Logger.getLogger(SERVER.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //Logger.getLogger(SERVER.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void road(JSONObject road){
    	String url = server + "/api/w/ruta";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpost = new HttpPost(url);
        byte buffer[] = new byte[255];
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("token", global.token));
        pairs.add(new BasicNameValuePair("ruta", road.toString()));
        try {
            httpost.setEntity(new UrlEncodedFormEntity(pairs));
            HttpResponse response = httpclient.execute(httpost);
            StringBuilder a = inputStreamToString(response.getEntity().getContent());
            JSONObject jObject = new JSONObject(a.toString());
            String error = jObject.getString("error");
            System.out.println(error);
            System.out.println(jObject.getString("msg"));
        } catch (Exception ex) {
        	System.out.println( ex.getCause().toString());
            //Logger.getLogger(SERVER.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}