package ucv.android.bean;

public class clienteBEAN {

	int idCliente;
	String Nombre;
	String Direccion;
	String Telefono;
	distritoBEAN distrito;
	int idEstado;
	
	
	public int getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getDireccion() {
		return Direccion;
	}
	public void setDireccion(String direccion) {
		Direccion = direccion;
	}
	public String getTelefono() {
		return Telefono;
	}
	public void setTelefono(String telefono) {
		Telefono = telefono;
	}
	public distritoBEAN getDistrito() {
		return distrito;
	}
	public void setDistrito(distritoBEAN distrito) {
		this.distrito = distrito;
	}
	 
	
	
	
}
