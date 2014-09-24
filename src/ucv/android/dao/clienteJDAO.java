package ucv.android.dao;
import java.util.ArrayList;  
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import ucv.android.bean.clienteBEAN;
import ucv.android.bean.clienteJBEAN; 
import ucv.android.bean.distritoBEAN;
import ucv.android.utils.ConexionHttpClient;
import ucv.android.utils.MySQLiteHelper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;


public class clienteJDAO {
	
	private SQLiteDatabase  database;
	private MySQLiteHelper  dbhelper;
	
    String  ruta="http://10.0.2.2:8080/PROYECTOMYSQL-SQLITE/CONTROLADOR/ClienteJControlador.php";
    String rutaIngresar="http://10.0.2.2:8080/PROYECTOMYSQL-SQLITE/CONTROLADOR/ClienteJControladorIngreso.php";
    
	
	public  clienteJDAO(Context  contexto)
		{ dbhelper=new MySQLiteHelper(contexto);
		}
	 
	 public  void open() throws SQLiteException
		{ database=dbhelper.getWritableDatabase();
		}
	 
	 public   void  close()
		{ dbhelper.close();
		}
	 
	 public   long InsrtClienteJURDSQLITE(clienteJBEAN  objclientenat)
		{	
		   long  i=0;
		   clienteBEAN objcliente = new clienteBEAN();
			try   
			{  
				//------ingresa nuevo cliente   
				 ContentValues   valores=new ContentValues();
				  objcliente = objclientenat.getCliente();
				 
				  valores.put("idCliente",objcliente.getIdCliente());
				  valores.put("Nombre",objcliente.getNombre());
	              valores.put("Direccion",objcliente.getDireccion());
	              valores.put("Telefono",objcliente.getTelefono());
	              valores.put("idDistrito",objcliente.getDistrito().getIdDistrito()); 
	              valores.put("idEstado",objcliente.getIdEstado()); 
	              
	              System.out.println(" INSERTO EN SQLITE idDistrito =>"+objcliente.getDistrito().getIdDistrito()+"  "+"idEstado"+objcliente.getIdEstado());
		             
	              
	              i=database.insert(MySQLiteHelper.TABLACLIENTE,null,valores);
	             
	            //------ingresa nuevo cliente Natural  
				 
	              ContentValues   valores2=new ContentValues();
				  valores2.put("idCliente",objcliente.getIdCliente());
				  
				  valores2.put("Contacto",objclientenat.getContacto());
	              valores2.put("Url",objclientenat.getUrl());
	              valores2.put("RUC",objclientenat.getRUC());  
	              
	              i=database.insert(MySQLiteHelper.TABLACLIENTEJ,null,valores2);
	             
			} catch (Exception e) 
			{ i=0;
			}
			return i;
		}
	 
	 
	 public   long InsrtSoloClienteJuridSQLITE(clienteJBEAN  objclienteJurid)
		{	
		 long  i=0;
			try   
			{    
				  ContentValues   valores=new ContentValues();
				  
				  valores.put("idCliente",objclienteJurid.getCliente().getIdCliente());
				  valores.put("RUC",objclienteJurid.getRUC());
	              valores.put("Contacto",objclienteJurid.getContacto());
	              valores.put("Url",objclienteJurid.getUrl()); 
	              
	              i=database.insert(MySQLiteHelper.TABLACLIENTEJ,null,valores);
	             
	             System.out.println(" ingreso en SQLITE "+objclienteJurid.getCliente().getIdCliente()+" "+objclienteJurid.getRUC());
				  
			} catch (Exception e) 
			{ i=0;
			}
			return i;
		}
	 
	 
	 public void borrTablaclientejur(){ 
          dbhelper.borrarTablaClienteJ(database);
	 }
	 
	
	 
	 
	 public  clienteJBEAN  dtclientejurSQLITE(int idCliente)
		{	Cursor  c=null;
			clienteJBEAN objclientejur = new clienteJBEAN();
		    
		    try 
			  {       
			   c=database.rawQuery("Select *  from  empleado where idUsuario="+idCliente,null);
			   while(c.moveToNext())
			   { 
				//   objclientejur.setIdCliente(c.getInt(0));
				   objclientejur.setRUC(c.getString(1));    
				   objclientejur.setContacto(c.getString(2)); 
				   objclientejur.setUrl(c.getString(3)); 
			   }
				c.close();
			} catch (Exception e)
			{			
			}
			return   objclientejur;
		}	
	// x.................................................................x
	 
	 public ArrayList<clienteJBEAN>  obtDtsMYSQLclientejur(){
		 
		 JSONObject     jsonobject=null;
		 clienteJBEAN   objclientejur;
		 clienteBEAN objcliente;
		 
		 ArrayList<clienteJBEAN> listaMysql=new ArrayList<clienteJBEAN>();
		  	
		 try 
			{				
			      
		     List<NameValuePair>  parametros = new ArrayList<NameValuePair>();		 
		     parametros.add(new BasicNameValuePair("txtid","1"));		

		      
		     ConexionHttpClient objconexion=new   ConexionHttpClient();
		     jsonobject=objconexion.getConexionHttpClientPost(ruta, parametros);
		      
		  	 JSONArray    clientes =jsonobject.getJSONArray("CLINTJURD");	  
		  	  System.out.println(" recorrer array ");
		  	  
				    for(int i = 0; i < clientes.length(); i++) 
				    {
				      JSONObject   jsonCliente = clientes.getJSONObject(i);
				      objclientejur=new clienteJBEAN();
				      objcliente = new clienteBEAN();
				      
				      objcliente.setIdCliente(jsonCliente.getInt("idCliente")); 
				      
				      
				      objclientejur.setRUC(jsonCliente.getString("RUC"));   
				      objclientejur.setContacto(jsonCliente.getString("Contacto"));
				      objclientejur.setUrl(jsonCliente.getString("Url")); 
				      
				      objclientejur.setCliente(objcliente);
				      System.out.println("DATO MYSQL  id :"+objclientejur.getCliente().getIdCliente());
				      
				      listaMysql.add(objclientejur);
				    }		     
			                    
			} catch (Exception e) {	 }
			
		 return listaMysql; 
	 }
	 
	// x.................................................................x
		
	 public int Sincronizar(){ 
		 int estado=0;
		 borrTablaclientejur();
		 	
		 ArrayList<clienteJBEAN> listaMysql=obtDtsMYSQLclientejur();
		 clienteJBEAN objClientJRD= new clienteJBEAN();
		 
	     try{
			 
	    	 for(int i = 0; i < listaMysql.size(); i++){
	    		 objClientJRD = listaMysql.get(i);
				  InsrtSoloClienteJuridSQLITE( objClientJRD);
			 }
			  
	    }catch(Exception e){
	    	estado=0;
	    }
		  return estado;
		}
	
	 
 // x---------------------------------------------------------------------------

public void sincronizarMYSQL() {
	
	 ArrayList<clienteJBEAN> listaSQLITE = ListarClientesJ(6);
	 clienteJBEAN objClienteJURD= new clienteJBEAN();
	
	 try{
		 
   	 for(int i = 0; i < listaSQLITE.size(); i++){
   	    	objClienteJURD = listaSQLITE.get(i);
   	    	InsrtClienteJURDMYSQL( objClienteJURD);
		 }
		  
   }catch(Exception e){
   	//estado=0;
   }
    
}

private void InsrtClienteJURDMYSQL(clienteJBEAN objClienteJuridico) {
	 JSONObject     jsonobject=null; 
	 
	 		  	
	 try 
		{				
		 
		 System.out.println(" MYSQL ENVIANDO");
		 
	     List<NameValuePair>  parametros = new ArrayList<NameValuePair>();		 
	    
	     System.out.println("CODIGO "+ objClienteJuridico.getCliente().getIdCliente()+
	    		 "  NOMBRE"+ objClienteJuridico.getCliente().getNombre()+
	    		 "   IDDISTRITO"+objClienteJuridico.getCliente().getDistrito().getIdDistrito()
	    		 );
	    		 
	    		 
	     parametros.add(new BasicNameValuePair("CODIGO", objClienteJuridico.getCliente().getIdCliente()+"" ));		
	     parametros.add(new BasicNameValuePair("NOMBRE", objClienteJuridico.getCliente().getNombre() ));		
	     parametros.add(new BasicNameValuePair("DIRECCION",objClienteJuridico.getCliente().getDireccion()));		
	     parametros.add(new BasicNameValuePair("TELEFONO",objClienteJuridico.getCliente().getTelefono()));		
	     parametros.add(new BasicNameValuePair("IDDISTRITO",objClienteJuridico.getCliente().getDistrito().getIdDistrito()+"" ));		
	    
	     parametros.add(new BasicNameValuePair("CONTACTO",objClienteJuridico.getContacto()));		
	     parametros.add(new BasicNameValuePair("URL",objClienteJuridico.getUrl()));	 
	     parametros.add(new BasicNameValuePair("RUC",objClienteJuridico.getRUC()));		 

	      
	     ConexionHttpClient objconexion=new   ConexionHttpClient();
	     jsonobject=objconexion.getConexionHttpClientPost(rutaIngresar, parametros);
	     
	     String estado =jsonobject.getString("estado");
		    
	    System.out.println("Estado => "+estado);
		} catch (Exception e) {	 } 

}

public  ArrayList<clienteJBEAN>  ListarClientesJ(int idEstado)
{	
    Cursor  c=null;
    ArrayList<clienteJBEAN> lista=new ArrayList<clienteJBEAN>();
    clienteJBEAN objclienteJBEAN ; 
    distritoBEAN objDistrito;
	clienteBEAN objCliente;
	
    try 
	  {       
	   c=database.rawQuery("Select cj.idCliente, c.Nombre, cj.RUC ,cj.Contacto,c.Direccion ," +
	   		"c.idDistrito,c.Telefono,d.Nombre,cj.Url,c.idEstado from clienteJ cj inner join cliente c on" +
	   		" cj.idCliente=c.idCliente inner join  distrito d on c.idDistrito=d.idDistrito where c.idEstado="+idEstado+" ;",null);
	  
	  
	   while(c.moveToNext())
	   {
		   System.out.println(" LISTAR   "+idEstado);
		   objclienteJBEAN = new clienteJBEAN();
		   objDistrito = new distritoBEAN();
		   objCliente = new clienteBEAN();
		   
		   objCliente.setIdCliente(c.getInt(0));
		   objCliente.setNombre(c.getString(1));
		   
		   objclienteJBEAN.setRUC(c.getString(2));
		   objclienteJBEAN.setContacto(c.getString(3));
		  
		   objCliente.setDireccion(c.getString(4));
		   
		   objDistrito.setIdDistrito(c.getInt(5)); 
		   objCliente.setTelefono(c.getString(6)); 
		   
		   objDistrito.setNombre(c.getString(7)); 
		    
		   objCliente.setDistrito(objDistrito); 
		   
		   objclienteJBEAN.setUrl(c.getString(8)); 
		  
		   objCliente.setIdEstado(c.getInt(9));
		   
		   objclienteJBEAN.setCliente(objCliente);
		   
	       lista.add(objclienteJBEAN);
	   }
		c.close();
	} catch (Exception e)
	{			
	} 
	return   lista;
}	 


}


