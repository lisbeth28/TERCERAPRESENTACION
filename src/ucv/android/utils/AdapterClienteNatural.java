package ucv.android.utils;

import java.util.ArrayList;

import ucv.android.bean.clienteNBEAN; 
import ucv.android.principal.R;
import android.app.Activity;  
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter; 
import android.widget.TextView;

public class AdapterClienteNatural extends ArrayAdapter<clienteNBEAN> {
	Activity context;
	ArrayList<clienteNBEAN> listaContactos;
    String telefono,celular;
     
	// Le pasamos al constructor el contecto y la lista de contactos
	public AdapterClienteNatural(Activity context, ArrayList<clienteNBEAN> listaContactos) {
		super(context, R.layout.layout_adapter_cliente, listaContactos);
		this.context = context;
		this.listaContactos = listaContactos;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		// Rescatamos cada item del listview y lo inflamos con nuestro layout
		View item = convertView;
		item = context.getLayoutInflater().inflate(R.layout.layout_adapter_cliente, null);
		
		clienteNBEAN c = listaContactos.get(position);
		
		// Definimos los elementos que tiene nuestro layout 
		TextView nombreCompleto = (TextView) item.findViewById(R.id.TXTListaNombreCom);
		TextView DNI = (TextView) item.findViewById(R.id.TXTListaDNI);
		TextView direccion = (TextView) item.findViewById(R.id.TXTListaDireccionCN);
		TextView telefonoList = (TextView) item.findViewById(R.id.TXTListaTelefono);
		TextView celularList = (TextView) item.findViewById(R.id.TXTListaCelular);
		
		
		telefono=c.getCliente().getTelefono(); 
		
		if(telefono==null){
			telefono="ninguno";
		}
		
		celular=c.getCelular();
		
		if(celular==null){
			celular="ninguno";
		}
		
		nombreCompleto.setText(c.getCliente().getNombre().toUpperCase());
		DNI.setText         (" • DNI       : "+c.getDNI());
		direccion.setText   (" • Direccion : "+c.getCliente().getDireccion());
		telefonoList.setText(" • Telefono  : "+telefono);
		celularList.setText (" • Celular   : "+celular);
		
		
		return (item);
	}
}
