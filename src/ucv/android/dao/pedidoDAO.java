package ucv.android.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
   
import ucv.android.bean.clienteBEAN; 
import ucv.android.bean.detallePedidoBEAN;
import ucv.android.bean.distritoBEAN;
import ucv.android.bean.pedidoBEAN;
import ucv.android.utils.ConexionHttpClient;
import ucv.android.utils.MySQLiteHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class pedidoDAO {

	private SQLiteDatabase  database;
	private MySQLiteHelper  dbhelper;
	
    String  ruta="http://10.0.2.2:8080/PROYECTOMYSQL-SQLITE/CONTROLADOR/PedidoControlador.php";
	String rutaIngresar="http://10.0.2.2:8080/PROYECTOMYSQL-SQLITE/CONTROLADOR/PedidoControladorRegistro.php";
 
	public  pedidoDAO(Context  contexto)
		{ dbhelper=new MySQLiteHelper(contexto);
		}
	 
	 public  void open() throws SQLiteException
		{ database=dbhelper.getWritableDatabase();
		}
	 
	 public   void  close()
		{ dbhelper.close();
		}
	 
	 public   long InsrtPEDIDOSQLITE(pedidoBEAN  objPedido)
		{	
		   long  i=0;
		   
		   try   
			{  
			   
			 ContentValues   valores=new ContentValues();

			  valores.put("idpedido",objPedido.getIdpedido());

			  valores.put("idEmpleado",objPedido.getIdEmpleado());
			  valores.put("idCliente",objPedido.getCliente().getIdCliente()); 
			 
			  System.out.println("FECHAR : "+objPedido.getFecha());
     	        
              valores.put("Fecha",objPedido.getFecha());
              valores.put("Observaciones",objPedido.getObservaciones());
              valores.put("idEstado",objPedido.getIdEstado()); 
              
              System.out.println("PEDIDO : INSERTO EN SQLITE idEmpleado =>"+objPedido.getIdEmpleado());
	           	              
              i=database.insert(MySQLiteHelper.TABLAPEDIDO,null,valores);
	             
			} catch (Exception e) 
			{ i=0;
			}
			return i;
		}
	 
	
	 public void borrTablaPedido(){ 
      	dbhelper.borrarTablaPedido(database);
	 }
	 
	 	 
	 public  ArrayList<pedidoBEAN>  ListarPedido(int idEmpleado)
		{	
		    Cursor  c=null;
		    ArrayList<pedidoBEAN> lista=new ArrayList<pedidoBEAN>(); 
			
		    clienteBEAN objCliente;
		    distritoBEAN objDistrito;
		    
		    pedidoBEAN objPedido;
		    
		    try 
			  {       
			   c=database.rawQuery("Select p.idpedido,p.Fecha,p.Observaciones,p.idEstado, p.idCliente," +
			   		" c.Nombre, c.Direccion ,c.idDistrito,d.Nombre,c.Telefono" +
			   		" from pedido p inner join cliente c on p.idCliente=c.idCliente inner join " +
			   		" distrito d on c.idDistrito=d.idDistrito where p.idEmpleado="+idEmpleado+" ;",null);
			
			   while(c.moveToNext())
			   {
				   objDistrito = new distritoBEAN();
				   objCliente = new clienteBEAN();
				   objPedido= new pedidoBEAN();
				  
				   objPedido.setIdpedido(c.getInt(0)); 
				   objPedido.setFecha(c.getString(1));
				   objPedido.setObservaciones(c.getString(2));
				   objPedido.setIdEstado(c.getInt(3));
					  
				   objCliente.setIdCliente(c.getInt(4));
				   objCliente.setNombre(c.getString(5)); 
				   objCliente.setDireccion(c.getString(6));
				   
				   objDistrito.setIdDistrito(c.getInt(7));  
				   objDistrito.setNombre(c.getString(8)); 
				   objCliente.setTelefono(c.getString(9)); 
				      
				   objCliente.setDistrito(objDistrito);  
				   objPedido.setCliente(objCliente)	;	
				   
			       lista.add(objPedido);
			       
			   }
				c.close();
			} catch (Exception e)
			{			
			} 
			return   lista;
		}	
	 
	 public  ArrayList<pedidoBEAN>  ListarPedidoBorrador(int idEmpleado)
		{	
		    Cursor  c=null;
		    ArrayList<pedidoBEAN> lista=new ArrayList<pedidoBEAN>(); 
			
		    clienteBEAN objCliente;
		    distritoBEAN objDistrito;
		    
		    pedidoBEAN objPedido;
		    
		    try 
			  {       //MODIFICAR SOLO PEDIDO NO TANTA COSA XD
			   c=database.rawQuery("Select p.idpedido,p.Fecha,p.Observaciones,p.idEstado, p.idCliente," +
			   		" c.Nombre, c.Direccion ,c.idDistrito,d.Nombre,c.Telefono" +
			   		" from pedido p inner join cliente c on p.idCliente=c.idCliente inner join " +
			   		" distrito d on c.idDistrito=d.idDistrito where p.idEmpleado="+idEmpleado+" and p.idEstado=6 ;",null);
			
			   while(c.moveToNext())
			   {
				   objDistrito = new distritoBEAN();
				   objCliente = new clienteBEAN();
				   objPedido= new pedidoBEAN();
				  
				   objPedido.setIdpedido(c.getInt(0)); 
				   objPedido.setFecha(c.getString(1));
				   objPedido.setObservaciones(c.getString(2));
				   objPedido.setIdEstado(c.getInt(3));
				   objPedido.setIdEmpleado(idEmpleado);
					  
				   objCliente.setIdCliente(c.getInt(4));
				   objCliente.setNombre(c.getString(5)); 
				   objCliente.setDireccion(c.getString(6));
				   
				   objDistrito.setIdDistrito(c.getInt(7));  
				   objDistrito.setNombre(c.getString(8)); 
				   objCliente.setTelefono(c.getString(9)); 
				      
				   objCliente.setDistrito(objDistrito);  
				   objPedido.setCliente(objCliente)	;	
				   
			       lista.add(objPedido);
			       
			   }
				c.close();
			} catch (Exception e)
			{			
			} 
			return   lista;
		}
	 
	// x.................................................................x
	 
	 public ArrayList<pedidoBEAN>  obtDtsMYSQLpedido(int idEmpleado){
		 
		 JSONObject  jsonobject=null;
		 pedidoBEAN  objPedido;  
	     clienteBEAN objCliente;
		 distritoBEAN objDistrito;
	     
		 ArrayList<pedidoBEAN> listaMysql=new ArrayList<pedidoBEAN>();
		 		  	
		 try 
			{				
			      
		     List<NameValuePair>  parametros = new ArrayList<NameValuePair>();		 
		     parametros.add(new BasicNameValuePair("idEmpleado",idEmpleado+""));		

		      
		     ConexionHttpClient objconexion=new   ConexionHttpClient();
		     jsonobject=objconexion.getConexionHttpClientPost(ruta, parametros);
		      
		    // System.out.println("Recibiendo Pedidos MYSQL =>"+idEmpleado);
				
		  	 JSONArray    clientes =jsonobject.getJSONArray("PEDIDOS");	  
		  	
			
				    for(int i = 0; i < clientes.length(); i++) {
				    	System.out.println("AKIII 2=>"+clientes.length());
						
				      JSONObject   jsonCliente = clientes.getJSONObject(i);
				      objPedido=new pedidoBEAN();
				      objCliente= new clienteBEAN(); 
				      objDistrito= new distritoBEAN();
				      
				      
				      objPedido.setIdEmpleado(idEmpleado);
				      
				      objPedido.setIdpedido(jsonCliente.getInt("idpedido"));
				      //System.out.println(" IDPEDIDO  =>"+" -- "+objPedido.getIdpedido());
 
				      objCliente.setIdCliente(jsonCliente.getInt("idCliente"));
				     
				      //System.out.println(" IDCLIENTE  =>"+" -- "+objCliente.getIdCliente());

				      objPedido.setFecha(jsonCliente.getString("Fecha"));
				      
				      //System.out.println(" Fecha  =>"+" -- "+objPedido.getFecha());

				      objPedido.setObservaciones(jsonCliente.getString("Observaciones"));
				      objPedido.setIdEstado(jsonCliente.getInt("idEstado"));
				      
				      
				      objCliente.setNombre(jsonCliente.getString("Nombre")); 
				      objCliente.setDireccion(jsonCliente.getString("Direccion")); 
				      
				      objDistrito.setIdDistrito(jsonCliente.getInt("idDistrito")); 
				     
				      objCliente.setTelefono(jsonCliente.getString("Telefono")); 
				     
				      objDistrito.setNombre(jsonCliente.getString("distrit")); 

				      objCliente.setDistrito(objDistrito);
				      objPedido.setCliente(objCliente);
				      
				      //System.out.println("Recibiendo Pedidos MYSQL =>"+idEmpleado+" -- "+objPedido.getIdEmpleado());

				      listaMysql.add(objPedido);
				    }		     
			                    
			} catch (Exception e) {	 }
			
		 return listaMysql; 
	 }
	 
	// x.................................................................x
		
	 public int SincronizarPedidosyDetalle(int idEmpleado,Context Contexto){ 
		 int idPedido=0;
		 detallePedidoDAO DetallessDAO;
		 
		 DetallessDAO = new detallePedidoDAO(Contexto);
		 DetallessDAO.open();
		 
		 System.out.println("SINCRONIZAND PEDIDO Y DETALLE");
		 
		 borrTablaPedido();
		 DetallessDAO.borrTablaDetallPedido();
		 
		
			
		 ArrayList<pedidoBEAN> listaMysql=obtDtsMYSQLpedido(idEmpleado);
		 
		 pedidoBEAN objPedido= new pedidoBEAN();
		 
		 System.out.println(" Datos MYSQL DEL EMPLEADO =>"+idEmpleado+" "+listaMysql.size());
	     try{
			 
	    	 for(int i = 0; i < listaMysql.size(); i++){
	    		  objPedido = listaMysql.get(i);
	    		  
				  InsrtPEDIDOSQLITE( objPedido);
				  
				  System.out.println(" OBTENR DETALLE =>"+objPedido.getIdpedido());
				    
				  DetallessDAO.Sincronizar(objPedido.getIdpedido());
				  
				  idPedido=objPedido.getIdpedido();
			 }
	    	 
	    	 DetallessDAO.close();
			  
	    }catch(Exception e){
	    	idPedido=0;
	    }
		  return idPedido;
		}

	 
	 
	 
	 
	 
	public void  sincronizarMYSQL(int idEmpleado,Context Contexto) {
		String mensaje="";
        detallePedidoDAO DetallessDAO;
		 
		 DetallessDAO = new detallePedidoDAO(Contexto);
		 DetallessDAO.open();
		 
	 	 ArrayList<pedidoBEAN> listaSQLITE = ListarPedidoBorrador(idEmpleado);
		 System.out.println("enviando PEDIDO => "+listaSQLITE.size());
		   
		 ArrayList<detallePedidoBEAN>  listaDetalle = new   ArrayList<detallePedidoBEAN>(); 
		
		 pedidoBEAN objPedido= new pedidoBEAN();
		 
		 try{
			 
	    	 for(int i = 0; i < listaSQLITE.size(); i++){
	    		 objPedido = listaSQLITE.get(i); 
				 InsrtPEDIDOMYSQL( objPedido);
				 
				 listaDetalle=DetallessDAO.ListarDetallePedido(objPedido.getIdpedido());
				 
				 for(int j = 0; j < listaDetalle.size();j++){
					 
					 DetallessDAO.InsrtDetallePEDIDOMYSQL(listaDetalle.get(i));
					 
				 }
				 
			 }
			  
	    }catch(Exception e){
	    	 
	    }
	     
		 	   
	}

	private void InsrtPEDIDOMYSQL(pedidoBEAN objPedido) {
		 JSONObject     jsonobject=null; 
		  	  	
		 try 
			{				
			
			 System.out.println(" MYSQL ENVIANDO PEDIDO .......");
			 
		     List<NameValuePair>  parametros = new ArrayList<NameValuePair>();		 
		   
		    		 
		     parametros.add(new BasicNameValuePair("idpedido", String.valueOf(objPedido.getIdpedido()))) ;		
		     parametros.add(new BasicNameValuePair("idEmpleado", String.valueOf(objPedido.getIdEmpleado())));		
		     parametros.add(new BasicNameValuePair("idCliente", String.valueOf(objPedido.getCliente().getIdCliente())));		
		     parametros.add(new BasicNameValuePair("Fecha",objPedido.getFecha()));		
		     parametros.add(new BasicNameValuePair("Observaciones",objPedido.getObservaciones()));		
		     		      
		     ConexionHttpClient objconexion=new   ConexionHttpClient();
		     jsonobject=objconexion.getConexionHttpClientPost(rutaIngresar, parametros);
		     
		     String estado =jsonobject.getString("estado");
			    
		    System.out.println("Estado => "+estado);
		   
		    
			} catch (Exception e) {	 } 

	}
	 
}
