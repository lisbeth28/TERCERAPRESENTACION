package ucv.android.principal;

import java.io.Serializable;
import java.util.ArrayList; 

import ucv.android.bean.productoBEAN;
import ucv.android.dao.empleadoDAO;
import ucv.android.dao.productoDAO;
import ucv.android.principal.Opciones.asyncBuscarUsuario;
import ucv.android.utils.AdapterProducto;
  
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class vistaCatalogo extends Activity implements OnItemClickListener  {

	ListView listView;
	ArrayList<productoBEAN> listaProdBean;
	
	// Creamos un adapter personalizado
	AdapterProducto adapter;
	productoDAO productoDAO;
	
    TextView LblNombre;
    TextView Lblcaracteristica;
    TextView LblMarca;
    TextView LblCategoria;
    TextView LblUnidad;
    TextView LblPrecio;
    
    TextView LblprodctoBuscado;
    ImageView VistaImagen;
    
	private productoBEAN productoEncontrado;
	
	Button BTNBuscar;
	private TextView LblStock;
	private Serializable idUsuario;
	private Serializable idSincronizacion;
    
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listar_producto_detalle); 

		productoDAO= new productoDAO(this);
		productoDAO.open();
	 
		LblNombre=(TextView)findViewById(R.id.LBLPRODTITULO);
		Lblcaracteristica=(TextView)findViewById(R.id.LBLPRODCARACT);
		LblMarca=(TextView)findViewById(R.id.LBLPRODMARCA);
		LblCategoria=(TextView)findViewById(R.id.LBLPRODCATG);
		LblUnidad=(TextView)findViewById(R.id.LBLPRODUNID);
		LblPrecio=(TextView)findViewById(R.id.LBLPRODPRECIO);
		LblStock=(TextView)findViewById(R.id.LBLPRODSTOCK);
		
		VistaImagen=(ImageView)findViewById(R.id.IMGVPRODUCTO);
		BTNBuscar=(Button)findViewById(R.id.BTNLSTPBUSCARPROD);
		
		
		
		
		LblprodctoBuscado=(TextView)findViewById(R.id.TXTLSPDatoBuscar);
		
		listView = (ListView) findViewById(R.id.LSTVPRODUCTOS);
		listView.setOnItemClickListener(this);
		
		listaProdBean = new ArrayList<productoBEAN>();
		
		listaProdBean=productoDAO.listadoSQLITE();
		 
		 
		adapter = new AdapterProducto(this, listaProdBean);
		listView.setAdapter(adapter);
		 
		Intent	objIntent=getIntent();
		Bundle	objbundle=objIntent.getExtras();
		
		if(objbundle!=null) {   
			idUsuario = objbundle.getInt("idUsuario"); 
			idSincronizacion= objbundle.getInt("idSincro");
		}
		
		
		
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int posicion, long arg3) {
		// TODO Auto-generated method stub
	
    productoEncontrado= listaProdBean.get(posicion);
	
    mostrar(productoEncontrado);
    

	}
  
	public void buscar(View v) {
		// TODO Auto-generated method stub
		String dato=LblprodctoBuscado.getText().toString();
		
		listaProdBean=productoDAO.listadoSQLITE(dato) ;
		adapter = new AdapterProducto(this, listaProdBean);
		listView.setAdapter(adapter);
		    
	}


	private void mostrar(productoBEAN objProducto) {
		// TODO Auto-generated method stub
		LblNombre.setText(objProducto.getDescripcion());
		Lblcaracteristica.setText("CARACTERISTICAS :"+objProducto.getCaracteristicas());
		LblMarca.setText("MARCA           :"+objProducto.getMarca().getNombre());
		LblCategoria.setText("CATEGORIA         :"+objProducto.getLineaPrincipal().getNombre());
		LblUnidad.setText("UNIDAD          :"+objProducto.getUnidad().getUnidad());
		LblPrecio.setText("PRECIO              : S/."+objProducto.getPrecioVenta()+"");
		LblStock.setText("STOCK                 :"+objProducto.getStock()+"");
		byte[] byteData = objProducto.getImagen();
		
		Bitmap  photo = BitmapFactory.decodeByteArray( byteData, 0, byteData.length);
			 
		VistaImagen.setImageBitmap(photo); 
		
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
	        	regresarMenu();
	            return true;
	        }
	        case R.id.men_sincronizar:
	        {   
	        	sincronizarTablas();
	            return true;
	        }
	        default:
	            return super.onOptionsItemSelected(item);
		}
	}


	private void regresarMenu() {
		Intent objIntent=new Intent(vistaCatalogo.this,Opciones.class);
		Bundle	objbundle=new Bundle();
		objbundle.putSerializable("idUsuario",idUsuario); 
		objbundle.putSerializable("idSincro", idSincronizacion); 
		
		objIntent.putExtras(objbundle);
		startActivity(objIntent); 
		
		productoDAO.close();
		finish();
	}


	private void sincronizarTablas() {
		new asyncSincronizarProducto().execute(); 
		 
	}
	
	class asyncSincronizarProducto extends     AsyncTask <Void,Void,Void> 
	   {   
		private ProgressDialog progressDialog; 
		
			
	       protected void onPreExecute() 
	        {  
	    	   progressDialog = ProgressDialog.show( vistaCatalogo.this,"Sincronizando BD -> Producto", " No apage la conexion a intenet ...",true);
	     	     
	    	   productoDAO= new productoDAO(getApplicationContext());  } 
	       
	        @Override  
	        protected Void doInBackground(Void... arg0) 
	        {   
	   	    try {   
	   	    	
	   	    	productoDAO.open();
	   	    	productoDAO.Sincronizar();
	   	    	listaProdBean=productoDAO.listadoSQLITE();
	   		 
	   	      	    	
	   	    } catch (Exception e)  {  	 }
	            
	   	       return null;  
	        }  
	      
	       @Override  
	         protected void onPostExecute(Void result)
	        {  
	    	   if(listaProdBean.size()!=0){ 
	    		  listar();    } 
	    	   progressDialog.dismiss();
	         }  
	  } 
		
	public  void listar() {
		 adapter = new AdapterProducto(this, listaProdBean);
		listView.setAdapter(adapter);
	}

}
