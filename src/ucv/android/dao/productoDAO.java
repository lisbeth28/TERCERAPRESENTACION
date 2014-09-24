package ucv.android.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject; 

import ucv.android.bean.empleadoBEAN;
import ucv.android.bean.lineaPrincipal;
import ucv.android.bean.marcaBEAN;
import ucv.android.bean.productoBEAN;
import ucv.android.bean.unidadBEAN;
import ucv.android.utils.ConexionHttpClient;
import ucv.android.utils.Detector;
import ucv.android.utils.MySQLiteHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Base64;

public class productoDAO {
	private SQLiteDatabase  database;
	private MySQLiteHelper  dbhelper;
	
    String  ruta="http://10.0.2.2:8080/PROYECTOMYSQL-SQLITE/CONTROLADOR/ProductoControlador.php";
	private Detector objDetector;
	private Context Contexto;
	private lineaPrincipalDAO lineasPrincipales;
	private marcaDAO marcas;
	private unidadmedidaDAO unidades;
 
	public  productoDAO(Context  contexto)
		{ dbhelper=new MySQLiteHelper(contexto);
		  Contexto=contexto;
		}
	 
	 public  void open() throws SQLiteException
		{ database=dbhelper.getWritableDatabase();
		}
	 
	 public   void  close()
		{ dbhelper.close();
		}
	 
	 public   long InsrtProductoSQLITE(productoBEAN  objProducto)
		{	
		 long  i=0;
			try   
			{    
				  System.out.println(" INGRESO A SQLITE =>> idProducto"+objProducto.getIdProducto()+"  - "
			      +"idLineaPrincipal"+objProducto.getLineaPrincipal().getIdLineaPrincipal()+"  - "
				 +"idUnidadMedida"+objProducto.getUnidad().getIdUnidadMedida()+"  - "

			      +"idMarca"+objProducto.getMarca().getIdMarca()+"  - "
			      +"idEstado"+objProducto.getIdEstado()+" COD "+objProducto.getCodReferencia()
			      );
				  
				  ContentValues   valores=new ContentValues();
				  valores.put("idProducto",objProducto.getIdProducto());
		          valores.put("idLineaPrincipal",objProducto.getLineaPrincipal().getIdLineaPrincipal());
	              valores.put("idUnidadMedida",objProducto.getUnidad().getIdUnidadMedida());
				  valores.put("idMarca",objProducto.getMarca().getIdMarca());
		          valores.put("Descripcion",objProducto.getDescripcion());
		          
		          valores.put("Imagen",objProducto.getImagen()); 
		           
	              valores.put("CodReferencia",objProducto.getCodReferencia()); 
	              valores.put("Caracteristicas",objProducto.getCaracteristicas());
				  valores.put("PrecioVenta",objProducto.getPrecioVenta());
		          valores.put("Stock",objProducto.getStock());
                  valores.put("idEstado",objProducto.getIdEstado());
	              
	              i=database.insert(MySQLiteHelper.TABLAPRODUCTO,null,valores);
	             
	             
			} catch (Exception e) 
			{ i=0;
			}
			return i;
		}
	 
	 public void borrTablaProducto(){ 
        dbhelper.borrarTablaProducto(database);
	 }
	 	
	 public   ArrayList<productoBEAN> listadoSQLITE()
		{	
		 
		 Cursor  c=null;
		 ArrayList<productoBEAN> listaSQLITE=new ArrayList<productoBEAN>();
		 productoBEAN objProducto ;
		 unidadBEAN objUnidad;
		 marcaBEAN objMarca;
		 lineaPrincipal objLPrincipal;
		    try 
			  {       
		    	String sql="Select p.idProducto,p.CodReferencia,p.Descripcion,p.Caracteristicas,p.Imagen,p.Stock," +
		    			"p.PrecioVenta,u.Unidad ,lp.Nombre,m.Nombre  from  "+MySQLiteHelper.TABLAPRODUCTO+" p " +
				   		" inner join "+MySQLiteHelper.TABLAUNIDADMEDIDA+" u on p.idUnidadMedida=u.idUnidadMedida " +
				   		" inner join "+MySQLiteHelper.TABLAMARCA+" m on p.idMarca=m.idMarca " +
				   		" inner join "+MySQLiteHelper.TABLALINEAPRINCIPAL+" lp on p.idLineaPrincipal=lp.idLineaPrincipal ;";
				   
			   c=database.rawQuery(sql,null);
			   while(c.moveToNext())
			   {
				   objProducto = new productoBEAN();
				   objUnidad = new unidadBEAN();
				   objMarca = new marcaBEAN();
				   objLPrincipal= new lineaPrincipal();
				   
				   objProducto.setIdProducto(c.getInt(0)); 
				   objProducto.setCodReferencia(c.getString(1));
				   objProducto.setDescripcion(c.getString(2));
				   objProducto.setCaracteristicas(c.getString(3));
				   objProducto.setImagen(c.getBlob(4));
				   objProducto.setStock(c.getInt(5));
				   objProducto.setPrecioVenta(c.getDouble(6));
				   
				   objUnidad.setUnidad(c.getString(7));
				   objMarca.setNombre(c.getString(8));
				   objLPrincipal.setNombre(c.getString(9));
				   
				   objProducto.setUnidad(objUnidad);
				   objProducto.setMarca(objMarca);
				   objProducto.setLineaPrincipal(objLPrincipal);
				   
				   listaSQLITE.add(objProducto);
			   }
				c.close();
			} catch (Exception e)
			{			
			}
			return listaSQLITE;
		}
	 
	 public   ArrayList<productoBEAN> listadoSQLITE(String Descripcion)
		{	
		 
		 Cursor  c=null;
		 ArrayList<productoBEAN> listaSQLITE=new ArrayList<productoBEAN>();
		 productoBEAN objProducto ;
		 unidadBEAN objUnidad;
		 marcaBEAN objMarca;
		 lineaPrincipal objLPrincipal;
		 
		    try 
			  {     
		    	String sql="Select p.idProducto,p.CodReferencia,p.Descripcion,p.Caracteristicas,p.Imagen,p.Stock," +
		    			"p.PrecioVenta,u.Unidad ,lp.Nombre,m.Nombre  from  "+MySQLiteHelper.TABLAPRODUCTO+" p " +
				   		" inner join "+MySQLiteHelper.TABLAUNIDADMEDIDA+" u on p.idUnidadMedida=u.idUnidadMedida " +
				   		" inner join "+MySQLiteHelper.TABLAMARCA+" m on p.idMarca=m.idMarca " +
				   		" inner join "+MySQLiteHelper.TABLALINEAPRINCIPAL+" lp on p.idLineaPrincipal=lp.idLineaPrincipal " +
				   		"where Descripcion like '%"+Descripcion+"%';";
				   
			   c=database.rawQuery(sql,null);
			   while(c.moveToNext())
			   {
				   objProducto = new productoBEAN();
				   objUnidad = new unidadBEAN();
				   objMarca = new marcaBEAN();
				   objLPrincipal= new lineaPrincipal();
				   
				   objProducto.setIdProducto(c.getInt(0)); 
				   objProducto.setCodReferencia(c.getString(1));
				   objProducto.setDescripcion(c.getString(2));
				   objProducto.setCaracteristicas(c.getString(3));
				   objProducto.setImagen(c.getBlob(4));
				   objProducto.setStock(c.getInt(5));
				   objProducto.setPrecioVenta(c.getDouble(6));
				   
				   objUnidad.setUnidad(c.getString(7));
				   objMarca.setNombre(c.getString(8));
				   objLPrincipal.setNombre(c.getString(9));
				   
				   objProducto.setUnidad(objUnidad);
				   objProducto.setMarca(objMarca);
				   objProducto.setLineaPrincipal(objLPrincipal);
				   
				   listaSQLITE.add(objProducto);
			   }
				c.close();
			} catch (Exception e)
			{			
			}
			return listaSQLITE;
		}
	 
	 public productoBEAN buscarProducto(String codReferencia){
		 productoBEAN productoE= new productoBEAN();
		 unidadBEAN unidad= new unidadBEAN();
		 Cursor  c=null;
		   try 
			  {  
			   String sql="Select p.idProducto,p.idUnidadMedida,p.Stock,p.PrecioVenta,u.Unidad  from  "+MySQLiteHelper.TABLAPRODUCTO+" p " +
			   		" inner join "+MySQLiteHelper.TABLAUNIDADMEDIDA+" u on p.idUnidadMedida=u.idUnidadMedida  where CodReferencia ='"+codReferencia+"';";
			   c=database.rawQuery(sql,null);
			   System.out.println(sql);
					   while(c.moveToNext())
			   { 
				   productoE.setIdProducto(c.getInt(0));
				   
				   unidad.setIdUnidadMedida(c.getInt(1)); 
				   
				   productoE.setStock(c.getInt(2));  
				   productoE.setPrecioVenta(c.getDouble(3));  
				  
				   unidad.setUnidad(c.getString(4));  
				   
				   productoE.setCodReferencia(codReferencia); 
				   
				   productoE.setUnidad(unidad);
				   
				   System.out.println(productoE.getIdProducto()+" ID");
				   System.out.println(productoE.getStock()+" stok");
				   System.out.println(productoE.getUnidad().getUnidad()+" unis");
				   
			   }
			   c.close();
			   
			  }catch(Exception e){
				  System.out.println("  eerrr"+e);
				  
			  }
		   
				
				
		 return productoE;
	 }
	 
	 // x.................................................................x
	 

	 public ArrayList<productoBEAN> obtenerListaMysql(){
		 JSONObject     jsonobject=null;
		 ArrayList<productoBEAN> listaMysql=new ArrayList<productoBEAN>();
		 productoBEAN objProducto;
		 marcaBEAN objMarca;
		 lineaPrincipal objLineaPrincipal;
		 unidadBEAN objUnidad;
		 String data;
		 
		 try 
			{				
			      
		     List<NameValuePair>  parametros = new ArrayList<NameValuePair>();		 
		     parametros.add(new BasicNameValuePair("TXTCODIGO", "1"));		

		      
		     ConexionHttpClient objconexion=new   ConexionHttpClient();
		     jsonobject=objconexion.getConexionHttpClientPost(ruta, parametros);
		      
		  	 JSONArray    producto =jsonobject.getJSONArray("PRODUCTOS");	  
		  	
				    for(int i = 0; i < producto.length(); i++) 
				    {
				      JSONObject   jsonProducto = producto.getJSONObject(i);
				      objProducto=new productoBEAN();
				     
				      objMarca=new marcaBEAN();
				      objLineaPrincipal=new lineaPrincipal();
				      objUnidad=new unidadBEAN();
				      
				      objProducto.setIdProducto(jsonProducto.getInt("idProducto"));

				      objUnidad.setIdUnidadMedida(jsonProducto.getInt("idUnidadMedida"));
				      objLineaPrincipal.setIdLineaPrincipal(jsonProducto.getInt("idLineaPrincipal"));
				      
				     
				      objMarca.setIdMarca(jsonProducto.getInt("idMarca"));
				      objProducto.setDescripcion(jsonProducto.getString("Descripcion"));
				      
				       
                      data =(jsonProducto.getString("Imagen")); 
				      byte[] byteData = Base64.decode(data, Base64.DEFAULT);
				      objProducto.setImagen(byteData);
				      
				      objProducto.setCodReferencia(jsonProducto.getString("CodReferencia"));
				      objProducto.setCaracteristicas(jsonProducto.getString("Caracteristicas"));
				      objProducto.setPrecioVenta(jsonProducto.getDouble("PrecioVenta"));
				      objProducto.setStock(jsonProducto.getInt("Stock"));
				      objProducto.setIdEstado(jsonProducto.getInt("idEstado"));
				    		
				      objProducto.setMarca(objMarca);
				      objProducto.setLineaPrincipal(objLineaPrincipal);
				      objProducto.setUnidad(objUnidad);
				      
				      
				      System.out.println(" OBTUBE DE MYSQL =>> idProducto"+objProducto.getIdProducto()+"  - "
						      +"idLineaPrincipal"+objProducto.getLineaPrincipal().getIdLineaPrincipal()+"  - "
							 +"idUnidadMedida"+objProducto.getUnidad().getIdUnidadMedida()+"  - "

						      +"idMarca"+objProducto.getMarca().getIdMarca()+"  - "
						      +"idEstado"+objProducto.getIdEstado());
							  
				      
				      listaMysql.add(objProducto);					      
				    }		     
			 
			} catch (Exception e) 
			{			
			}
			
		 return listaMysql; 
	 }	 
	// x.................................................................x
		
	 public int Sincronizar(   ){
		  int estadoRed=comprobarConexion(Contexto);
		    
		  if(estadoRed==1){
		    	
		    	 lineasPrincipales=new lineaPrincipalDAO(Contexto);
		 		 marcas=new marcaDAO(Contexto); 
		 		 unidades=new unidadmedidaDAO(Contexto);
		 		
		 		lineasPrincipales.open();
				marcas.open();
				unidades.open();
				
		 		 borrTablaProducto();
		 		 
		 		lineasPrincipales.Sincronizar();
		 		marcas.Sincronizar();
		 		unidades.Sincronizar();
		 		
				 ArrayList<productoBEAN> listaMysql=obtenerListaMysql();
			     productoBEAN objProducto= new productoBEAN();
			     try{
					 for(int i = 0; i < listaMysql.size(); i++){
						 
						 objProducto = listaMysql.get(i);
						 InsrtProductoSQLITE( objProducto);
						 
				     }
			     }catch(Exception e){  }
			      
			     lineasPrincipales.close();
				 marcas.close();
                 unidades.close();
                 
			}else{ }
			 
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
	


