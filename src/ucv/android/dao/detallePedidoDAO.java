package ucv.android.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
   
import ucv.android.bean.detallePedidoBEAN;
import ucv.android.bean.productoBEAN;
import ucv.android.utils.ConexionHttpClient;
import ucv.android.utils.MySQLiteHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class detallePedidoDAO {
	private SQLiteDatabase  database;
	private MySQLiteHelper  dbhelper;
	
    String  ruta="http://10.0.2.2:8080/PROYECTOMYSQL-SQLITE/CONTROLADOR/DetallePedidoControlador.php";
	String rutaIngresar="http://10.0.2.2:8080/PROYECTOMYSQL-SQLITE/CONTROLADOR/DetallePedidoControladorRegistro.php";
 
	public  detallePedidoDAO(Context  contexto)
		{ dbhelper=new MySQLiteHelper(contexto);
		}
	 
	 public  void open() throws SQLiteException
		{ database=dbhelper.getWritableDatabase();
		}
	 
	 public   void  close()
		{ dbhelper.close();
		}
	 
	 public   long InsrtDetallePEDIDOSQLITE(detallePedidoBEAN  objDetallePedido)
		{	
		   long  i=0;
		   
		   try   
			{  
			
			   System.out.println("INSERTO DTALLE PEDIDO EN SQLITE idPedido =>"+objDetallePedido.getIdpedido()+"  SUTOTAK: "+objDetallePedido.getSubTotal());
			           
			 ContentValues   valores=new ContentValues();

			  valores.put("idPedido",objDetallePedido.getIdpedido());

			  valores.put("idProducto",objDetallePedido.getProducto().getIdProducto());
			  valores.put("Cantidad",objDetallePedido.getCantidad()); 
			  
              valores.put("Precio",objDetallePedido.getPrecio());
              valores.put("SubTotal",objDetallePedido.getSubTotal()); 
              
               	              
              i=database.insert(MySQLiteHelper.TABLADETALLEPEDIDO,null,valores);
	             
			} catch (Exception e) 
			{    i=0;	}
			return i;
		}
	 
	
	 public void borrTablaDetallPedido(){ 
      	dbhelper.borrarTablaDetallePedido(database);
	 }
	 
	 	 
	 public  ArrayList<detallePedidoBEAN>  ListarDetallePedido(int idPedido)
		{	
		    Cursor  c=null;
		    ArrayList<detallePedidoBEAN> lista=new ArrayList<detallePedidoBEAN>(); 
			
		    detallePedidoBEAN objDetallePedido;
		    productoBEAN objProducto; 
		    
		    try 
			  {       
			   c=database.rawQuery("Select dp.idPedido,dp.idProducto,dp.Cantidad,dp.Precio, dp.SubTotal," +
			   		" p.Descripcion, p.CodReferencia " +
			   		" from "+MySQLiteHelper.TABLADETALLEPEDIDO+" dp inner join "+MySQLiteHelper.TABLAPRODUCTO+" p " +
			   		" on dp.idProducto=p.idProducto where dp.idPedido="+idPedido+" ;",null);
			
			   while(c.moveToNext())
			   {
				   objDetallePedido = new detallePedidoBEAN();
				   objProducto = new productoBEAN(); 
				  
				   objDetallePedido.setIdpedido(c.getInt(0)); 
				   
				   objProducto.setIdProducto(c.getInt(1));
				   
				   objDetallePedido.setCantidad(c.getInt(2));
				   objDetallePedido.setPrecio(c.getDouble(3)); 
				   objDetallePedido.setSubTotal(c.getDouble(4));
				    
				   objProducto.setDescripcion(c.getString(5));
				   objProducto.setCodReferencia(c.getString(6));
					   
				   objDetallePedido.setProducto(objProducto);  	
				   
			       lista.add(objDetallePedido);
			       
			   }
				c.close();
			} catch (Exception e)
			{			
			} 
			return   lista;
		}	 
	 
	// x.................................................................x
	 
	 public ArrayList<detallePedidoBEAN>  obtDtsMYSQLDetallePedido(int idPedido){
		 
		 JSONObject  jsonobject=null;
		 
		 detallePedidoBEAN objDetallePedido;
		 productoBEAN objProducto;
	     
		 ArrayList<detallePedidoBEAN> listaMysql=new ArrayList<detallePedidoBEAN>();
		 		  	
		 try 
			{				
			      
		     List<NameValuePair>  parametros = new ArrayList<NameValuePair>();		 
		     parametros.add(new BasicNameValuePair("TXTIdPedido",idPedido+""));		

		      
		     ConexionHttpClient objconexion=new   ConexionHttpClient();
		     jsonobject=objconexion.getConexionHttpClientPost(ruta, parametros);
		      
		     System.out.println("Recibiendo Pedidos MYSQL =>"+idPedido);
				
		  	 JSONArray    clientes =jsonobject.getJSONArray("DETALLEPEDIDOS");	  
		  	 
			
				    for(int i = 0; i < clientes.length(); i++) {
				    	System.out.println(" CANTIDAD DE DETALES =>"+clientes.length());
						
				      JSONObject   jsonCliente = clientes.getJSONObject(i);
				      objDetallePedido = new detallePedidoBEAN();
					  objProducto = new productoBEAN(); 
					  
				       
					   objDetallePedido.setIdpedido(jsonCliente.getInt("idPedido"));
					   
					   objProducto.setIdProducto(jsonCliente.getInt("idProducto"));
					   
					   objDetallePedido.setCantidad(jsonCliente.getInt("Cantidad"));
					   objDetallePedido.setPrecio(jsonCliente.getDouble("Precio"));
					   objDetallePedido.setSubTotal(jsonCliente.getDouble("SubTotal"));
					   
					   objProducto.setDescripcion(jsonCliente.getString("Descripcion"));
					   objProducto.setCodReferencia(jsonCliente.getString("CodReferencia"));
						   
					   objDetallePedido.setProducto(objProducto);  	
					   
                      System.out.println("Detalle recibido MYSQL =>"+idPedido+" -- "+objDetallePedido.getSubTotal());
					   listaMysql.add(objDetallePedido);
				       

				    }		     
			                    
			} catch (Exception e) {	 }
			
		 return listaMysql; 
	 }
	 
	// x.................................................................x
		
	 public int Sincronizar(int idPedido){ 
		 int estado=0;
		 
		 System.out.println("ID PEDIDO=>"+idPedido);
			
		 ArrayList<detallePedidoBEAN> listaMysql=obtDtsMYSQLDetallePedido(idPedido);
		 
		 detallePedidoBEAN objDetallePedido= new detallePedidoBEAN();
		 
	     try{
			 
	    	 for(int i = 0; i < listaMysql.size(); i++){
	    		 
	    		 System.out.println(" TOTAL =>"+listaMysql.size());
	    		 
	    		 objDetallePedido = listaMysql.get(i);
	    		 InsrtDetallePEDIDOSQLITE( objDetallePedido);
			 }
			  
	    }catch(Exception e){
	    	estado=0;
	    }
		  return estado;
		}

	 
	 
	 
	 
	 
	public void  sincronizarMYSQL(int idPedido) {
		String mensaje="";
		ArrayList<detallePedidoBEAN> listaSQLITE = ListarDetallePedido(idPedido);
		detallePedidoBEAN objDetallePedido= new detallePedidoBEAN();
		 try{
			 
	    	 for(int i = 0; i < listaSQLITE.size(); i++){
	    		 objDetallePedido = listaSQLITE.get(i);
	    		 InsrtDetallePEDIDOMYSQL( objDetallePedido);
			 }
			  
	    }catch(Exception e){
	    	//estado=0;
	    }
	    
		 	    // return mensaje;
	}

	public void InsrtDetallePEDIDOMYSQL(detallePedidoBEAN objDetallePedido) {
		 JSONObject     jsonobject=null;   	
		 try 
			{				
			 
			 System.out.println(" MYSQL ENVIANDO DETALLE PEDIDO "+objDetallePedido.getIdpedido());
			 
		     List<NameValuePair>  parametros = new ArrayList<NameValuePair>();		 
		    
		   /*  System.out.println("CODIGO "+ objclienteNatural.getCliente().getIdCliente()+
		    		 "NOMBRE"+ objclienteNatural.getCliente().getNombre()+
		    		 "IDDISTRITO"+objclienteNatural.getCliente().getDistrito().getIdDistrito()
		    		 );*/
		    		 
		    		 
		     parametros.add(new BasicNameValuePair("idPedido", objDetallePedido.getIdpedido()+"" ));		
		     parametros.add(new BasicNameValuePair("idProducto",objDetallePedido.getProducto().getIdProducto()+"" ));		
		     parametros.add(new BasicNameValuePair("Precio",objDetallePedido.getPrecio()+""));		
		     parametros.add(new BasicNameValuePair("Cantidad",objDetallePedido.getCantidad()+""));		
		     parametros.add(new BasicNameValuePair("SubTotal",objDetallePedido.getSubTotal()+"" ));	
		      
		     ConexionHttpClient objconexion=new   ConexionHttpClient();
		     jsonobject=objconexion.getConexionHttpClientPost(rutaIngresar, parametros);
		     
		     String estado =jsonobject.getString("estado");
			    
		    System.out.println("Estado => "+estado);
		     
		    
			} catch (Exception e) {	 } 

	}
	  
	
}
