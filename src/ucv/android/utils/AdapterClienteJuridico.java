package ucv.android.utils;

import java.util.ArrayList;
 
import ucv.android.bean.clienteJBEAN;
import ucv.android.principal.R;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterClienteJuridico  extends ArrayAdapter<clienteJBEAN> {
	Activity context;
	ArrayList<clienteJBEAN> listaContactos;
    String telefono,Contacto;
     
	// Le pasamos al constructor el contecto y la lista de contactos
	public AdapterClienteJuridico(Activity context, ArrayList<clienteJBEAN> listaContactos) {
		super(context, R.layout.layout_adapter_clientejurd, listaContactos);
		this.context = context;
		this.listaContactos = listaContactos;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		// Rescatamos cada item del listview y lo inflamos con nuestro layout
		View item = convertView;
		item = context.getLayoutInflater().inflate(R.layout.layout_adapter_clientejurd, null);
		
		clienteJBEAN c = listaContactos.get(position);
		
		// Definimos los elementos que tiene nuestro layout 
		TextView razonSocial = (TextView) item.findViewById(R.id.TXTListaRazonSocl);
		TextView ruc = (TextView) item.findViewById(R.id.TXTListaRUC);
		TextView direccion = (TextView) item.findViewById(R.id.TXTListaDireccionCJ);
		TextView telefonoList = (TextView) item.findViewById(R.id.TXTListaTelefonoCJ);
		TextView contacto = (TextView) item.findViewById(R.id.TXTListaContacto);
		
		
		telefono=c.getCliente().getTelefono(); 
		
		if(telefono==null){
			telefono="ninguno";
		}
		
		Contacto=c.getContacto();
		
		if(Contacto==null){
			Contacto="ninguno";
		}
		
		razonSocial.setText(c.getCliente().getNombre().toUpperCase());
		ruc.setText         (" • RUC       : "+c.getRUC());
		direccion.setText   (" • Direccion : "+c.getCliente().getDireccion());
		telefonoList.setText(" • Telefono  : "+telefono);
		contacto.setText    (" • Contacto   : "+Contacto);
		
		
		return (item);
	}
}
