package ucv.android.principal;

import java.io.Serializable;

import ucv.android.bean.clienteBEAN;
import ucv.android.bean.clienteJBEAN;
import ucv.android.bean.distritoBEAN;
import ucv.android.dao.clienteJDAO;
import ucv.android.dao.distritoDAO;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.TextView;

public class mantClienteJuridico extends Activity  {

	private int idUltimoCliente, idUsuario;
	private int idDistrito;
	private distritoDAO distritoDAO;  
	
	private Spinner cmbOpciones;
	private String[] datos ;
	  
	private EditText txtNombre; 
	private EditText txtdireccion;
	private EditText txtRUC;
	private EditText txttelefono; 
	private EditText txtContacto;
 
	private EditText txtURL;
	 	 
	
	private clienteJDAO clientJDAO;
	
	private clienteBEAN objcliente;
	private clienteJBEAN objclientejuridico;
	private distritoBEAN objdistrito;
	
	private TextView lbltitulo;
	private TextView lbltituloRUC;
	private TextView lbltituloRZ;
	private TextView lbltituloDIST;
	private TextView lbltituloDIR;
	private TextView lbltituloTELF;
	private TextView lbltituloPAG; 
	private TextView lbltituloCONTACTO;
	private Serializable idSincronizacion; 
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mantcljuridico);
		
		cmbOpciones = (Spinner)findViewById(R.id.SPNDISTRITOCJ);
		
		txtRUC =(EditText)findViewById(R.id.TXTRUCJ);
		txtNombre =(EditText)findViewById(R.id.TXTRAZCJ); 
		txtContacto =(EditText)findViewById(R.id.TXTCONTACTOCJ); 
		txtdireccion =(EditText)findViewById(R.id.TXTDIRECCIONCJ);
		txtURL =(EditText)findViewById(R.id.TXTPAGINACJ); 
		txttelefono =(EditText)findViewById(R.id.TXTTELEFONOCJ);  
		 
		
		lbltitulo=(TextView)findViewById(R.id.LBLTITULOCLIENTEJURID);
		lbltituloRUC=(TextView)findViewById(R.id.textView2);
		lbltituloRZ=(TextView)findViewById(R.id.textView3);
		lbltituloDIST=(TextView)findViewById(R.id.textView5);
		lbltituloDIR=(TextView)findViewById(R.id.textView4);
		lbltituloTELF=(TextView)findViewById(R.id.textView6);
		lbltituloPAG=(TextView)findViewById(R.id.textView7);
		lbltituloCONTACTO=(TextView)findViewById(R.id.textView8); 
		
	//--------------------------------------------------------------------------/
		lbltitulo.setText("MANTENIMIENTO CLIENTE JURÍDICO");
		Typeface font=Typeface.createFromAsset(getAssets(),"Hey Pretty Girl.ttf");
		lbltitulo.setTypeface(font);
		
		
		Typeface font2=Typeface.createFromAsset(getAssets(),"RivannaNF.ttf");
		lbltituloRUC.setTypeface(font2);
		lbltituloRZ.setTypeface(font2);
		lbltituloDIST.setTypeface(font2);
		lbltituloDIR.setTypeface(font2);
		lbltituloTELF.setTypeface(font2);
		lbltituloPAG.setTypeface(font2);
		lbltituloCONTACTO.setTypeface(font2); 
		 
	 
		
	//--------------------------------------------------------------------------/
			
		
		Intent	objIntent=getIntent();
		Bundle	objbundle=objIntent.getExtras();
		if(objbundle!=null) { 
			idUltimoCliente = objbundle.getInt("idCliente"); 
			idUsuario = objbundle.getInt("idUsuario"); 
			idSincronizacion= objbundle.getInt("idSincro");
		}
		
	//--------------------------------------------------------------------------/
		
		distritoDAO=new distritoDAO(this);
		distritoDAO.open(); // abrir  la conexion a la BD 
	     
	    clientJDAO=new clienteJDAO(this); 
		clientJDAO.open(); 
	//--------------------------------------------------------------------------/
		
	     datos=  distritoDAO.ListarDistritosSQL();
		 clientJDAO.Sincronizar();
	//--------------------------------------------------------------------------/
					 
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

 



	private void registrarClienteJuridico() {
		
		objclientejuridico= new clienteJBEAN();
		objcliente= new clienteBEAN();
		objdistrito= new distritoBEAN();
		
		int idCliente=idUltimoCliente+1;
		
		objdistrito.setIdDistrito(idDistrito);
		  
		objcliente.setIdCliente(idCliente);
		
		objcliente.setNombre(txtNombre.getText().toString());
		objcliente.setDireccion(txtdireccion.getText().toString());
		objcliente.setTelefono(txttelefono.getText().toString());
		
		objcliente.setIdEstado(6);
		
		
		objclientejuridico.setContacto(txtContacto.getText().toString());
		objclientejuridico.setRUC(txtRUC.getText().toString());
		objclientejuridico.setUrl(txtURL.getText().toString()); 
		
		
		objcliente.setDistrito(objdistrito);
		
		objclientejuridico.setCliente(objcliente);
		
		System.out.println(" PARAMEWTROS SALIDOS "+
				objclientejuridico.getCliente().getIdCliente()+" "+
				objclientejuridico.getCliente().getNombre()+" "+
				" idEstado "+objclientejuridico.getCliente().getIdEstado()+" "+
				objclientejuridico.getRUC()+"  IDDISTRITO :"+objcliente.getDistrito().getIdDistrito());
		
		clientJDAO.InsrtClienteJURDSQLITE(objclientejuridico);
		
		
	}


	private void limpiar() {
		
		txtNombre.setText("");
		txtRUC.setText("");
		txtdireccion.setText("");
		txttelefono.setText(""); 
		txtContacto.setText("");
		txtURL.setText(""); 
	
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
	        	mantenimientoClienteNatural();
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
		registrarClienteJuridico();
		limpiar();
	}


	private void regresar() {
 
		clientJDAO.sincronizarMYSQL();
		
		distritoDAO.close();
		clientJDAO.close();
		
		Intent objIntent=new Intent(mantClienteJuridico.this,Opciones.class);
		Bundle	objbundle=new Bundle();
		objbundle.putSerializable("idUsuario",idUsuario); 
		objbundle.putSerializable("idSincro", idSincronizacion); 
		
		objIntent.putExtras(objbundle);
		startActivity(objIntent); 
		finish();
	}


	private void mantenimientoClienteNatural() {
		Intent objIntent=new Intent(mantClienteJuridico.this,mantClienteNatural.class);
		Bundle	objbundle=new Bundle();
		objbundle.putSerializable("idUsuario",idUsuario); 
		objbundle.putSerializable("idSincro", idSincronizacion); 
		objbundle.putSerializable("idCliente", idUltimoCliente); 
		
		objIntent.putExtras(objbundle);
		startActivity(objIntent);
	
		finish();
	}


}
