package ucv.android.bean;

public class clienteNBEAN {

	int  idClienteN; 
	String DNI; 
	String Celular;
	String Correo;
	clienteBEAN cliente;
	
	public int getIdClienteN() {
		return idClienteN;
	}
	public void setIdClienteN(int idClienteN) {
		this.idClienteN = idClienteN;
	} 
	public String getDNI() {
		return DNI;
	}
	public void setDNI(String dNI) {
		DNI = dNI;
	}
	public String getCelular() {
		return Celular;
	}
	public void setCelular(String celular) {
		Celular = celular;
	}
	public String getCorreo() {
		return Correo;
	}
	public void setCorreo(String correo) {
		Correo = correo;
	}
	public clienteBEAN getCliente() {
		return cliente;
	}
	public void setCliente(clienteBEAN cliente) {
		this.cliente = cliente;
	}
	
	
	
}
