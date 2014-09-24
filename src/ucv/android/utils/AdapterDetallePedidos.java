package ucv.android.utils;

import java.util.ArrayList;
 
import ucv.android.bean.detallePedidoBEAN; 
import ucv.android.principal.R;
import android.app.Activity;  
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter; 
import android.widget.TextView;

public class AdapterDetallePedidos extends ArrayAdapter<detallePedidoBEAN> {
	Activity context;
	ArrayList<detallePedidoBEAN> listaContactos;
    String telefono,celular;
     
	// Le pasamos al constructor el contecto y la lista de contactos
	public AdapterDetallePedidos(Activity context, ArrayList<detallePedidoBEAN> listaContactos) {
		super(context, R.layout.adapter_detalle_pedido, listaContactos);
		this.context = context;
		this.listaContactos = listaContactos;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		// Rescatamos cada item del listview y lo inflamos con nuestro layout
		View item = convertView;
		item = context.getLayoutInflater().inflate(R.layout.adapter_detalle_pedido, null);
		
		detallePedidoBEAN c = listaContactos.get(position);
		
		// Definimos los elementos que tiene nuestro layout 
		TextView codigo = (TextView) item.findViewById(R.id.TXTCODIGO);
		TextView unidad = (TextView) item.findViewById(R.id.TXTUNID);
		TextView cant = (TextView) item.findViewById(R.id.TXTCANT);
		TextView precio = (TextView) item.findViewById(R.id.TXTPRECIO);
		TextView subtotal = (TextView) item.findViewById(R.id.TXTSUBTOTAL);
		
		codigo.setText(String.valueOf(c.getProducto().getCodReferencia()));
		unidad.setText (String.valueOf(c.getProducto().getUnidad().getUnidad()));
		cant.setText   (String.valueOf(c.getCantidad()));
		precio.setText(String.valueOf(c.getPrecio()));
		subtotal.setText (String.valueOf(c.getSubTotal()));
		
		
		return (item);
	}
}
