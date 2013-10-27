package mx.xackaton.views;
import com.example.android.effectivenavigation.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


/**
 *
 * @author master
 */
public class ActivarFragment extends Fragment{
	
		ImageButton start_stop, pause;
		
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.bike_fragment, container, false);

            init(rootView);
            return rootView;
        }
        
        private void init(View v){
        	start_stop = (ImageButton)v.findViewById(R.id.start_stop);
        	pause = (ImageButton)v.findViewById(R.id.pause);
        }
}
