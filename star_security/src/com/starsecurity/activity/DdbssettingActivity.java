import android.view.Menu;
import android.view.MenuInflater    
    /***
     * 载入Menu
     */
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.ddbssettingactivitymenu, menu);
		return true;
	}
}
