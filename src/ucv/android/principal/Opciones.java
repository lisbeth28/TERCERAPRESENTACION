package ucv.android.principal;

import java.util.ArrayList;
 
import ucv.android.bean.empleadoBEAN; 
import ucv.android.dao.empleadoDAO;
import ucv.android.dao.sincronizarBD; 
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;  
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class Opciones extends Activity  implements OnItemClickListener {

	private int idUsuario;
	private int idCliente; 
	private int idSincronizacion=0;
	
	private empleadoDAO EmpleadoDAO; 
	private sincronizarBD sincronizarTablas;
	
 	private TextView lblBienvenida;
	private TextView lblTitulo;
	
	private ListView LSTMENU; 
	
	private ArrayList<String> lstOpciones;
	private ArrayAdapter<String> adaptador;
	
	private empleadoBEAN empleadoBEAN;
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.opciones);
		
	    //--------------------------------------------------------------------------/
		LSTMENU=(ListView)findViewById(R.id.LSTOPCIONES);
		LSTMENU.setOnItemClickListener(this);
	     
		lblBienvenida=(TextView)findViewById(R.id.LBLBNBUSUARIO);
		lblTitulo=(TextView)findViewById(R.id.LBLTITULMENU); 
	   //--------------------------------------------------------------------------/
		Typeface font=Typeface.createFromAsset(getAssets(),"Chocolates.ttf");
		lblBienvenida.setTypeface(font);
	
		Typeface font2=Typeface.createFromAsset(getAssets(),"RivannaNF.ttf");
		lblTitulo.setTypeface(font2); 
	   //--------------------------------------------------------------------------/	
		
		lstOpciones=llenarOpciones(); 
		adaptador=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lstOpciones);
		LSTMENU.setAdapter(adaptador);
	  //--------------------------------------------------------------------------/
		 	
		Intent	objIntent=getIntent();
		Bundle	objbundle=objIntent.getExtras();
		if(objbundle!=null) { 
			idUsuario = objbundle.getInt("idUsuario");  
			int id= objbundle.getInt("idSincro");  
			if(id > 0){ idSincronizacion=id; }
		}
     
		//--------------------------------------------------------------------------/
		 	
		sincronizarBD_SQLITE();
	}
 


	private ArrayList<String> llenarOpciones() {
		
		lstOpciones=new ArrayList<String>();
    	    	
		String obj1=new String("   • Registrar Pedido");
    	lstOpciones.add(obj1);
    	
    	String obj2=new String("   • Mantenimiento Cliente");
    	lstOpciones.add(obj2); 
    	
    	String obj7=new String("   • Listar Clientes");
    	lstOpciones.add(obj7); 
    	
    	String obj3=new String("   • Estado Pedidos");
    	lstOpciones.add(obj3);
    	
    	String obj4=new String("   • Catalogo");
    	lstOpciones.add(obj4); 
    	
    	String obj5=new String("   • Regresar");
    	lstOpciones.add(obj5);
    	
    	return lstOpciones;
	}
	
	private void sincronizarBD_SQLITE() {
		
		if(idSincronizacion==0){
			
			
			realizarSincronizacion();
			
		}else {
			confirmarSincronizacion("¿Desea sincronizar denuevo las BD?");
		}
		
		
	}
	
	private void mostrarEmpleado() {
		new asyncBuscarUsuario().execute(); 
		
	}
	
	private void realizarSincronizacion() {
		new asyncSincronizarBD().execute();  
		mostrarEmpleado();
	} 
 
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	    
		switch(arg2)
		{
		
		case 0:
		{
			Intent objIntent=new Intent(Opciones.this,registrarPedido.class);
			Bundle	objbundle=new Bundle();
			objbundle.putSerializable("idEmpleado", empleadoBEAN.getIdEmpleado()); // ??? 
			objbundle.putSerializable("idUsuario", idUsuario); // ??? 
			objbundle.putSerializable("idSincro", idSincronizacion); 
			
			objIntent.putExtras(objbundle);
			startActivity(objIntent);
			
			finish();
			 
			break;
		}
		
		case 1:
		{
			Intent objIntent=new Intent(Opciones.this,mantClienteNatural.class);
			Bundle	objbundle=new Bundle();
			objbundle.putSerializable("idCliente", idCliente); // ???
			objbundle.putSerializable("idUsuario", idUsuario); // ???
			objIntent.putExtras(objbundle);
			startActivity(objIntent);
			
			finish();
			
			break;
		} 
		
		case 2:
		{
			Intent objIntent=new Intent(Opciones.this,listarCliente.class);
			Bundle	objbundle=new Bundle(); 
			objbundle.putSerializable("idUsuario", idUsuario); 
			objbundle.putSerializable("idSincro", idSincronizacion); 
			objIntent.putExtras(objbundle);
			startActivity(objIntent);
			
			finish();
			
			break;
		} 
		
		case 3:
		{
			Intent objIntent=new Intent(Opciones.this,listarPedidos.class);
			Bundle	objbundle=new Bundle();
			objbundle.putSerializable("idCliente", idCliente); // ???
			objbundle.putSerializable("idUsuario", idUsuario); // ???
			objbundle.putSerializable("idSincro", idSincronizacion); 
			objbundle.putSerializable("idEmpleado", empleadoBEAN.getIdEmpleado()); // ???
			objIntent.putExtras(objbundle);
			startActivity(objIntent);
		
			finish();
			
			break;
		}
		
		case 4:
		{
			Intent objIntent=new Intent(Opciones.this,vistaCatalogo.class);
			Bundle	objbundle=new Bundle();
			objbundle.putSerializable("idUsuario", idUsuario);  
			objbundle.putSerializable("idSincro", idSincronizacion); 
			
			objIntent.putExtras(objbundle);
			startActivity(objIntent);
			
			finish();
			
			break;
		}
		
		case 5:
		{
			Intent objIntent=new Intent(Opciones.this,Principal.class);
			Bundle	objbundle=new Bundle();
			objbundle.putSerializable("idUsuario", idUsuario); // ???
			objIntent.putExtras(objbundle);
			startActivity(objIntent);
		
			finish();
			
			break;
		}
		
		}
	}

	class asyncSincronizarBD extends     AsyncTask <Void,Void,Void> 
	 {   
		private ProgressDialog progressDialog; 
		        
        protected void onPreExecute() 
         
         {    progressDialog = ProgressDialog.show( Opciones.this,"Sincronizando BD", " No apage la conexion a intenet ...",true);
     	      sincronizarTablas= new sincronizarBD(getApplicationContext()); 
     	   } 
        
       @Override  
         protected Void doInBackground(Void... arg0) 
         {   
    	    try {    idCliente= sincronizarTablas.efectuarSincronizacionBD();
    	      	    	
    	    } catch (Exception e)  {  	 }
              
    	   return null;  
         }  
       
        @Override  
          protected void onPostExecute(Void result)
         {  
        	if(idCliente!=0){  idSincronizacion=1; }
        	else { 
        	
			  Toast toast2 =   Toast.makeText(getApplicationContext(),
	                  "ERROR AL SINCRONIZAR :Porfavor restablesca la conexion a Internet.", Toast.LENGTH_LONG);
	          toast2.setGravity(Gravity.CENTER|Gravity.LEFT,210,90);
	          toast2.show();
		    } 
        	 progressDialog.dismiss();
        	 
          }
   } 
	
	
	class asyncBuscarUsuario extends     AsyncTask <Void,Void,Void> 
   {   
	   
		
       protected void onPreExecute() 
        {   EmpleadoDAO= new empleadoDAO(getApplicationContext());  } 
       
        @Override  
        protected Void doInBackground(Void... arg0) 
        {   
   	    try {   
   	    	
   	    	EmpleadoDAO.open();
   	   		EmpleadoDAO.Sincronizar(idUsuario);
   	   		
   	   		empleadoBEAN=EmpleadoDAO.datosEmpldSQLITE(idUsuario);
   	      	    	
   	    } catch (Exception e)  {  	 }
            
   	       return null;  
        }  
      
       @Override  
         protected void onPostExecute(Void result)
        {  
    	   if(empleadoBEAN.getNombres()!=null){ 
          		lblBienvenida.setText("USUARIO : '' "+empleadoBEAN.getNombres().toUpperCase()+" "+empleadoBEAN.getApellidos().toUpperCase()+" ''");
          	}
       	 
         }
  } 
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	 
		getMenuInflater().inflate(R.menu.menu_opciones, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	        case R.id.opciones_sincro:
	        {   
	        	sincronizarBD_SQLITE();
	            return true;
	        }
	        case R.id.opciones_regresar:
	        {   
	        	 
	            return true;
	        }
	        default:
	            return super.onOptionsItemSelected(item);
		}
	}
	
	private void confirmarSincronizacion(String consulta) {
	
		
		AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this) ;
        dialogo1.setTitle("Importante");  
        dialogo1.setMessage(consulta);            
        dialogo1.setCancelable(false);  
         
        dialogo1.setNegativeButton(" NO ", new DialogInterface.OnClickListener() {  
            public void onClick(DialogInterface dialogo1, int id) {  
            	
            	dialogo1.cancel(); 
            	mostrarEmpleado();
            }  
        }); 
        
        dialogo1.setPositiveButton(" SI ", new DialogInterface.OnClickListener() {  
            public void onClick(DialogInterface dialogo1, int id) {  
            	realizarSincronizacion();
            } 
        });
        
        
        dialogo1.show();   
	}
	
	

}

