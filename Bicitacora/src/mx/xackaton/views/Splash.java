package mx.xackaton.views;


import com.example.android.effectivenavigation.R;

import mx.xackaton.bicitacora.MainActivity;
import mx.xackaton.bicitacora.global;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class Splash extends Activity {

	protected boolean _active = true;
    protected int _splashTime = 1500;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        Thread splashTread = new Thread() {
			@Override
            public void run() {
                try { 
                    int waited = 0;
                    while(_active && (waited < _splashTime)) {
                        sleep(100);
                        if(_active) {
                            waited += 100;
                        }
                    }
                } catch(InterruptedException e) {
                } finally {
                    finish();
                    SharedPreferences prefs = getSharedPreferences("Bicitacora", MODE_PRIVATE);
                    String token = prefs.getString("token", "null");
                    Intent i = null;
                    if (token.equals("null"))
                    	i = new Intent(getBaseContext(), Login.class);
                    else{ 
                    	i = new Intent(getBaseContext(), MainActivity.class);
                    	global.token = token;
                    }
                    startActivity(i);
                    this.interrupt();
                }
            }
        };
        splashTread.start();
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            _active = false;
        }
        return true;
    }
}
