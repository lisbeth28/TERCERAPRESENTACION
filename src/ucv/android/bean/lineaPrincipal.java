package ucv.android.bean;

public class lineaPrincipal {
	
	int idLineaPrincipal;
	String Nombre;
	String Codigo;
	int idEstado;

	 
	public int getIdLineaPrincipal() {
		return idLineaPrincipal;
	}
	public void setIdLineaPrincipal(int idLineaPrincipal) {
		this.idLineaPrincipal = idLineaPrincipal;
	}
	public String getCodigo() {
		return Codigo;
	}
	public void setCodigo(String codigo) {
		Codigo = codigo;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public int getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}

}
