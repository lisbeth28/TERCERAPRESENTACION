package ucv.android.principal;

import java.util.ArrayList;

import ucv.android.bean.pedidoBEAN;
import ucv.android.dao.pedidoDAO; 
import ucv.android.utils.AdapterPedidos;
 
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class listarPedidos extends Activity {
	 
		private TextView lblTitulo;
		private int idSincronizacion=0;
		private int idEmpleado;
		private ListView listView;
		private ArrayList<pedidoBEAN> listaContactos;

		
		//private AdapterPedido adapter;
		private pedidoDAO contactoSQLITE;

		private int idUsuario;
		private AdapterPedidos adapter;
		
		 @Override
		protected void onCreate(Bundle savedInstanceState) {
			 
			super.onCreate(savedInstanceState);
			setContentView(R.layout.listado_pedidos); 

			contactoSQLITE= new pedidoDAO(this);
			contactoSQLITE.open();
		 
			listView = (ListView) findViewById(R.id.LSTVIEWPEDIDOESTADS);
			lblTitulo = (TextView) findViewById(R.id.LBLTITLISTADOPEDIDS);
	 
			Typeface font2=Typeface.createFromAsset(getAssets(),"Hey Pretty Girl.ttf");
			lblTitulo.setTypeface(font2);
			
			 
		    Intent	objIntent=getIntent();
			Bundle	objbundle=objIntent.getExtras();
			
			if(objbundle!=null) { 
				idUsuario = objbundle.getInt("idUsuario");
				idEmpleado= objbundle.getInt("idEmpleado");
				idSincronizacion= objbundle.getInt("idSincro");
			}
			
			
			//--------------------------------------------------------------------------/
			 	
			
			
			listaContactos = new ArrayList<pedidoBEAN>();
			
			contactoSQLITE.SincronizarPedidosyDetalle(idEmpleado, getApplicationContext());
			
			
			listaContactos=contactoSQLITE.ListarPedido(idEmpleado);
			
			 
            adapter = new AdapterPedidos(this, listaContactos);
		     
		    listView.setAdapter(adapter);
		   
			
		 }
		 

			@Override
			public boolean onCreateOptionsMenu(Menu menu) {
			 
				getMenuInflater().inflate(R.menu.menu_lista_ped, menu);
				return true;
			}

			@Override
			public boolean onOptionsItemSelected(MenuItem item) 
			{
				switch (item.getItemId()) {
			        case R.id.men_regrsar:
			        {   
			        	regresar();
			            return true;
			        }
			        case R.id.men_sincronizar:
			        {   
			        	sincronizar();
			            return true;
			        }
			        default:
			            return super.onOptionsItemSelected(item);
				}
			}


			private void sincronizar() {
				contactoSQLITE.SincronizarPedidosyDetalle(idEmpleado, getApplicationContext());
				
				
				listaContactos=contactoSQLITE.ListarPedido(idEmpleado);
				
				 
	            adapter = new AdapterPedidos(this, listaContactos);
			     
			    listView.setAdapter(adapter);
			}


			private void regresar() {
				Intent objIntent=new Intent(listarPedidos.this,Opciones.class);
				Bundle	objbundle=new Bundle();
				objbundle.putSerializable("idUsuario",idUsuario); 
				objbundle.putSerializable("idSincro", 1); 
				
				objIntent.putExtras(objbundle);
				startActivity(objIntent); 
				finish();
			}

		}

