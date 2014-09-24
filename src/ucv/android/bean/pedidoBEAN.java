package ucv.android.bean;

public class pedidoBEAN {

	clienteBEAN Cliente;
	int idpedido;
	int idEmpleado;
	String Fecha;
	String Observaciones;
	int idEstado;  
	 
	public clienteBEAN getCliente() {
		return Cliente;
	}
	public void setCliente(clienteBEAN cliente) {
		Cliente = cliente;
	}
	public int getIdpedido() {
		return idpedido;
	}
	public void setIdpedido(int idpedido) {
		this.idpedido = idpedido;
	}
	public int getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public String getFecha() {
		return Fecha;
	}
	public void setFecha(String fecha) {
		Fecha = fecha;
	}
	public String getObservaciones() {
		return Observaciones;
	}
	public void setObservaciones(String Observaciones) {
		this.Observaciones = Observaciones;
	}
	public int getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}
	
	
}
