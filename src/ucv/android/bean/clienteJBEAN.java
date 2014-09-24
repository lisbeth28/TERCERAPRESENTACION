package ucv.android.bean;

public class clienteJBEAN {
	
	int  idClienteJ; 
	String RUC;
	String Contacto; 
	String Url;
   
	public int getIdClienteJ() {
		return idClienteJ;
	}
	public void setIdClienteJ(int idClienteJ) {
		this.idClienteJ = idClienteJ;
	}
	distritoBEAN distrito;
	clienteBEAN cliente;
	
	
	public distritoBEAN getDistrito() {
		return distrito;
	}
	public clienteBEAN getCliente() {
		return cliente;
	}
	public void setCliente(clienteBEAN cliente) {
		this.cliente = cliente;
	}
	public void setDistrito(distritoBEAN distrito) {
		this.distrito = distrito;
	}
	 
	public String getRUC() {
		return RUC;
	}
	public void setRUC(String rUC) {
		RUC = rUC;
	}
	public String getContacto() {
		return Contacto;
	}
	public void setContacto(String contacto) {
		Contacto = contacto;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	} 
	
	
	
}
