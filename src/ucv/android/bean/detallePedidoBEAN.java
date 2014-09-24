package ucv.android.bean;

public class detallePedidoBEAN {

	productoBEAN Producto;
	int idpedido;
	int Cantidad;
	double Precio;
	double SubTotal;
	
	public productoBEAN getProducto() {
		return Producto;
	}
	public void setProducto(productoBEAN producto) {
		Producto = producto;
	}
	public int getIdpedido() {
		return idpedido;
	}
	public void setIdpedido(int idpedido) {
		this.idpedido = idpedido;
	}
	public int getCantidad() {
		return Cantidad;
	}
	public void setCantidad(int cantidad) {
		Cantidad = cantidad;
	}
	public double getPrecio() {
		return Precio;
	}
	public void setPrecio(double precio) {
		Precio = precio;
	}
	public double getSubTotal() {
		return SubTotal;
	}
	public void setSubTotal(double subTotal) {
		SubTotal = subTotal;
	}
	
	
	
}
