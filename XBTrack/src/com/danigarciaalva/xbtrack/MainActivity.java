package com.danigarciaalva.xbtrack;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	 private String[] drawerListViewItems;
	    private DrawerLayout drawerLayout;
	    private ListView drawerListView;
	    private ActionBarDrawerToggle actionBarDrawerToggle;


	        @Override
	        protected void onCreate(Bundle savedInstanceState) {
	                super.onCreate(savedInstanceState);
	                setContentView(R.layout.activity_main);
	                
	                drawerListViewItems = getResources().getStringArray(R.array.items);
	                drawerListView = (ListView) findViewById(R.id.left_drawer);
	                drawerListView.setAdapter(new ArrayAdapter<String>(this,
	                R.layout.drawer_listview_item, drawerListViewItems));
	                drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

	                actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.drawable.ic_drawer,R.string.drawer_open,R.string.drawer_close);
	                drawerLayout.setDrawerListener(actionBarDrawerToggle);
	                getActionBar().setDisplayHomeAsUpEnabled(true); 
	                drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
	                drawerListView.setOnItemClickListener(new DrawerItemClickListener());
	        }

	        @Override
	    protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	         actionBarDrawerToggle.syncState();
	    }

	        @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	        super.onConfigurationChanged(newConfig);
	        actionBarDrawerToggle.onConfigurationChanged(newConfig);
	    }

	        
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
	        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
	            return true;
	        }
            return super.onOptionsItemSelected(item);
        }
        
        private class DrawerItemClickListener implements ListView.OnItemClickListener {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
	            	Fragment fragment = new Mapa();
	                FragmentManager fragmentManager = getFragmentManager();
	                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
	
	                // update selected item and title, then close the drawer
	                drawerListView.setItemChecked(position, true);
                    drawerLayout.closeDrawer(drawerListView);
            }
        }
}
