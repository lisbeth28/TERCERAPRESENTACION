package ucv.android.dao;

import ucv.android.utils.Detector;
import android.content.Context;

public class sincronizarBD {

	Detector objDetector;
	 	
	clienteDAO cliente;
	clienteJDAO clienteJuridico;
	clienteNDAO clienteNatural;
	distritoDAO distritos;
	 
	productoDAO productos; 
	pedidoDAO pedidoBD;
	
	
	public  sincronizarBD(Context  contexto)
	{ 
		
		objDetector = new Detector(contexto);
		 
		
		distritos=new distritoDAO(contexto);
		cliente=new clienteDAO(contexto);
		clienteJuridico=new clienteJDAO(contexto);
		clienteNatural=new clienteNDAO(contexto);
		 
		productos=new productoDAO(contexto); 
		
	}
	
	public int efectuarSincronizacionBD(){
		
		int estadoRed=comprobarConexion();
		int idCliente=0;
		abrirConexiones();
		
		if(estadoRed==1){
			 			
			distritos.Sincronizar();
			idCliente=cliente.Sincronizar();
			clienteJuridico.Sincronizar();
			clienteNatural.Sincronizar();
			 
			productos.Sincronizar();
			 
			
			 cerrarConexiones();
			 
			return idCliente;
		}
		else{ 
			 cerrarConexiones();
			 return idCliente;
			}
	}
	
public void efectuarSincronizacionMysql(Context Contexto,int idEmpleado){
		
		int estadoRed=comprobarConexion();
	 
		abrirConexiones();
		
		if(estadoRed==1){
			 			
			clienteJuridico.open();
			clienteNatural.open();
			pedidoBD.open();
			
			clienteJuridico.sincronizarMYSQL();
			clienteNatural.sincronizarMYSQL();
			
			pedidoBD.sincronizarMYSQL(idEmpleado, Contexto);
			
			clienteJuridico.close();
			clienteNatural.close();
			pedidoBD.close();
			 
			 
		}
		else{ 
			 cerrarConexiones(); 
			}
	}
	
   private int comprobarConexion() {
		
		Boolean hayInt = false;
		 
	    hayInt = objDetector.estasConectado();
		
	    if(hayInt){ return 1; }
		 
		else{  return 0;  }
	
		
	}
   
   private void abrirConexiones(){
			
		distritos.open();
		cliente.open();
		clienteJuridico.open();
		clienteNatural.open();
		
		
		productos.open();
		
 }
	
   private void cerrarConexiones(){
	    
		distritos.close();
		cliente.close();
		clienteJuridico.close();
		clienteNatural.close();
		 
		productos.close();
   }
   
}

