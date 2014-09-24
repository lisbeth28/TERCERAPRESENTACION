package ucv.android.principal;

import ucv.android.bean.clienteBEAN;
import ucv.android.bean.clienteNBEAN;
import ucv.android.bean.distritoBEAN;
import ucv.android.dao.clienteNDAO;
import ucv.android.dao.distritoDAO;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.R.id;

public class mantClienteNatural extends Activity  {

	private int idUltimoCliente, idUsuario;
	
	private distritoDAO distritoDAO;  
	
	private Spinner cmbOpciones;
	private String[] datos ;
	  
	private EditText txtNombre; 
	private EditText txtdireccion;
	private EditText txttelefono; 
	private EditText txtcelular;
	private int idDistrito;
	private EditText txtdni;
	private EditText txtcorreo;
	 	
	private Button btnInsertar;
	private Button btnActualizar;
	private Button btnEliminar; 
	private Button btnSalir;
	
	private clienteNDAO clientNDAO;
	
	private clienteBEAN objcliente;
	private clienteNBEAN objclientenatural;
	private distritoBEAN objdistrito;

	private int idSincronizacion;
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mantclnatural);
		
		//***********************************************/
		 distritoDAO=new distritoDAO(this);
		 distritoDAO.open(); // abrir  la conexion a la BD 
      
		 clientNDAO=new clienteNDAO(this); 
		 clientNDAO.open();
       //***********************************************/ 
		
		Intent	objIntent=getIntent();
		Bundle	objbundle=objIntent.getExtras();
		if(objbundle!=null) { 
			idUltimoCliente = objbundle.getInt("idCliente"); 
			idUsuario = objbundle.getInt("idUsuario"); 
			idSincronizacion= objbundle.getInt("idSincro");
		}
				
		cmbOpciones = (Spinner)findViewById(R.id.spnDistritos);
		
		txtdni =(EditText)findViewById(R.id.TXTDNI);
		txtNombre =(EditText)findViewById(R.id.TXTNOMBRECN); 
		txtdireccion =(EditText)findViewById(R.id.TXTDIRECCIONCN);
		txtcelular =(EditText)findViewById(R.id.TXTCELULARCN); 
		txttelefono =(EditText)findViewById(R.id.TXTELEFONOCN); 
		txtcorreo=(EditText)findViewById(R.id.TXTCORREOCN);
		 
		
		 
		
		/********** SINCRONIZAR  **********/
		 datos=  distritoDAO.ListarDistritosSQL();
		 clientNDAO.Sincronizar();
		/********** ............ **********/
		  
		
		ArrayAdapter<String> adaptador =  new ArrayAdapter<String>(this,
	                android.R.layout.simple_spinner_item, datos);
	        
	     adaptador.setDropDownViewResource(
	                android.R.layout.simple_spinner_dropdown_item);
	         
	     cmbOpciones.setAdapter(adaptador);
	     
	     cmbOpciones.setOnItemSelectedListener(
	         	new AdapterView.OnItemSelectedListener() {
	                 public void onItemSelected(AdapterView<?> parent,
	                     android.view.View v, int position, long id) {
	                	  String nombre=datos[position];
	             		  idDistrito=distritoDAO .obtenerIdDistrito(nombre);
	             		  // System.out.println("Seleccionado: " + distritoDAO .obtenerIdDistrito(nombre)+" "+datos[position]);
	  	              }
	          
	                 public void onNothingSelected(AdapterView<?> parent) {  }
	         });

	        
		
		}
		
	

	private void limpiar() {
		
		txtNombre.setText("");
		txtdireccion.setText("");
		txttelefono.setText(""); 
		txtcelular.setText("");
		txtdni.setText("");
		txtcorreo.setText("");
	
	}





	private void registrarClienteNatural() {
	
		objclientenatural= new clienteNBEAN();
		objcliente= new clienteBEAN();
		objdistrito= new distritoBEAN();
		
		idUltimoCliente=idUltimoCliente+1;
		
		int idCliente=idUltimoCliente;
		
		objdistrito.setIdDistrito(idDistrito);
		 
		
		objcliente.setIdCliente(idCliente);
		
		objcliente.setNombre(txtNombre.getText().toString());
		objcliente.setDireccion(txtdireccion.getText().toString());
		objcliente.setTelefono(txttelefono.getText().toString());
		
		objclientenatural.setCorreo(txtcorreo.getText().toString());
		objclientenatural.setDNI(txtdni.getText().toString());
		objclientenatural.setCelular(txtcelular.getText().toString());
		
		objcliente.setIdEstado(6);
		
		objcliente.setDistrito(objdistrito);
		
		objclientenatural.setCliente(objcliente);
		
	 
		
		clientNDAO.InsrtClienteNatSQLITE(objclientenatural);
		
		
	}
 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	 
		getMenuInflater().inflate(R.menu.menu2, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) {
	        case R.id.opcion_cj:
	        {   
	        	mantenimientoClienteJuridicos();
	            return true;
	        }
	        case R.id.opcion_regresar:
	        {   
	        	regresar();
	            return true;
	        }
	        case R.id.opcion_guardar:
	        {   
	        	guardar();
	            return true;
	        }
	        default:
	            return super.onOptionsItemSelected(item);
		}
	}





	private void guardar() {
		 String  correo=txtcorreo.getText().toString();
		 
		 if(correo.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+") && correo.length() > 0)
		 { 
			 registrarClienteNatural();limpiar();
			 
		 }else
		 {		 Toast.makeText(this,"correo invalido)", 
	             Toast.LENGTH_LONG).show();
		          txtcorreo.setText("");
		 }
		
		
	}





	private void regresar()   {
			// TODO Auto-generated method stub
			Intent objIntent=new Intent(mantClienteNatural.this,Opciones.class);
			Bundle	objbundle=new Bundle();
			objbundle.putSerializable("idUsuario",idUsuario); 
			objbundle.putSerializable("idSincro", 1); 
			
            clientNDAO.sincronizarMYSQL();
			
			clientNDAO.close();
			distritoDAO.close();
			
			objIntent.putExtras(objbundle);
			startActivity(objIntent); 
			finish();
		}


		private void mantenimientoClienteJuridicos() {
			// TODO Auto-generated method stub
			Intent objIntent=new Intent(mantClienteNatural.this,mantClienteJuridico.class);
			Bundle	objbundle=new Bundle();
			objbundle.putSerializable("idUsuario",idUsuario); 
			objbundle.putSerializable("idSincro", idSincronizacion); 
			objbundle.putSerializable("idCliente", idUltimoCliente); 
			
			objIntent.putExtras(objbundle);
			startActivity(objIntent);
		
			finish();
		}
	
}
