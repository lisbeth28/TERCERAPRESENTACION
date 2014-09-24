package ucv.android.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
 
import ucv.android.bean.lineaPrincipal;
import ucv.android.utils.ConexionHttpClient;
import ucv.android.utils.Detector;
import ucv.android.utils.MySQLiteHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class lineaPrincipalDAO {
	private SQLiteDatabase  database;
	private MySQLiteHelper  dbhelper;
	
    String  ruta="http://10.0.2.2:8080/PROYECTOMYSQL-SQLITE/CONTROLADOR/LineaPrincipalControlador.php";
	private Detector objDetector;
	private Context Contexto;
	
	public  lineaPrincipalDAO(Context  contexto)
		{ dbhelper=new MySQLiteHelper(contexto);
		  Contexto=contexto;
		}
	 
	 public  void open() throws SQLiteException
		{ database=dbhelper.getWritableDatabase();
		}
	 
	 public   void  close()
		{ dbhelper.close();
		}
	 
	 public   long InsrtSubLineaSQLITE(lineaPrincipal objLineaPrincipal)
		{	
		 long  i=0;
			try   
			{    
				  ContentValues   valores=new ContentValues();
				  valores.put("idLineaPrincipal",objLineaPrincipal.getIdLineaPrincipal());
		          valores.put("Nombre",objLineaPrincipal.getNombre());
		          valores.put("Codigo",objLineaPrincipal.getCodigo());
		          valores.put("idEstado",objLineaPrincipal.getIdEstado());
	              
	              i=database.insert(MySQLiteHelper.TABLALINEAPRINCIPAL,null,valores);
	             
	              System.out.println("INSERTE EN SQLITE => ID:"+objLineaPrincipal.getIdLineaPrincipal()); 
			} catch (Exception e) 
			{ i=0;
			}
			return i;
		}
	 
	 public void borrTablaSublinea(){ 
        dbhelper.borrarTablaLineaPrincipal(database);
	 }
	 
	 public   long  ModSublineaSQLITE(lineaPrincipal objSublinea)
		{	
		  long i=0;
		  try 
		  {	
			 ContentValues  valores=new ContentValues();
		//	  valores.put("idMarca",objSublinea.getIdSubLinea());
	          valores.put("nombre",objSublinea.getNombre());
              valores.put("idEstado",objSublinea.getIdEstado());
	       
	   //      i=database.update(MySQLiteHelper.TABLASUBLINEA,
		//		     valores,"idSubLinea="+objSublinea.getIdSubLinea(),null);			
		
		  } catch (Exception e) {	}
			return  i;
		}
	 
	 public  lineaPrincipal  datosSublineaSQLITE(int idLineaPrincipal)
		{	Cursor  c=null;
		    lineaPrincipal objSublinea = new lineaPrincipal();
		    
		    try 
			  {       
			   c=database.rawQuery("Select Nombre,Codigo,idEstado from  "+MySQLiteHelper.TABLALINEAPRINCIPAL+" where idLineaPrincipal="+idLineaPrincipal+" ;",null);
			   while(c.moveToNext())
			   { 
				   objSublinea.setNombre(c.getString(0));
				   objSublinea.setCodigo(c.getString(1));
				   objSublinea.setIdEstado(c.getInt(2));
			   }
				c.close();
			} catch (Exception e)
			{			
			}
			return   objSublinea;
		}	
	// x.................................................................x
	 

	 public ArrayList<lineaPrincipal> obtenerListaMysql(){
		 JSONObject     jsonobject=null;
		 ArrayList<lineaPrincipal> listaMysql=new ArrayList<lineaPrincipal>();
		 try 
			{				
			 List<NameValuePair>  parametros = new ArrayList<NameValuePair>();		 
		     parametros.add(new BasicNameValuePair("TXTCODIGO", "1"));		

		      
		     ConexionHttpClient objconexion=new   ConexionHttpClient();
		     jsonobject=objconexion.getConexionHttpClientPost(ruta, parametros);
		      
		  	 JSONArray    sublinea =jsonobject.getJSONArray("LINEAS");	  
		  	
				    for(int i = 0; i < sublinea.length(); i++) 
				    {
				      JSONObject   jsonSublinea = sublinea.getJSONObject(i);
				      lineaPrincipal objLineaPrincipal=new lineaPrincipal();
				      
				      objLineaPrincipal.setIdLineaPrincipal(jsonSublinea.getInt("idLineaPrincipal"));
				      objLineaPrincipal.setNombre(jsonSublinea.getString("Nombre"));
				      objLineaPrincipal.setCodigo(jsonSublinea.getString("Codigo"));
				      objLineaPrincipal.setIdEstado(jsonSublinea.getInt("idEstado"));
				    	
				      System.out.println(" LP => ID:"+objLineaPrincipal.getIdLineaPrincipal());
				      
				      listaMysql.add(objLineaPrincipal);					      
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
			 
			 borrTablaSublinea();
			 
			 ArrayList<lineaPrincipal> listaMysql=obtenerListaMysql();
			 lineaPrincipal objSublinea= new lineaPrincipal();
		   
			 try{
				 for(int i = 0; i < listaMysql.size(); i++){
					 objSublinea = listaMysql.get(i);
					 InsrtSubLineaSQLITE(objSublinea);
					 
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

