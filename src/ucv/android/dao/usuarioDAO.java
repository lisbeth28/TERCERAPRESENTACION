package ucv.android.dao;

import java.util.ArrayList;  
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject; 

import ucv.android.bean.usuarioBEAN;
import ucv.android.utils.ConexionHttpClient;
import ucv.android.utils.Detector;
import ucv.android.utils.MySQLiteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
 

public class usuarioDAO {

	private SQLiteDatabase  database;
	private MySQLiteHelper  dbhelper;
	
    String  ruta="http://10.0.2.2:8080/PROYECTOMYSQL-SQLITE/CONTROLADOR/UsuarioControlador.php";
	
	
	public  usuarioDAO(Context  contexto)
		{ dbhelper=new MySQLiteHelper(contexto);
		}
	 
	 public  void open() throws SQLiteException
		{ database=dbhelper.getWritableDatabase();
		}
	 
	 public   void  close()
		{ dbhelper.close();
		}
	 
	 public   long InsrtUsuarioSQLITE(usuarioBEAN  objusuario)
		{	
		 long  i=0;
			try   
			{    
				  ContentValues   valores=new ContentValues();
				  valores.put("idUsuario",objusuario.getIdUsuario());
	              valores.put("usuario",objusuario.getUsuario());
	              valores.put("password",objusuario.getPassword()); 
	          			
	              i=database.insert(MySQLiteHelper.TABLAUSUARIO,null,valores);
		         
			} catch (Exception e) 
			{ i=0;
			}
			return i;
		}
	 
	 public void borrarTblUsuario(){ 
			dbhelper.borrarTablaUsuarios(database);
	 }
	
	 
	 public int autentificarSQLITE(String usuario,String pswrd)
		{	 
		    Cursor  c=null;
		    int idUsuario=0;
		     try 
			  {       
			   c=database.rawQuery("SELECT idUsuario  FROM usuario " +
						"WHERE usuario='"+usuario+"' and password= '"+pswrd+"' ", null);
			  
			   while(c.moveToNext())
			   {
				   idUsuario = c.getInt(0); 
			      
			   }
				c.close();
			} catch (Exception e)
			{			
			}
			return  idUsuario;
		}
	 
	
	 // ---------------------------------------------------------------------------
	 
	 public ArrayList<usuarioBEAN> obtenerListaMysql(){
		 JSONObject     jsonobject=null;
		 ArrayList<usuarioBEAN> listaMysql=new ArrayList<usuarioBEAN>();
		 try 
			{				
			      
		     List<NameValuePair>  parametros = new ArrayList<NameValuePair>();		 
		     parametros.add(new BasicNameValuePair("TXTCODIGO", "1"));		

		      
		     ConexionHttpClient objconexion=new   ConexionHttpClient();
		     jsonobject=objconexion.getConexionHttpClientPost(ruta, parametros);
		      
		  	 JSONArray    personas =jsonobject.getJSONArray("USERS");	  
		  	
				    for(int i = 0; i < personas.length(); i++) 
				    {
				      JSONObject   jsonPersonas = personas.getJSONObject(i);
				      usuarioBEAN   objusuario=new usuarioBEAN();
				      
				      objusuario.setIdUsuario(jsonPersonas.getInt("idUsuario"));
					  objusuario.setUsuario(jsonPersonas.getString("usuario"));
				      objusuario.setPassword(jsonPersonas.getString("password")); ;
				    
				     	
				      listaMysql.add(objusuario);					      
				    }		     
			                    
			 
			} catch (Exception e) 
			{			
			}
			
		 return listaMysql; 
	 }
	 
	// x.................................................................x
		
	 public int Sincronizar(Context  contexto){
		 
		 int estado= comprobarConexion(contexto);
		 
		 if(estado==1){
			 borrarTblUsuario();
			 ArrayList<usuarioBEAN> listaMysql=obtenerListaMysql();
		     usuarioBEAN objUsuario= new usuarioBEAN();
		     try{
				 for(int i = 0; i < listaMysql.size(); i++){
					 objUsuario = listaMysql.get(i);
					 InsrtUsuarioSQLITE( objUsuario);
					 
			     }
		    }catch(Exception e){
		    	estado=0;
		    }
		 }
		  
		  return estado;
	}

	private int comprobarConexion(Context  contexto) {
		
		Boolean hayInt = false;
		Detector objDetector;
		
		objDetector = new Detector(contexto);
		 
	    hayInt = objDetector.estasConectado();
		
	    if(hayInt){ return 1; }
		 
		else{  return 0;  }
	
		
	}
	 
}

