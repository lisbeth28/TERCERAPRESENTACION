package ucv.android.utils;

import java.util.ArrayList;
 
import ucv.android.bean.pedidoBEAN;
import ucv.android.principal.R;
import android.app.Activity;  
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter; 
import android.widget.TextView;

public class AdapterPedidos extends ArrayAdapter<pedidoBEAN> {
	Activity context;
	ArrayList<pedidoBEAN> listaContactos;
    String telefono,celular;
     
	// Le pasamos al constructor el contecto y la lista de contactos
	public AdapterPedidos(Activity context, ArrayList<pedidoBEAN> listaContactos) {
		super(context, R.layout.adapter_pedido, listaContactos);
		this.context = context;
		this.listaContactos = listaContactos;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		// Rescatamos cada item del listview y lo inflamos con nuestro layout
		View item = convertView;
		item = context.getLayoutInflater().inflate(R.layout.adapter_pedido, null);
		
		pedidoBEAN c = listaContactos.get(position);
		
		// Definimos los elementos que tiene nuestro layout 
		TextView idPedido = (TextView) item.findViewById(R.id.idPedidoTXT);
		TextView cliente = (TextView) item.findViewById(R.id.clienteTXT);
		TextView estado = (TextView) item.findViewById(R.id.estadoTXT);
		TextView fechaenvio = (TextView) item.findViewById(R.id.fechaEnvioTXT);
		TextView fecha = (TextView) item.findViewById(R.id.fechaTXT);
		
		idPedido.setText(String.valueOf(c.getIdpedido()));
		cliente.setText         (" • CLIENTE       : "+c.getCliente().getNombre());
		fecha.setText   (" • SOLICITADO : "+c.getFecha());
		fechaenvio.setText(" • OBSERVACIONES  : "+c.getObservaciones());
		
		if(c.getIdEstado()==5){
			estado.setText (" • ESTADO   : ESPERA");
			
		}else if(c.getIdEstado()==4){
			estado.setText (" • ESTADO   : CANCELADO");
			
		}
		
		
		
		return (item);
	}
}
