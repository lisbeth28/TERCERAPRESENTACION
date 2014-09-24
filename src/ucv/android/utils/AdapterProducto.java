package ucv.android.utils;

import java.util.ArrayList;

import ucv.android.bean.productoBEAN;
import ucv.android.principal.R;
 
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 

public class AdapterProducto extends ArrayAdapter<productoBEAN> {
	Activity context;
	ArrayList<productoBEAN> listaContactos;
	
	// Le pasamos al constructor el contecto y la lista de contactos
	public AdapterProducto(Activity context, ArrayList<productoBEAN> listaContactos) {
		super(context, R.layout.layout_adapter, listaContactos);
		this.context = context;
		this.listaContactos = listaContactos;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		// Rescatamos cada item del listview y lo inflamos con nuestro layout
		View item = convertView;
		item = context.getLayoutInflater().inflate(R.layout.layout_adapter, null);
		
		productoBEAN c = listaContactos.get(position);
		
		// Definimos los elementos que tiene nuestro layout
		ImageView img = (ImageView)item.findViewById(R.id.imagen);
		TextView nombre = (TextView) item.findViewById(R.id.txtNombre);
		TextView carat = (TextView) item.findViewById(R.id.txtCaract);
		TextView precio = (TextView) item.findViewById(R.id.txtPrecio);
		
		 
		byte[] byteData = c.getImagen();
		
		Bitmap  photo = BitmapFactory.decodeByteArray( byteData, 0, byteData.length);
			
		
		
		img.setImageBitmap(photo); 
		
		 
		nombre.setText(c.getDescripcion());
		carat.setText("Caracteristicas"+c.getCaracteristicas());
		precio.setText("Precio"+ (c.getPrecioVenta()));
		
		return (item);
	}
}
