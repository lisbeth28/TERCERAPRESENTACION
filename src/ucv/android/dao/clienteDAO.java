package ucv.android.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import ucv.android.bean.clienteBEAN;
import ucv.android.bean.distritoBEAN;
import ucv.android.bean.productoBEAN;
import ucv.android.bean.unidadBEAN;
import ucv.android.utils.ConexionHttpClient;
import ucv.android.utils.MySQLiteHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class clienteDAO {

	private SQLiteDatabase  database;
	private MySQLiteHelper  dbhelper;
	
    String  ruta="http://10.0.2.2:8080/PROYECTOMYSQL-SQLITE/CONTROLADOR/ClienteControlador.php";
	
	
	public  clienteDAO(Context  contexto)
		{ dbhelper=new MySQLiteHelper(contexto);
		}
	 
	 public  void open() throws SQLiteException
		{ database=dbhelper.getWritableDatabase(); }
	 
	 public   void  close()
		{ dbhelper.close(); }
	 
	public void borrTablacliente(){   dbhelper.borrarTablaCliente(database);  }
	
	public  clienteBEAN  dtclienteSQLITE(int idCliente)
		{	
		    Cursor  c=null;
			clienteBEAN objcliente = new clienteBEAN();
			distritoBEAN objDistrito=new distritoBEAN() ;
			  
		    try {       
			   c=database.rawQuery("Select *  from  empleado where idCliente="+idCliente,null);
			   while(c.moveToNext())
			   { 
				   objcliente.setIdCliente(c.getInt(0));
				   objcliente.setNombre(c.getString(1));
				   objcliente.setDireccion(c.getString(2));
				   objcliente.setTelefono(c.getString(3));
				   
				   objDistrito.setNombre(c.getString(4)); 
				    
				   objcliente.setDistrito(objDistrito);   
			   }
				c.close();
			} catch (Exception e)
			{			
			}
			return   objcliente;
		}	
	 
	public clienteBEAN buscarCliente(String nombreCliente){
		 
		clienteBEAN objCliente= new clienteBEAN();
		  
		 Cursor  c=null;
		   try 
			  {       
			   c=database.rawQuery("Select idCliente,Nombre  from  cliente where Nombre like '%"+nombreCliente+"%';",null);
			   while(c.moveToNext())
			   { 
				   objCliente.setIdCliente(c.getInt(0)); 
				   objCliente.setNombre(c.getString(1));    
				   	
			   }
			   c.close();
			   
			  }catch(Exception e){} 
		   
		 return objCliente;
	 }
	
	// x...................... MYSQL ...........................................x
	 
	 public ArrayList<clienteBEAN>  obtDtsMYSQLcliente(){
		 
		 JSONObject     jsonobject=null;
		 clienteBEAN   objCliente;
		 ArrayList<clienteBEAN> listaMysql=new ArrayList<clienteBEAN>();
		  	
		 try 
			{				
			      
		     List<NameValuePair>  parametros = new ArrayList<NameValuePair>();		 
		     parametros.add(new BasicNameValuePair("txtid","1"));	
		     distritoBEAN objDistrito=new distritoBEAN() ;
				  
		     ConexionHttpClient objconexion=new   ConexionHttpClient();
		     jsonobject=objconexion.getConexionHttpClientPost(ruta, parametros);
		      
		  	 JSONArray    clientes =jsonobject.getJSONArray("CLINTS");	  
		  	 
				    for(int i = 0; i < clientes.length(); i++) 
				    {
				      JSONObject   jsonCliente = clientes.getJSONObject(i);
				      objCliente=new clienteBEAN();
				      
				      objCliente.setIdCliente(jsonCliente.getInt("idCliente"));
				      objCliente.setNombre(jsonCliente.getString("Nombre"));
				      objCliente.setDireccion(jsonCliente.getString("Direccion"));
				      objCliente.setTelefono(jsonCliente.getString("Telefono"));
				      objCliente.setIdEstado(jsonCliente.getInt("idEstado"));
				      objDistrito.setIdDistrito(jsonCliente.getInt("idDistrito"));
				      
				      objCliente.setDistrito(objDistrito);

				      listaMysql.add(objCliente);
				     
				    //  System.out.println(" id :"+objCliente.getIdCliente());
				     }		     
			                    
			} catch (Exception e) {	 }
			
		 return listaMysql; 
	 }
	 
	 public   long InsrtClienteSQLITE(clienteBEAN  objcliente)
		{	
		 long  i=0;
			try   
			{    
				  ContentValues   valores=new ContentValues();
				  valores.put("idCliente",objcliente.getIdCliente());
				  valores.put("Nombre",objcliente.getNombre());
	              valores.put("Direccion",objcliente.getDireccion());
	              valores.put("Telefono",objcliente.getTelefono());
	              valores.put("idEstado",objcliente.getIdEstado());
		              
	              valores.put("idDistrito",objcliente.getDistrito().getIdDistrito()); 
	              // System.out.println(" Estado => "+objcliente.getIdEstado());
	              i=database.insert(MySQLiteHelper.TABLACLIENTE,null,valores);
	             
	             
			} catch (Exception e) 
			{ i=0;
			}
			return i;
		}
	 
	 public  ArrayList<clienteBEAN>  ListarClientes()
		{	
		    Cursor  c=null;
		    ArrayList<clienteBEAN> lista=new ArrayList<clienteBEAN>();
		    clienteBEAN objclienteNBEAN ; 
		    distritoBEAN objDistrito=new distritoBEAN() ;
			
		    try 
			  {       
			   c=database.rawQuery("Select * from  "+MySQLiteHelper.TABLACLIENTE+";",null);
			   while(c.moveToNext())
			   {
				   objclienteNBEAN = new clienteBEAN();
				   
				   objclienteNBEAN.setIdCliente(c.getInt(0)); 
				   objclienteNBEAN.setNombre(c.getString(1));
				   objclienteNBEAN.setDireccion(c.getString(2));
				   objclienteNBEAN.setTelefono(c.getString(3));
				   objclienteNBEAN.setIdEstado(c.getInt(4));
					  
				   objDistrito.setIdDistrito(c.getInt(5)); 
				   
				   objclienteNBEAN.setDistrito(objDistrito);
				   lista.add(objclienteNBEAN);
			   }
				c.close();
			} catch (Exception e)
			{			
			} 
			return   lista;
		}
	 
	// x.................................................................x
		
	 public int Sincronizar(){
		 int idCliente=0;
		 borrTablacliente();
		 
		 ArrayList<clienteBEAN> listaMysql=obtDtsMYSQLcliente();
		 clienteBEAN objcliente= new clienteBEAN();
	    
		 try{
			 for(int i = 0; i < listaMysql.size(); i++){
				
				 objcliente = listaMysql.get(i);
				 InsrtClienteSQLITE( objcliente);
				
				 idCliente=objcliente.getIdCliente(); 	
		     }
	    }catch(Exception e){
	    	idCliente=0;
	    }
		  return idCliente;
		}
	 
		 
	 }
	 
