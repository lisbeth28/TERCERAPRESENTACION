package ucv.android.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
public class Detector {
private Context contexto;
 public Detector(Context contexto)
 {
 this.contexto = contexto;
 }
 public boolean estasConectado()
 {
	 boolean bconec=false;
 ConnectivityManager conectividad = (ConnectivityManager)
 contexto.getSystemService(Context.CONNECTIVITY_SERVICE);

	 NetworkInfo informacion = conectividad.getActiveNetworkInfo();
		 
			 if (informacion !=null && informacion.isConnected())
			 {
				 bconec=true;
				 return bconec;
				 }
		 
 return bconec;
 }
}
