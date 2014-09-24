package ucv.android.principal;

import ucv.android.dao.usuarioDAO; 
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Gravity; 
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Principal extends Activity implements OnClickListener {

	private usuarioDAO usuarioDao; 

	public int idUsuario=0;
	private int estadoSincronizacion=0;
	
	private TextView lbltitulo;
	private TextView lblusuario;
	private TextView lblpassword;
	
	private EditText txtUsuario;
	private EditText txtPassword;
	
	private Button btnIngresar;
	private Button btnSalir;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.principal);
		
	  //***********************************************/
		usuarioDao=new usuarioDAO(this);
		usuarioDao.open(); // abrir  la conexion a la BD 
      //***********************************************/
		
		btnIngresar=(Button)findViewById(R.id.BTNLOGIN);
		btnIngresar.setOnClickListener(this);
		btnSalir=(Button)findViewById(R.id.BTNCERRAR);
		btnSalir.setOnClickListener(this);
		
		txtUsuario=(EditText)findViewById(R.id.TXTUSER);
		txtPassword=(EditText)findViewById(R.id.TXTPASS);
	
		lbltitulo=(TextView)findViewById(R.id.LBLTITULO);
		lblusuario=(TextView)findViewById(R.id.LBLUSUARIO);
		lblpassword=(TextView)findViewById(R.id.LBLPASSWRD);
		
	  //***********************************************/
		Typeface font=Typeface.createFromAsset(getAssets(),"Hey Pretty Girl.ttf");
		lbltitulo.setTypeface(font);
		
		Typeface font2=Typeface.createFromAsset(getAssets(),"RivannaNF.ttf");
		lblusuario.setTypeface(font2);
		lblpassword.setTypeface(font2);
		
		btnIngresar.setTypeface(font);
		btnSalir.setTypeface(font);
	 //***********************************************/
		 
		sincronizarValores();
		
	}
 
	

	private void cargarMenu() {
		
		Intent	objIntent=new Intent(Principal.this,Opciones.class);
		Bundle	objbundle=new Bundle();
				objbundle.putInt("idUsuario", idUsuario);
				objIntent.putExtras(objbundle);
		startActivity(objIntent);
		finish();
	} 
	
	private void limpiar(){
		txtUsuario.setText("");
		txtPassword.setText("");
	}
	
	private void salir(){
		usuarioDao.close();
		finish();
	}
 
	@Override
	public void onClick(View v) {
		
	    if(btnIngresar==v){   validarEntrada();  }
       
	    if(btnSalir==v){  salir(); }
		
	} 
	
	public void validarEntrada() { 
		
		 new asyncValidarEntrada().execute();  
		
	}
	
	private void sincronizarValores() {
		
		new asyncSincronizar().execute();  
	}
	
	class asyncValidarEntrada extends     AsyncTask <Void,Void,Void> 
	 {   
		private ProgressDialog progressDialog; 
        private String Usuario,Password;
        
        protected void onPreExecute() 
         
         {    progressDialog = ProgressDialog.show( Principal.this,"Verificando", "Espere unos segundos...", 
       		  true);  
        } 
        
       @Override  
         protected Void doInBackground(Void... arg0) 
         {   
    	    try { Usuario=txtUsuario.getText().toString();
    			 Password=txtPassword.getText().toString();
    			 
    			 idUsuario=usuarioDao.autentificarSQLITE(Usuario, Password);
    	    } catch (Exception e)  {  	 }
             
    	   return null;  
         }  
       
        @Override  
          protected void onPostExecute(Void result)
         { 
        	if(idUsuario!=0){
        		
        		 progressDialog.setTitle("Verificando"); 
        		 usuarioDao.close();
    			
    			 cargarMenu();
    		}else {
    			Toast toast2 =   Toast.makeText(getApplicationContext(),
    		                    "Usuario Incorrecto", Toast.LENGTH_LONG);
    		    toast2.setGravity(Gravity.CENTER|Gravity.LEFT,210,90);
    		    toast2.show();
    		    
    		    limpiar();
    		}
        	
        	  progressDialog.dismiss();
          }
   }   
	
	class asyncSincronizar extends     AsyncTask <Void,Void,Void> 
	 {   
		 
       protected void onPreExecute() 
        {    }  
       
      @Override  
        protected Void doInBackground(Void... arg0) 
        {   
	   	   try {  
	   		   estadoSincronizacion = usuarioDao.Sincronizar(getApplicationContext());
	   	   } catch (Exception e)  {   }
	            
   	       return null;  
       }  
      
       @Override  
       protected void onPostExecute(Void result)
       { 
    	   if(estadoSincronizacion==0){
    		   
    		   Toast toast2 =   Toast.makeText(getApplicationContext(),
		                    "Error al Sincronizar", Toast.LENGTH_LONG);
		       toast2.setGravity(Gravity.CENTER|Gravity.LEFT,210,90);
		       toast2.show();
		    	
			}else{
    		   
    		   Toast toast2 =   Toast.makeText(getApplicationContext(),
		                    " Sincronizado", Toast.LENGTH_LONG);
		       toast2.setGravity(Gravity.CENTER|Gravity.LEFT,210,90);
		       toast2.show();
				    	
					
			}
    	   
   		}
       	    
  }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	        case R.id.menu_sincronizar:
	        {   sincronizarValores();
	            return true;
	        }
	        default:
	            return super.onOptionsItemSelected(item);
		}
	}
	
}


