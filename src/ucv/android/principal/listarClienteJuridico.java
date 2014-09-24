package ucv.android.principal;

import java.util.ArrayList;

import ucv.android.bean.clienteJBEAN;
import ucv.android.dao.clienteJDAO;
import ucv.android.utils.AdapterClienteJuridico;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class listarClienteJuridico extends Activity {
	
	private ListView listView;
	private ArrayList<clienteJBEAN> listaContactos;
	private TextView lblTitulo;
	
	// Creamos un adapter personalizado
	
	private AdapterClienteJuridico adapter;
	private clienteJDAO contactoSQLITE;
	private int idUsuario; 
	

	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_clientes_juridicos); 

		contactoSQLITE= new clienteJDAO(this);
		contactoSQLITE.open();
		
		lblTitulo = (TextView) findViewById(R.id.LBLTITULOLISTACLIENTJD);
		
		listView = (ListView) findViewById(R.id.LSTCLIENTESJD);
		Typeface font2=Typeface.createFromAsset(getAssets(),"Hey Pretty Girl.ttf");
		lblTitulo.setTypeface(font2);
		
		listaContactos = new ArrayList<clienteJBEAN>();
		
		listaContactos=contactoSQLITE.ListarClientesJ(1);
		
		adapter = new AdapterClienteJuridico(this, listaContactos);
	    listView.setAdapter(adapter);
		
	    Intent	objIntent=getIntent();
		Bundle	objbundle=objIntent.getExtras();
		if(objbundle!=null) { idUsuario = objbundle.getInt("idUsuario");
 }
     
		//--------------------------------------------------------------------------/
		 	
		
	 }
	 

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
		 
			getMenuInflater().inflate(R.menu.menu3, menu);
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) 
		{
			
			
			switch (item.getItemId()) {
		        case R.id.op_cn:
		        {   
		        	cargarListaClienteNatural();
		            return true;
		        }
		        case R.id.op_regresar:
		        {   
		        	cargarMenu();
		        	
		            return true;
		        }
		        default:
		            return super.onOptionsItemSelected(item);
			}
		}


		private void cargarListaClienteNatural() {
			// TODO Auto-generated method stub
			Intent objIntent=new Intent(listarClienteJuridico.this,listarCliente.class);
			Bundle	objbundle=new Bundle();
			objbundle.putSerializable("idUsuario",idUsuario); 
			objIntent.putExtras(objbundle);
			startActivity(objIntent);
		
			finish();
		}


		private void cargarMenu() {
			// TODO Auto-generated method stub
			Intent objIntent=new Intent(listarClienteJuridico.this,Opciones.class);
			Bundle	objbundle=new Bundle();
			objbundle.putSerializable("idUsuario",idUsuario); 
			objbundle.putSerializable("idSincro", 1); 
			
			objIntent.putExtras(objbundle);
			startActivity(objIntent); 
			finish();
		}
		
		
	}
