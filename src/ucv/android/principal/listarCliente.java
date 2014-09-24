package ucv.android.principal;

import java.util.ArrayList;
 
import ucv.android.bean.clienteNBEAN;  
import ucv.android.dao.clienteNDAO;
import ucv.android.utils.AdapterClienteNatural;
import android.app.Activity; 
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView; 
import android.widget.TextView;

public class listarCliente extends Activity {
 
	private TextView lblTitulo;
	private int idSincronizacion=0;
	
	private ListView listView;
	private ArrayList<clienteNBEAN> listaContactos;

	// Creamos un adapter personalizado
	
	private AdapterClienteNatural adapter;
	private clienteNDAO contactoSQLITE;

	private int idUsuario;
	
	

	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_clientes_naturals); 

		contactoSQLITE= new clienteNDAO(this);
		contactoSQLITE.open();
	 
		listView = (ListView) findViewById(R.id.LSTCLIENTESNT);
		lblTitulo = (TextView) findViewById(R.id.LBLTITULOLISTACLIENTN);
		
		Typeface font2=Typeface.createFromAsset(getAssets(),"Hey Pretty Girl.ttf");
		lblTitulo.setTypeface(font2);
		
		listaContactos = new ArrayList<clienteNBEAN>();
		
		listaContactos=contactoSQLITE.ListarClientesN(1);
		
		adapter = new AdapterClienteNatural(this, listaContactos);
	    listView.setAdapter(adapter);
	    
	    Intent	objIntent=getIntent();
		Bundle	objbundle=objIntent.getExtras();
		if(objbundle!=null) { 
			idUsuario = objbundle.getInt("idUsuario");
			idSincronizacion= objbundle.getInt("idSincro");
		}
     
		//--------------------------------------------------------------------------/
		 	
		
		
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
		        	listarClientesJuridicos();
		            return true;
		        }
		        case R.id.opcion_regresar:
		        {   
		        	regresar();
		            return true;
		        }
		        default:
		            return super.onOptionsItemSelected(item);
			}
		}


		private void regresar() {
			// TODO Auto-generated method stub
			Intent objIntent=new Intent(listarCliente.this,Opciones.class);
			Bundle	objbundle=new Bundle();
			objbundle.putSerializable("idUsuario",idUsuario); 
			objbundle.putSerializable("idSincro", idSincronizacion); 
			
			objIntent.putExtras(objbundle);
			startActivity(objIntent); 
			finish();
		}


		private void listarClientesJuridicos() {
			// TODO Auto-generated method stub
			Intent objIntent=new Intent(listarCliente.this,listarClienteJuridico.class);
			Bundle	objbundle=new Bundle();
			objbundle.putSerializable("idUsuario",idUsuario); 
			objIntent.putExtras(objbundle);
			startActivity(objIntent);
		
			finish();
		}
		

	

	}
