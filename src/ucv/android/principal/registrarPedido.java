package ucv.android.principal;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ucv.android.bean.clienteBEAN;
import ucv.android.bean.detallePedidoBEAN;
import ucv.android.bean.pedidoBEAN;
import ucv.android.bean.productoBEAN;
import ucv.android.dao.clienteDAO; 
import ucv.android.dao.detallePedidoDAO;
import ucv.android.dao.pedidoDAO;
import ucv.android.dao.productoDAO;
import ucv.android.utils.AdapterDetallePedidos; 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle; 
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener; 
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView; 
import android.widget.Toast;

public class registrarPedido extends Activity implements OnClickListener{
	
	EditText txtCliente;
	EditText txtProducto;
	EditText txtCantidad;
	EditText txtObservaciones;
	ListView ListadetallePedido;
	
	detallePedidoBEAN objdetalle;
	pedidoBEAN objPedido;
	
	clienteDAO ClienteDAO;
	productoDAO ProductoDAO;
	pedidoDAO PedidoDAO;
	detallePedidoDAO DetallessDAO;
	
	Button agregarDetalle;
	 ImageButton validarCliente;

	ArrayList<detallePedidoBEAN> lista=new ArrayList<detallePedidoBEAN>();
	private productoBEAN objProducto;
	private clienteBEAN objCliente; 
		
	private AdapterDetallePedidos adapter;
	private TextView LBLFECHACT;
	
	private int idEmpleado;
	private int idUsuario;
	private int idPedido=0;
	private String FechaSolicitada;
	private String Observaciones;
	private Serializable idSincronizacion;
	
	
protected void onCreate(Bundle savedInstanceState) {
		
	super.onCreate(savedInstanceState);
	setContentView(R.layout.registar_nuevopedido);
	
	Intent	objIntent=getIntent();
	Bundle	objbundle=objIntent.getExtras();
	if(objbundle!=null) { 
		idUsuario = objbundle.getInt("idUsuario");  
		idEmpleado= objbundle.getInt("idEmpleado"); 
		idSincronizacion= objbundle.getInt("idSincro");
	}
	
	txtCliente=(EditText)findViewById(R.id.TXTNPCLIENTE);
	txtProducto=(EditText)findViewById(R.id.TXTNPPRODUCTO);
	txtCantidad=(EditText)findViewById(R.id.TXTNPCANTIDAD);
	txtObservaciones=(EditText)findViewById(R.id.TXTOBESRVACIONESPED);
	
	LBLFECHACT=(TextView)findViewById(R.id.LBLFECHASOLICITAD);
	
	validarCliente=(ImageButton)findViewById(R.id.BTIMGValidar);
	agregarDetalle =(Button)findViewById(R.id.BTNAGREGARDETALLE);
	
	
	agregarDetalle.setOnClickListener(this);
	validarCliente.setOnClickListener(this);
	
	ListadetallePedido=(ListView)findViewById(R.id.LISTVIEWDPEDIDO);
	 
	ClienteDAO= new clienteDAO(this);
	ProductoDAO= new productoDAO(this);
	PedidoDAO=new pedidoDAO(this);
	DetallessDAO= new detallePedidoDAO(this);
	
	ClienteDAO.open();
	ProductoDAO.open();
	PedidoDAO.open();
	DetallessDAO.open();
	
	lista=new ArrayList<detallePedidoBEAN>();
	adapter = new AdapterDetallePedidos(this, lista);
    
	ListadetallePedido.setAdapter(adapter);
   
	obtenerfecha() ;
	System.out.println(" EMPL  "+idEmpleado);
	
	idPedido=PedidoDAO.SincronizarPedidosyDetalle(idEmpleado, getApplicationContext());
	
	
 }

private void obtenerfecha() {
	Calendar c = Calendar.getInstance();
    SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
	String fecha = df1.format(c.getTime()); 
	
	SimpleDateFormat df3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
	FechaSolicitada = df3.format(c.getTime());
			
	LBLFECHACT.setText(fecha);
	
}

@Override
public void onClick(View v) {
	
	if(v==agregarDetalle){
		agregarDetalle();
	}
	
	if(v==validarCliente){
		validarCliente();
	}
	
}

private void validarCliente() {
    	String nombreCliente=txtCliente.getText().toString();
	    objCliente= new clienteBEAN();
	    objCliente=ClienteDAO.buscarCliente(nombreCliente);
	    
	    if(objCliente.getIdCliente()>0){
	    	Toast toast2 =   Toast.makeText(getApplicationContext(),
	                "  Se valido el Cliente", Toast.LENGTH_LONG);
	   toast2.setGravity(Gravity.CENTER|Gravity.LEFT,210,90);
	   toast2.show();
	    	
	   txtCliente.setText(objCliente.getNombre());
	    } else {
	    Toast toast2 =   Toast.makeText(getApplicationContext(),
                " No se encontro ningun Cliente con ese codigo", Toast.LENGTH_LONG);
   toast2.setGravity(Gravity.CENTER|Gravity.LEFT,210,90);
   toast2.show();}
	   
}


private void agregarDetalle() {
	
    String codReferencia=txtProducto.getText().toString();
	objProducto= new productoBEAN();
	objProducto=ProductoDAO.buscarProducto(codReferencia);
	
    if(objProducto.getIdProducto() > 0){
    	
    	int cantidad=Integer.parseInt(txtCantidad.getText().toString());
    	
    	objdetalle=new detallePedidoBEAN();
    	
    	objdetalle.setIdpedido(idPedido);
    	objdetalle.setCantidad(cantidad);
    	objdetalle.setPrecio(objProducto.getPrecioVenta());
    	objdetalle.setProducto(objProducto);
    	
    	double subtotal=cantidad*objProducto.getPrecioVenta();
    	
    	objdetalle.setSubTotal(subtotal);
    	  
    	System.out.println("PROD "+objdetalle.getProducto().getCodReferencia());

    	System.out.println(" Sincronizado => "+objProducto.getCodReferencia());
    	
    	System.out.println(" UNID "+objdetalle.getProducto().getUnidad().getIdUnidadMedida()  );
    	System.out.println( " CANT "+objdetalle.getCantidad() );
    	System.out.println(" PREC "+objdetalle.getPrecio()  );
    	System.out.println(" SUBT "+objdetalle.getSubTotal()  );
    	
    	lista.add(objdetalle);
    	    
    	adapter = new AdapterDetallePedidos(this, lista);
    	    
    	ListadetallePedido.setAdapter(adapter);
    	
    	limpiarnuevoDetalle();
    	
    } else{
    	
    	   limpiarnuevoDetalle();
    	 
		   Toast toast2 =   Toast.makeText(getApplicationContext(),
	                    " No se encontro ningun producto con ese codigo", Toast.LENGTH_LONG);
	       toast2.setGravity(Gravity.CENTER|Gravity.LEFT,210,90);
	       toast2.show();
    	
    }
	
	
}

private void limpiarnuevoDetalle() {
	// TODO Auto-generated method stub
	txtProducto.setText("");
	txtCantidad.setText("");
}

@Override
public boolean onCreateOptionsMenu(Menu menu) {
 
	getMenuInflater().inflate(R.menu.menu_pedido, menu);
	return true;
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
        case R.id.opc_guardar:
        {   
        	guardarPedidoCompleto();
            return true;
        }
        case R.id.opc_reg:
        {   
        	sincronizar();
        	regresar();
            return true;
        }
        default:
            return super.onOptionsItemSelected(item);
	}
}



private void regresar() {
	Intent objIntent=new Intent(registrarPedido.this,Opciones.class);
	Bundle	objbundle=new Bundle();
	objbundle.putSerializable("idUsuario",idUsuario); 
	objbundle.putSerializable("idSincro", idSincronizacion); 
	
	objIntent.putExtras(objbundle);
	startActivity(objIntent); 
	finish();
}

private void sincronizar() {
	PedidoDAO.sincronizarMYSQL(idEmpleado,getApplicationContext());
	PedidoDAO.close();
	

}

private void guardarPedidoCompleto() {
	// TODO Auto-generated method stub
	idPedido=idPedido+1;
	System.out.println("SE GUARDA EL PEDIDO :"+idPedido);
	objPedido=new pedidoBEAN();
	//Observaciones=txtObservaciones.getText();
	Observaciones="OLASSS";
	
	objPedido.setIdpedido(idPedido);
	objPedido.setCliente(objCliente);
	objPedido.setIdEmpleado(idEmpleado);
	objPedido.setFecha(FechaSolicitada);
	objPedido.setCliente(objCliente);
	objPedido.setObservaciones(Observaciones);
	objPedido.setIdEstado(6);

	PedidoDAO.InsrtPEDIDOSQLITE(objPedido);
	//guardando los detalles en SQLITE
	 System.out.println("detalles : "+lista.size());
	 
	 for(int i = 0; i < lista.size(); i++){
		 objdetalle=new detallePedidoBEAN();
		 objdetalle = lista.get(i);
		 
		 objdetalle.setIdpedido(idPedido);
		 
		 System.out.println("DETALLE : INSERTO EN SQLITE idPedido =>"+objdetalle.getIdpedido()+"  "+objdetalle.getSubTotal()+" PROD"
				 +objdetalle.getProducto().getIdProducto()+"  CANT"+objdetalle.getCantidad()+"  PRECIO"+objdetalle.getPrecio());
			
		 DetallessDAO.InsrtDetallePEDIDOSQLITE(objdetalle);
	 }
	 
	 
}


}
