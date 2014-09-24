package ucv.android.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import ucv.android.bean.unidadBEAN;
import ucv.android.utils.ConexionHttpClient;
import ucv.android.utils.Detector;
import ucv.android.utils.MySQLiteHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class unidadmedidaDAO {
	private SQLiteDatabase  database;
	private MySQLiteHelper  dbhelper;
	
    String  ruta="http://10.0.2.2:8080/PROYECTOMYSQL-SQLITE/CONTROLADOR/UnidadMedidaControlador.php";
	private Detector objDetector;
	private Context Contexto;
	
	
	public  unidadmedidaDAO(Context  contexto)
		{ dbhelper=new MySQLiteHelper(contexto);
		  Contexto=contexto;
		}
	 
	 public  void open() throws SQLiteException
		{ database=dbhelper.getWritableDatabase();
		}
	 
	 public   void  close()
		{ dbhelper.close();
		}
	 
	 public   long InsrtUnidadSQLITE(unidadBEAN objUnidad)
		{	
		 long  i=0;
			try   
			{    
				  ContentValues   valores=new ContentValues();
				  valores.put("idUnidadMedida",objUnidad.getIdUnidadMedida());
		          valores.put("Unidad",objUnidad.getUnidad());
	              
	             i=database.insert(MySQLiteHelper.TABLAUNIDADMEDIDA,null,valores);
	             
	             System.out.println("INGRESO EN SQLITE UNIDAD :"+objUnidad.getIdUnidadMedida());
			} catch (Exception e) 
			{ i=0;
			}
			return i;
		}
	 
	 public void borrTablaUnidad(){ 

			dbhelper.borrarTablaUnidadMedida(database);
	 }
	 
	 public   long  ModfMarcaSQLITE(unidadBEAN objUnidad)
		{	
		  long i=0;
		  try 
		  {	
			 ContentValues  valores=new ContentValues();
			 valores.put("idUnidadMedida",objUnidad.getIdUnidadMedida());
	         valores.put("Unidad",objUnidad.getUnidad());  
	       
	      //   i=database.update(MySQLiteHelper.TABLAUNIDAD,
			//	     valores,"idUnidadMedida="+objUnidad.getIdUnidadMedida(),null);			
		
		  } catch (Exception e) {	}
			return  i;
		}
	 
	 public  unidadBEAN  datosUnidadSQLITE(int idUnidadMedida)
		{	Cursor  c=null;
		    unidadBEAN objUnidad = new unidadBEAN();
		    
		    try 
			  {       
			   c=database.rawQuery("Select Unidad from  unidadmedida where idUnidadMedida="+idUnidadMedida,null);
			   while(c.moveToNext())
			   { 
				   objUnidad.setUnidad(c.getString(0));
			   }
				c.close();
			} catch (Exception e)
			{			
			}
			return   objUnidad;
		}	
	// x.................................................................x
	 

	 public ArrayList<unidadBEAN> obtenerListaMysql(){
		 JSONObject     jsonobject=null;
		 ArrayList<unidadBEAN> listaMysql=new ArrayList<unidadBEAN>();
		 try 
			{				
			      
		     List<NameValuePair>  parametros = new ArrayList<NameValuePair>();		 
		     parametros.add(new BasicNameValuePair("TXTCODIGO", "1"));		

		      
		     ConexionHttpClient objconexion=new   ConexionHttpClient();
		     jsonobject=objconexion.getConexionHttpClientPost(ruta, parametros);
		      
		  	 JSONArray    unidad =jsonobject.getJSONArray("UnidadesMedida");	  
		  	
				    for(int i = 0; i < unidad.length(); i++) 
				    {
				      JSONObject   jsonUnidad = unidad.getJSONObject(i);
				      unidadBEAN   objunidad=new unidadBEAN();
				      
				      objunidad.setIdUnidadMedida(jsonUnidad.getInt("idUnidadMedida"));
				      objunidad.setUnidad(jsonUnidad.getString("Unidad"));
				    	
				      System.out.println(" RECIBO MYSQL : "+objunidad.getIdUnidadMedida());
				      
				      listaMysql.add(objunidad);					      
				    }		     
			 
			} catch (Exception e) 
			{			
			}
			
		 return listaMysql; 
	 }	 
	// x.................................................................x
		
	 public int Sincronizar(){
		 int estadoRed=comprobarConexion(Contexto);
		    
		 if(estadoRed==1){
			  
			 borrTablaUnidad();
			 
			 ArrayList<unidadBEAN> listaMysql=obtenerListaMysql();
		     unidadBEAN objUnidad= new unidadBEAN();
		     try{
				 for(int i = 0; i < listaMysql.size(); i++){
					 System.out.println("  SINCRONIZAR ");
					 objUnidad = listaMysql.get(i);
					 InsrtUnidadSQLITE(objUnidad);  
			     }
				 
		    }catch(Exception e){  }
		 }
	     return estadoRed;
	 }
	 
	 
	 private int comprobarConexion(Context contexto) {
			Boolean hayInt = false;
			objDetector = new Detector(contexto); 
		    hayInt = objDetector.estasConectado();
			
		    if(hayInt){ return 1; }
			 
			else{  return 0;  }
			 
		}
	 
}
