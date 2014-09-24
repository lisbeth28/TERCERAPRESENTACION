package ucv.android.dao;


import java.util.ArrayList;  
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import ucv.android.bean.clienteBEAN;
import ucv.android.bean.clienteNBEAN; 
import ucv.android.bean.distritoBEAN;
import ucv.android.utils.ConexionHttpClient;
import ucv.android.utils.MySQLiteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class clienteNDAO {
	private SQLiteDatabase  database;
	private MySQLiteHelper  dbhelper;
	
    String  ruta="http://10.0.2.2:8080/PROYECTOMYSQL-SQLITE/CONTROLADOR/ClienteNControlador.php";
	String rutaIngresar="http://10.0.2.2:8080/PROYECTOMYSQL-SQLITE/CONTROLADOR/ClienteNControladorIngreso.php";
 
	public  clienteNDAO(Context  contexto)
		{ dbhelper=new MySQLiteHelper(contexto);
		}
	 
	 public  void open() throws SQLiteException
		{ database=dbhelper.getWritableDatabase();
		}
	 
	 public   void  close()
		{ dbhelper.close();
		}
	 
	 public   long InsrtClienteNatSQLITE(clienteNBEAN  objclientenat)
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
	              
	              System.out.println(" INSERTO EN SQLITE idCliente =>"+objcliente.getIdCliente()+"  "+objcliente.getDistrito().getIdDistrito());
		             
	              
	              i=database.insert(MySQLiteHelper.TABLACLIENTE,null,valores);
	             
	            //------ingresa nuevo cliente Natural  
				 
	              ContentValues   valores2=new ContentValues();
				  valores2.put("idCliente",objcliente.getIdCliente());
				   
	              valores2.put("DNI",objclientenat.getDNI());
	              valores2.put("Celular",objclientenat.getCelular());
	              valores2.put("Correo",objclientenat.getCorreo()); 
	              
	              i=database.insert(MySQLiteHelper.TABLACLIENTEN,null,valores2);
	             
			} catch (Exception e) 
			{ i=0;
			}
			return i;
		}
	 
	 
	 public   long InsrtSoloClienteNatSQLITE(clienteNBEAN  objclientenat)
		{	
		 long  i=0;
			try   
			{    
				  ContentValues   valores=new ContentValues();
				  
				  valores.put("idCliente",objclientenat.getCliente().getIdCliente()); 
	              valores.put("DNI",objclientenat.getDNI());
	              valores.put("Celular",objclientenat.getCelular());
	              valores.put("Correo",objclientenat.getCorreo()); 
	              
	              i=database.insert(MySQLiteHelper.TABLACLIENTEN,null,valores);
	              
			} catch (Exception e) 
			{ i=0;
			}
			return i;
		}
	 
	 public void borrTablaclientenat(){ 
      	dbhelper.borrarTablaClienteN(database);
	 }
	 
	 	 
	 public  ArrayList<clienteNBEAN>  ListarClientesN(int idEstado)
		{	
		    Cursor  c=null;
		    ArrayList<clienteNBEAN> lista=new ArrayList<clienteNBEAN>();
		    clienteNBEAN objclienteNBEAN ; 
		    distritoBEAN objDistrito;
			clienteBEAN objCliente;
			
		    try 
			  {       
			   c=database.rawQuery("Select cn.idCliente, c.Nombre ,cn.DNI,c.Direccion ," +
			   		"c.idDistrito,c.Telefono,d.Nombre,cn.Celular,cn.Correo,c.idEstado from clienteN cn inner join cliente c on" +
			   		" cn.idCliente=c.idCliente inner join  distrito d on c.idDistrito=d.idDistrito where c.idEstado="+idEstado+" ;",null);
			
			   while(c.moveToNext())
			   {
				   objclienteNBEAN = new clienteNBEAN();
				   objDistrito = new distritoBEAN();
				   objCliente = new clienteBEAN();
				   
				   objCliente.setIdCliente(c.getInt(0));
				   objCliente.setNombre(c.getString(1));
				    
				   objclienteNBEAN.setDNI(c.getString(2));
				  
				   objCliente.setDireccion(c.getString(3));
				   
				   objDistrito.setIdDistrito(c.getInt(4)); 
				   objCliente.setTelefono(c.getString(5)); 
				   
				   objDistrito.setNombre(c.getString(6)); 
				   
				   
				  
				   
				   objCliente.setDistrito(objDistrito); 
				   
				   objclienteNBEAN.setCelular(c.getString(7));
				   objclienteNBEAN.setCorreo(c.getString(8)); 
				  
				   objCliente.setIdEstado(c.getInt(9));
				   
				   objclienteNBEAN.setCliente(objCliente);
				   
			       lista.add(objclienteNBEAN);
			   }
				c.close();
			} catch (Exception e)
			{			
			} 
			return   lista;
		}	 
	 
	// x.................................................................x
	 
	 public ArrayList<clienteNBEAN>  obtDtsMYSQLclientenat(){
		 
		 JSONObject     jsonobject=null;
		 clienteNBEAN   objclientenat;  
	     clienteBEAN objCliente;
		
		 ArrayList<clienteNBEAN> listaMysql=new ArrayList<clienteNBEAN>();
		 		  	
		 try 
			{				
			      
		     List<NameValuePair>  parametros = new ArrayList<NameValuePair>();		 
		     parametros.add(new BasicNameValuePair("txtid","2"));		

		      
		     ConexionHttpClient objconexion=new   ConexionHttpClient();
		     jsonobject=objconexion.getConexionHttpClientPost(ruta, parametros);
		      
		  	 JSONArray    clientes =jsonobject.getJSONArray("CLINTSNAT");	  
		  	 
				    for(int i = 0; i < clientes.length(); i++) {
				    	
				      JSONObject   jsonCliente = clientes.getJSONObject(i);
				      objclientenat=new clienteNBEAN();
				      objCliente= new clienteBEAN(); 
				      
				      objCliente.setIdCliente(jsonCliente.getInt("idCliente"));
				     
				      objclientenat.setDNI(jsonCliente.getString("DNI")); 
				      objclientenat.setCelular(jsonCliente.getString("Celular")); 
				      objclientenat.setCorreo(jsonCliente.getString("Correo"));  

				      objclientenat.setCliente(objCliente);
				      
				      listaMysql.add(objclientenat);
				    }		     
			                    
			} catch (Exception e) {	 }
			
		 return listaMysql; 
	 }
	 
	// x.................................................................x
		
	 public int Sincronizar(){ 
		 int estado=0;
		 borrTablaclientenat();
		 	
		 ArrayList<clienteNBEAN> listaMysql=obtDtsMYSQLclientenat();
		 clienteNBEAN objclienten= new clienteNBEAN();
		 
	     try{
			 
	    	 for(int i = 0; i < listaMysql.size(); i++){
				 objclienten = listaMysql.get(i);
				  InsrtSoloClienteNatSQLITE( objclienten);
			 }
			  
	    }catch(Exception e){
	    	estado=0;
	    }
		  return estado;
		}

	public void sincronizarMYSQL() {
		//estado 6=borrador es decir se encuentra en SQLITE NO MYSQL
		 ArrayList<clienteNBEAN> listaSQLITE = ListarClientesN(6);
		 
		 clienteNBEAN objclienteNatural= new clienteNBEAN();
		 	     try{
			 
	    	 for(int i = 0; i < listaSQLITE.size(); i++){
	    		 objclienteNatural = listaSQLITE.get(i);
				 InsrtClienteNatMYSQL( objclienteNatural);
			 }
			  
	    }catch(Exception e){
	    	//estado=0;
	    }
	     
	}

	private void InsrtClienteNatMYSQL(clienteNBEAN objclienteNatural) {
		 JSONObject     jsonobject=null; 
		 
		 		  	
		 try 
			{				
			 
			 System.out.println(" INGRESANDO EN MYSQL ENVIANDO");
			 
		     List<NameValuePair>  parametros = new ArrayList<NameValuePair>();		 
		    
		     System.out.println("CODIGO "+ objclienteNatural.getCliente().getIdCliente()+
		    		 "NOMBRE"+ objclienteNatural.getCliente().getNombre()+
		    		 "IDDISTRITO"+objclienteNatural.getCliente().getDistrito().getIdDistrito()+
		    		 "dire"+objclienteNatural.getCliente().getDireccion()+" telef"+objclienteNatural.getCliente().getTelefono()
		    		 +"cel "+objclienteNatural.getCelular()+"DNI"+objclienteNatural.getDNI()+"correo"+objclienteNatural.getCorreo()
		    		 ); 
		    		 
		    		 
		     parametros.add(new BasicNameValuePair("CODIGO", objclienteNatural.getCliente().getIdCliente()+"" ));		
		     parametros.add(new BasicNameValuePair("NOMBRE", objclienteNatural.getCliente().getNombre() ));		
		     parametros.add(new BasicNameValuePair("DIRECCION",objclienteNatural.getCliente().getDireccion()));		
		     parametros.add(new BasicNameValuePair("TELEFONO",objclienteNatural.getCliente().getTelefono()));		
		     parametros.add(new BasicNameValuePair("IDDISTRITO",objclienteNatural.getCliente().getDistrito().getIdDistrito()+"" ));		
		     		
		     parametros.add(new BasicNameValuePair("CELULAR",objclienteNatural.getCelular()));	 
		     parametros.add(new BasicNameValuePair("DNI",objclienteNatural.getDNI()));		
		     parametros.add(new BasicNameValuePair("CORREO",objclienteNatural.getCorreo()));

		      
		     ConexionHttpClient objconexion=new   ConexionHttpClient();
		     jsonobject=objconexion.getConexionHttpClientPost(rutaIngresar, parametros);
		     
		     String estado =jsonobject.getString("estado");
			    
		    System.out.println("Estado => "+estado);
			} catch (Exception e) {	 } 

	}
	 
	 
}

