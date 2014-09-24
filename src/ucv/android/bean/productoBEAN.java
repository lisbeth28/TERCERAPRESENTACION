package ucv.android.bean;

public class productoBEAN {
	int idProducto;
	
	String Descripcion;
	String CodReferencia;
	Double PrecioVenta; 
	String Caracteristicas;
	marcaBEAN Marca;
	lineaPrincipal LineaPrincipal;
	unidadBEAN Unidad;
	byte[] imagen;
	int Stock;
	int idEstado;

	
    public byte[] getImagen() {
		return imagen;
	}
    
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	 
	public String getCaracteristicas() {
		return Caracteristicas;
	}
	public void setCaracteristicas(String caracteristicas) {
		Caracteristicas = caracteristicas;
	}
	public marcaBEAN getMarca() {
		return Marca;
	}
	public void setMarca(marcaBEAN marca) {
		Marca = marca;
	}
	public lineaPrincipal getLineaPrincipal() {
		return LineaPrincipal;
	}
	public void setLineaPrincipal(lineaPrincipal lineaPrincipal) {
		LineaPrincipal = lineaPrincipal;
	}
	public unidadBEAN getUnidad() {
		return Unidad;
	}
	public void setUnidad(unidadBEAN unidad) {
		Unidad = unidad;
	}
	
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	public String getCodReferencia() {
		return CodReferencia;
	}
	public void setCodReferencia(String codReferencia) {
		CodReferencia = codReferencia;
	}
	public Double getPrecioVenta() {
		return PrecioVenta;
	}
	public void setPrecioVenta(Double precioVenta) {
		PrecioVenta = precioVenta;
	}
	public int getStock() {
		return Stock;
	}
	public void setStock(int stock) {
		Stock = stock;
	}
	public int getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}


}
