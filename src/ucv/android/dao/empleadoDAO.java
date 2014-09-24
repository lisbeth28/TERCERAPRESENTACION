package ucv.android.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
 
import ucv.android.bean.empleadoBEAN;
import ucv.android.utils.ConexionHttpClient;
import ucv.android.utils.MySQLiteHelper;
 
import android.content.ContentValues;
import android.content.Context; 
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class empleadoDAO {
	private SQLiteDatabase  database;
	private MySQLiteHelper  dbhelper;
	
    String  ruta="http://10.0.2.2:8080/PROYECTOMYSQL-SQLITE/CONTROLADOR/EmpleadoControlador.php";
	
	
	public  empleadoDAO(Context  contexto)
		{ dbhelper=new MySQLiteHelper(contexto);
		}
	 
	 public  void open() throws SQLiteException
		{ database=dbhelper.getWritableDatabase();
		}
	 
	 public   void  close()
		{ dbhelper.close();
		}
	 
	 public   long InsrtEmpleadoSQLITE(empleadoBEAN  objEmpleado)
		{	
		 long  i=0;
			try   
			{    
				  ContentValues   valores=new ContentValues();
				  valores.put("idEmpleado",objEmpleado.getIdEmpleado());
		          valores.put("nombre",objEmpleado.getNombres());
	              valores.put("apellido",objEmpleado.getApellidos());
                  valores.put("idUsuario",objEmpleado.getIdUsuario());
	              
	              i=database.insert(MySQLiteHelper.TABLAEMPLEADO,null,valores);
	             
	             
			} catch (Exception e) 
			{ i=0;
			}
			return i;
		}
	 
	 public void borrTablaEmpleado(){ 

			dbhelper.borrarTablaEmpleado(database);
	 }
	 
	 public   long  ModfEmpleadoSQLITE(empleadoBEAN objEmpleado)
		{	
		  long i=0;
		  try 
		  {	
			 ContentValues  valores=new ContentValues();
			 valores.put("nombre",objEmpleado.getNombres());
	         valores.put("apellido",objEmpleado.getApellidos()); 
	       
	         i=database.update(MySQLiteHelper.TABLAEMPLEADO,
				     valores,"idEmpleado="+objEmpleado.getIdEmpleado(),null);			
		
		  } catch (Exception e) {	}
			return  i;
		}
	 
	 public  empleadoBEAN  datosEmpldSQLITE(int idUsuario)
		{	Cursor  c=null;
		    empleadoBEAN objEmpleado = new empleadoBEAN();
		    
		    try 
			  {       
			   c=database.rawQuery("Select nombre,apellido,idEmpleado   from  empleado where idUsuario="+idUsuario,null);
			   while(c.moveToNext())
			   { 
				   objEmpleado.setNombres(c.getString(0));
				   objEmpleado.setApellidos(c.getString(1)); 
				   objEmpleado.setIdEmpleado(c.getInt(2)); 
				   
				   
			   }
				c.close();
			} catch (Exception e)
			{			
			}
			return   objEmpleado;
		}	
	// x.................................................................x
	 
	 public empleadoBEAN obtDtsMYSQLEmpleado(int idUsuario){
		 
		 JSONObject     jsonobject=null;
		 empleadoBEAN   objEmpleado=new empleadoBEAN();
		  	
		 try 
			{				
			      
		     List<NameValuePair>  parametros = new ArrayList<NameValuePair>();		 
		     parametros.add(new BasicNameValuePair("TXTIDUSUARIO", idUsuario+""));		

		      
		     ConexionHttpClient objconexion=new   ConexionHttpClient();
		     jsonobject=objconexion.getConexionHttpClientPost(ruta, parametros);
		      
		  	 JSONArray    personas =jsonobject.getJSONArray("EMPLEADS");	  
		  	 
				    for(int i = 0; i < personas.length(); i++) 
				    {
				      JSONObject   jsonPersonas = personas.getJSONObject(i);
				      
				      objEmpleado.setIdEmpleado(jsonPersonas.getInt("idEmpleado"));
					  objEmpleado.setNombres(jsonPersonas.getString("Nombre"));
				      objEmpleado.setApellidos(jsonPersonas.getString("Apellido")); 
				      objEmpleado.setIdUsuario(idUsuario); 
					     
				        }		     
			                    
			} catch (Exception e) {	 }
			
		 return objEmpleado; 
	 }
	 
	// x.................................................................x
		
	 public void Sincronizar(int idUsuario){
		
		 borrTablaEmpleado();
		 
		 empleadoBEAN empleado=obtDtsMYSQLEmpleado(idUsuario);
		
		 InsrtEmpleadoSQLITE( empleado);
		 
	 }
	 
}

