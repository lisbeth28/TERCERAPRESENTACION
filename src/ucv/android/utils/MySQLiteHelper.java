package ucv.android.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase; 
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper 
{	 
	public static  final String TABLAUSUARIO="usuario";
	public static  final String TABLAEMPLEADO="empleado";
	
	public static  final String TABLADISTRITO="distrito";
	public static  final String TABLACLIENTE="cliente";
	public static  final String TABLACLIENTEN="clienteN";
	public static  final String TABLACLIENTEJ="clienteJ";

	public static  final String TABLAMARCA="Marca";
	public static  final String TABLAUNIDADMEDIDA="UnidadMedida";
	public static  final String TABLALINEAPRINCIPAL="lineaprincipal";
	public static  final String TABLAPRODUCTO="Producto";
	
	public static  final String TABLAPEDIDO="pedido";
	public static  final String TABLADETALLEPEDIDO="detallepedido";
	
	public static final String NOMBREBASEDATOS="BDEMPRESA.db";
	public static final int  VERSION=1;
	 
	
	public static  final String  SQLUsuario=" create table "+TABLAUSUARIO+
			" ( idUsuario integer primary key autoincrement , " +
			"usuario text not null ," +
			"password text  not null );";
	
	public static  final String  SQLEmpleado=" create table "+TABLAEMPLEADO+
			" (idEmpleado integer primary key autoincrement," +
    		"nombre text not null," +
    		"apellido text not null," +
    		"direccion text," +
    		"telefono text," +
    		"celular text," +
    		"idUsuario integer ," +
    		" FOREIGN KEY (idUsuario) REFERENCES "+TABLAUSUARIO+"(idUsuario) );";	
	
   //-------------------------------------------------------------------------
	
	public static  final String  SQLDistrito=" create table "+TABLADISTRITO+
			" ( idDistrito integer primary key  , " +
			"Nombre text not null );";
	
	public static  final String  SQLCliente=" create table "+TABLACLIENTE+
			" ( idCliente integer primary key autoincrement , " +
			"Nombre text not null ," +
			"Direccion text not null ," +
			"Telefono text not null ," +
			"idDistrito text not null ," + 
			"idEstado int not null ," +
	        " FOREIGN KEY (idDistrito) REFERENCES "+TABLADISTRITO+" (idDistrito) );";	
	
	
	public static  final String  SQLClienteN=" create table "+TABLACLIENTEN+
			" (idClienteN integer primary key autoincrement," + 
    		"idCliente integer not null," + 
    		"Celular text not null," +
    		"DNI text not null," +
    		"Correo text," + 
    		" FOREIGN KEY (idCliente) REFERENCES "+TABLACLIENTE+" (idCliente) );";	
	
	public static  final String  SQLClienteJ=" create table "+TABLACLIENTEJ+
			" (idClienteJ integer primary key autoincrement," +
    		"idCliente integer not null," +
    		"RUC text not null," +
    		"Contacto text," +
    		"Url text," +  
    		" FOREIGN KEY (idCliente) REFERENCES "+TABLACLIENTE+" (idCliente) );";	
	//--------------------------------------------------------------------------------
	
	public static  final String  SQLUnidadMedida=" create table "+TABLAUNIDADMEDIDA+
			" ( idUnidadMedida integer primary key  , " + 
            "Unidad text not null );";
	
	public static  final String  SQLMarca=" create table "+TABLAMARCA+
			" ( idMarca integer primary key  , " +
			"Nombre text not null," +
			"Codigo text not null ,"+
            "idEstado integer not null );";
	
	public static  final String  SQLLineaPrincipal=" create table "+TABLALINEAPRINCIPAL+
			" ( idLineaPrincipal integer primary key  , " +
			"Nombre text not null," +
			"Codigo text not null ,"+
            "idEstado integer not null );";
	
	public static  final String  SQLProducto=" create table "+TABLAPRODUCTO+
			" (idProducto integer primary key autoincrement," +
    		"idUnidadMedida integer not null," +
    		"idLineaPrincipal integer not null," +
    		"idMarca integer not null," +
    		"Descripcion text," + 
    		"Imagen BLOB," +    
    		"CodReferencia text," +  
    		"Caracteristicas text," + 
    		"PrecioVenta float," +  
    		"Stock integer," + 
    		"idEstado integer," + 
    		" FOREIGN KEY (idUnidadMedida) REFERENCES "+TABLAUNIDADMEDIDA+" (idUnidadMedida),"+
        	" FOREIGN KEY (idLineaPrincipal) REFERENCES "+TABLALINEAPRINCIPAL+" (idLineaPrincipal) ,"+	
	        " FOREIGN KEY (idMarca) REFERENCES "+TABLAMARCA+" (idMarca) );";	
	//--------------------------------------------------------------------------------
	
	public static  final String  SQLPedido=" create table "+TABLAPEDIDO+
			" (idpedido integer primary key autoincrement," +
    		"idEmpleado integer not null," +
    		"idCliente integer not null," +
    		"Fecha text not null," +
    		"Observaciones text," +   
    		"idEstado integer," + 
    		" FOREIGN KEY (idEmpleado) REFERENCES "+TABLAEMPLEADO+" (idEmpleado),"+
        	" FOREIGN KEY (idCliente) REFERENCES "+TABLACLIENTE+" (idCliente) );";	
	
	public static  final String  SQLDetallePedido=" create table "+TABLADETALLEPEDIDO+
			" (idDetallePedido integer primary key autoincrement," +
    		"idPedido integer not null," +
    		"idProducto integer not null," +
    		"Cantidad integer not null," + 
    		"Precio float," +  
    		"SubTotal float," +  
    		" FOREIGN KEY (idpedido) REFERENCES "+TABLAPEDIDO+" (idpedido),"+
        	" FOREIGN KEY (idProducto) REFERENCES "+TABLAPRODUCTO+" (idProducto) );";	
	//--------------------------------------------------------------------------------
	
	public MySQLiteHelper(Context context)
	{ super(context,NOMBREBASEDATOS,null,VERSION);		
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) 
	{     crearTablaUsuario(database); 
	}
		
	@Override
	public void onUpgrade(SQLiteDatabase db, int antiguaversion,
			int nuevaversion)
	{	Log.w(MySQLiteHelper.class.getName(),
		"Actualizando la Version :"+antiguaversion+" a "+nuevaversion);
		db.execSQL("drop table if exists "+TABLAUSUARIO);
		db.execSQL("drop table if exists "+TABLAEMPLEADO);
		
		borrarTablaDistrito(db);
		borrarTablaCliente(db);
		
		db.execSQL("drop table if exists "+TABLACLIENTEN);
		
		borrarTablaClienteJ(db);
		
		onCreate(db);
	}
	 
	public void CrearTablasRestantes(SQLiteDatabase database) 
	{  
		borrarTablaEmpleado(database);
		 
		borrarTablaClienteN(database);
		borrarTablaClienteJ(database);
		borrarTablaCliente(database);
		borrarTablaDistrito(database);
		
		borrarTablaProducto(database);
		borrarTablaUnidadMedida(database);
		borrarTablaMarca(database);
		borrarTablaLineaPrincipal(database);
		
		 
	   crearTablaEmpleado(database);
	   
	   crearTablaDistrito(database);
	   crearTablaCliente(database);
	   crearTablaClienteN(database);
	   crearTablaClienteJ(database);
	   
	   crearTablaUnidadMedida(database);
	   crearTablaMarca(database);
	   crearTablaLineaPrincipal(database);
	   crearTablaProducto(database);
	}
	
	
	
	
// ********************************************************************************
	
	private void crearTablaUsuario(SQLiteDatabase database) {
		database.execSQL(SQLUsuario);   
	}
	
	private void crearTablaEmpleado(SQLiteDatabase database) {
        database.execSQL(SQLEmpleado); 
	}
	
	public void borrarTablaUsuarios(SQLiteDatabase db)
	{	
		db.execSQL("drop table if exists "+TABLAEMPLEADO);
		db.execSQL("drop table if exists "+TABLAUSUARIO);
		
		crearTablaUsuario(db);
	}
	
	public void borrarTablaEmpleado(SQLiteDatabase db){
		db.execSQL("drop table if exists "+TABLAEMPLEADO);
		crearTablaEmpleado(db); 
	}

// ********************************************************************************

	private void crearTablaDistrito(SQLiteDatabase database) {
		database.execSQL(SQLDistrito);   
	}
	
	private void crearTablaCliente(SQLiteDatabase database) {
		database.execSQL(SQLCliente);   
	}
	
	private void crearTablaClienteN(SQLiteDatabase database) {
		database.execSQL(SQLClienteN);   
	}
	
	private void crearTablaClienteJ(SQLiteDatabase database) {
	    database.execSQL(SQLClienteJ);   
	}
	
	public void borrarTablaDistrito(SQLiteDatabase db){
		db.execSQL("drop table if exists "+TABLADISTRITO);
		crearTablaDistrito(db); 
	}
	
	public void borrarTablaCliente(SQLiteDatabase db){
		db.execSQL("drop table if exists "+TABLACLIENTE);
		crearTablaCliente(db); 
	}
	
	public void borrarTablaClienteN(SQLiteDatabase db){
		db.execSQL("drop table if exists "+TABLACLIENTEN);
		
		crearTablaClienteN(db); 
	}
	
	public void borrarTablaClienteJ(SQLiteDatabase db){
		db.execSQL("drop table if exists "+TABLACLIENTEJ);
		crearTablaClienteJ(db); 
	}
	
	// ********************************************************************************

	private void crearTablaUnidadMedida(SQLiteDatabase database) {
		database.execSQL(SQLUnidadMedida);   
	}
	
	private void crearTablaMarca(SQLiteDatabase database) {
		database.execSQL(SQLMarca);   
	}
	private void crearTablaLineaPrincipal(SQLiteDatabase database) {
		database.execSQL(SQLLineaPrincipal);   
	}
	
	private void crearTablaProducto(SQLiteDatabase database) {
		database.execSQL(SQLProducto);   
	}
	
	public void borrarTablaUnidadMedida(SQLiteDatabase db){
		db.execSQL("drop table if exists "+TABLAUNIDADMEDIDA);
		crearTablaUnidadMedida(db); 
	}
	public void borrarTablaMarca(SQLiteDatabase db){
		db.execSQL("drop table if exists "+TABLAMARCA);
		crearTablaMarca(db); 
	}
	public void borrarTablaLineaPrincipal(SQLiteDatabase db){
		db.execSQL("drop table if exists "+TABLALINEAPRINCIPAL);
		crearTablaLineaPrincipal(db); 
	}
	public void borrarTablaProducto(SQLiteDatabase db){
		db.execSQL("drop table if exists "+TABLAPRODUCTO);
		crearTablaProducto(db); 
	}
	
	

	private void crearTablaPedido(SQLiteDatabase database) {
		database.execSQL(SQLPedido);   
	}
	
	public void borrarTablaPedido(SQLiteDatabase db){
		db.execSQL("drop table if exists "+TABLAPEDIDO);
		crearTablaPedido(db); 
	}

	private void crearTablaDetallePedido(SQLiteDatabase database) {
		database.execSQL(SQLDetallePedido);   
	}
	
	public void borrarTablaDetallePedido(SQLiteDatabase db){
		db.execSQL("drop table if exists "+TABLADETALLEPEDIDO);
		crearTablaDetallePedido(db); 
	}
}
