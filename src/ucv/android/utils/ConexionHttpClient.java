package ucv.android.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class ConexionHttpClient {

	public JSONObject getConexionHttpClientPost(String ruta,List<NameValuePair> parametros){
	 
		InputStream   is=null;
        String  linea=null,json=null;
        JSONObject    jsonObject=null;
        try
         {
       	       HttpPost     httpPost = new HttpPost(ruta);		    
			   HttpClient      httpClient    =   new    DefaultHttpClient(); 
			    
			    httpPost.setEntity(new UrlEncodedFormEntity(parametros));
			    
			    HttpResponse    response   =  httpClient.execute(httpPost);        
			    
			    HttpEntity   entity  =  response.getEntity(); 			    
			     is = entity.getContent();			     
			     BufferedReader    reader   = new      BufferedReader(new InputStreamReader(is));  	
				StringBuilder sb = new StringBuilder();   			   	
		   	  
				while ( (  linea = reader.readLine() ) != null) 
			   {
				sb.append(linea + "\n");
		    	}
		     	is.close();			     
		     	// Parseamos la respuesta obtenida del servidor a un objeto JSON    
			     json=sb.toString(); // convirtiendo en una cadena
			     	
			     jsonObject = new JSONObject(json);
			     
        } catch (Exception e)
        {	
        } 
        return jsonObject;
	}
	
}