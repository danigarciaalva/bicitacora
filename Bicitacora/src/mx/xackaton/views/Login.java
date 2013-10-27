package mx.xackaton.views;

import mx.xackaton.bicitacora.MainActivity;
import mx.xackaton.bicitacora.SERVER;
import mx.xackaton.bicitacora.SERVER.UDP;
import mx.xackaton.bicitacora.global;

import com.example.android.effectivenavigation.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity{

	Button entrar, registro;
	EditText correo, password;
	UDP u;
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_activity);
		
		correo = (EditText)findViewById(R.id.correo);
		password = (EditText)findViewById(R.id.password);
		registro = (Button)findViewById(R.id.registro);
		entrar = (Button)findViewById(R.id.entrar);
		entrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(correo.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty()){
					Toast.makeText(getBaseContext(), "Por favor introduce los campos correctamente", Toast.LENGTH_SHORT).show();
				}else{
					Thread peticion = new Thread(new Runnable() {
						
						@Override
						public void run() {
							SERVER server = new SERVER();
							u = server.login(correo.getText().toString().trim(), password.getText().toString().trim());
						}
					});
					peticion.start();
					try{
						peticion.join();
					}catch(Exception e){}
					if(u.CODIGO != 0){
						correo.setText("");
						password.setText("");
						Toast.makeText(getBaseContext(), u.MSG, Toast.LENGTH_SHORT).show();										
					}else{
						global.token = u.TOKEN;
						SharedPreferences prefs = getSharedPreferences("Bicitacora", MODE_PRIVATE);
						SharedPreferences.Editor editor = prefs.edit();
						editor.putString("token", u.TOKEN);
						editor.commit();
						System.out.println(u.TOKEN);
						finish();
						Intent i = new Intent(getBaseContext(), MainActivity.class);
						startActivity(i);
					}
				}
			}
		});
		registro.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), Registro.class);
				startActivity(i);
			}
		});
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		return true;
	}
	
}
