package ucv.android.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import ucv.android.bean.marcaBEAN;
import ucv.android.utils.ConexionHttpClient;
import ucv.android.utils.Detector;
import ucv.android.utils.MySQLiteHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class marcaDAO {
	private SQLiteDatabase  database;
	private MySQLiteHelper  dbhelper;
	
    String  ruta="http://10.0.2.2:8080/PROYECTOMYSQL-SQLITE/CONTROLADOR/MarcaControlador.php";
	private Detector objDetector;
	private Context Contexto;
	
	
	public  marcaDAO(Context  contexto)
		{ dbhelper=new MySQLiteHelper(contexto);
		  Contexto=contexto;
		}
	 
	 public  void open() throws SQLiteException
		{ database=dbhelper.getWritableDatabase();
		}
	 
	 public   void  close()
		{ dbhelper.close();
		}
	 
	 public   long InsrtMarcaSQLITE(marcaBEAN  objMarca)
		{	
		 long  i=0;
			try   
			{    
				  ContentValues   valores=new ContentValues();
				  valores.put("idMarca",objMarca.getIdMarca());
		          valores.put("Nombre",objMarca.getNombre());
	              valores.put("Codigo",objMarca.getCodigo());
                  valores.put("idEstado",objMarca.getIdEstado());
	              
	               i=database.insert(MySQLiteHelper.TABLAMARCA,null,valores);
	             
	             System.out.println(" SQSLITE MARCA"+objMarca.getIdMarca());
			} catch (Exception e) 
			{ i=0;
			}
			return i;
		}
	 
	 public void borrTablaMarca(){ 
        dbhelper.borrarTablaMarca(database);
	 }
	 
	 public   long  ModfMarcaSQLITE(marcaBEAN objMarca)
		{	
		  long i=0;
		  try 
		  {	
			 ContentValues  valores=new ContentValues();
			 valores.put("idMarca",objMarca.getIdMarca());
	         valores.put("Nombre",objMarca.getNombre()); 
	         valores.put("Codigo",objMarca.getCodigo());
	         valores.put("idEstado",objMarca.getIdEstado()); 
	       
	    //     i=database.update(MySQLiteHelper.TABLAMARCA,
		//		     valores,"idMarca="+objMarca.getIdMarca(),null);			
		
		  } catch (Exception e) {	}
			return  i;
		}
	 
	 public  marcaBEAN  datosMarcaSQLITE(int idMarca)
		{	Cursor  c=null;
		    marcaBEAN objMarca = new marcaBEAN();
		    
		    try 
			  {       
			   c=database.rawQuery("Select Nombre,Codigo,idEstado from  marca where idMarca="+idMarca,null);
			   while(c.moveToNext())
			   { 
				   objMarca.setNombre(c.getString(0));
				   objMarca.setCodigo(c.getString(1));
				   objMarca.setIdEstado(c.getInt(2));
			   }
				c.close();
			} catch (Exception e)
			{			
			}
			return   objMarca;
		}	
	// x.................................................................x
	 

	 public ArrayList<marcaBEAN> obtenerListaMysql(){
		 JSONObject     jsonobject=null;
		 ArrayList<marcaBEAN> listaMysql=new ArrayList<marcaBEAN>();
		 try 
			{				
			      
		     List<NameValuePair>  parametros = new ArrayList<NameValuePair>();		 
		     parametros.add(new BasicNameValuePair("TXTCODIGO", "1"));		

		      
		     ConexionHttpClient objconexion=new   ConexionHttpClient();
		     jsonobject=objconexion.getConexionHttpClientPost(ruta, parametros);
		      
		  	 JSONArray    marca =jsonobject.getJSONArray("Marcas");	  
		  	
				    for(int i = 0; i < marca.length(); i++) 
				    {
				      JSONObject   jsonMarca = marca.getJSONObject(i);
				      marcaBEAN   objmarca=new marcaBEAN();
				      
				      objmarca.setIdMarca(jsonMarca.getInt("idMarca"));
				      objmarca.setNombre(jsonMarca.getString("Nombre"));
				      objmarca.setCodigo(jsonMarca.getString("Codigo"));
				      objmarca.setIdEstado(jsonMarca.getInt("idEstado"));
				    			   
				      System.out.println(" MYSQL MARCA"+objmarca.getIdMarca());
				      
				      listaMysql.add(objmarca);					      
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
			  borrTablaMarca();
			 
			  ArrayList<marcaBEAN> listaMysql=obtenerListaMysql();
			     marcaBEAN objMarca= new marcaBEAN();
			     try{
					 for(int i = 0; i < listaMysql.size(); i++){
						 objMarca = listaMysql.get(i);
						 InsrtMarcaSQLITE( objMarca);
						 
				     }
			    }catch(Exception e){     }
			   
		  }else{
			  
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
	


