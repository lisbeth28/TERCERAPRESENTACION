package ucv.android.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
 
import ucv.android.bean.distritoBEAN;
import ucv.android.bean.empleadoBEAN;
import ucv.android.utils.ConexionHttpClient;
import ucv.android.utils.MySQLiteHelper; 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class distritoDAO {
 
	private SQLiteDatabase  database;
	private MySQLiteHelper  dbhelper;
	
    String  ruta="http://10.0.2.2:8080/PROYECTOMYSQL-SQLITE/CONTROLADOR/DistritoControlador.php";
	
	
	public  distritoDAO(Context  contexto)
		{ dbhelper=new MySQLiteHelper(contexto);
		}
	 
	public  void open() throws SQLiteException
		{ database=dbhelper.getWritableDatabase(); }
	 
	 public   void  close()
		{ dbhelper.close(); }
	 
	 public   long InsrtClienteSQLITE(distritoBEAN  objDistrito)
		{	
		 long  i=0;
			try   
			{    
				  ContentValues   valores=new ContentValues();
				  valores.put("idDistrito",objDistrito.getIdDistrito());
				  valores.put("Nombre",objDistrito.getNombre());
	              
	              i=database.insert(MySQLiteHelper.TABLADISTRITO,null,valores);
	              
	              
	             
			} catch (Exception e) 
			{ i=0;
			}
			return i;
		}
	 
	 public void borrTablaDistrito(){   dbhelper.borrarTablaDistrito(database);  }
	 
	 public  String[]  ListarDistritosSQL()
		{	Cursor  c=null;
		    ArrayList<distritoBEAN> lista=new ArrayList<distritoBEAN>();
		    distritoBEAN objDistrito ;
		    String[] datos ;
		    
		    try 
			  {       
			   c=database.rawQuery("Select *   from  "+MySQLiteHelper.TABLADISTRITO+";",null);
			   while(c.moveToNext())
			   {
				   objDistrito = new distritoBEAN();
				   
				   objDistrito.setIdDistrito(c.getInt(0));
				   objDistrito.setNombre(c.getString(1));

			       lista.add(objDistrito);
			   }
				c.close();
			} catch (Exception e)
			{			
			}
		   
		    datos =new String[lista.size()];
		    for (int i = 0; i < lista.size(); i++){
		    	objDistrito = lista.get(i);
		    	datos[i]= objDistrito.getNombre();
		    	
		    }
		    
			return   datos;
		}	 
	
	 public  int  obtenerIdDistrito (String nombre)
		{	Cursor  c=null;
		     
		    int idDistrito=0;
		    try 
			  {       
			   c=database.rawQuery("Select idDistrito  from  "+MySQLiteHelper.TABLADISTRITO+" where nombre='"+nombre+"'",null);
			   while(c.moveToNext())
			   { 
				   idDistrito=(c.getInt(0)); 
			   }
				c.close();
			} catch (Exception e)
			{			
			}
			return   idDistrito;
		}
	 
	// x...................... MYSQL ...........................................x
	 
	 public ArrayList<distritoBEAN>  obtDtsMYSQLcliente(){
		 
		 JSONObject     jsonobject=null;
		 distritoBEAN   objDistrito;
		 ArrayList<distritoBEAN> listaMysql=new ArrayList<distritoBEAN>();
		  	
		 try 
			{				
			      
		     List<NameValuePair>  parametros = new ArrayList<NameValuePair>();		 
		     parametros.add(new BasicNameValuePair("txtid","1"));		

		      
		     ConexionHttpClient objconexion=new   ConexionHttpClient();
		     jsonobject=objconexion.getConexionHttpClientPost(ruta, parametros);
		      
		  	 JSONArray    listaDistrito =jsonobject.getJSONArray("DISTRIT");	  
		  	 
				    for(int i = 0; i < listaDistrito.length(); i++) 
				    {
				      JSONObject   jsonDistrito = listaDistrito.getJSONObject(i);
				      objDistrito=new distritoBEAN();
				      
				      objDistrito.setIdDistrito(jsonDistrito.getInt("idDistrito"));
				      objDistrito.setNombre(jsonDistrito.getString("Nombre")); 
				  	
				      listaMysql.add(objDistrito);
				     }		     
			                    
			} catch (Exception e) {	 }
			
		 return listaMysql; 
	 }
	 
	// x.................................................................x
		
	 public int Sincronizar(){
		 int estado=1;
		 borrTablaDistrito();
		 
		 ArrayList<distritoBEAN> listaMysql=obtDtsMYSQLcliente();
		 distritoBEAN objDistrito= new distritoBEAN();
	    
		 try{
			 for(int i = 0; i < listaMysql.size(); i++){
				 objDistrito = listaMysql.get(i);
				  	
				 InsrtClienteSQLITE( objDistrito);
				 
		     }
	    }catch(Exception e){
	    	estado=0;
	    }
		  return estado;
		}
	 
		 
	 }

