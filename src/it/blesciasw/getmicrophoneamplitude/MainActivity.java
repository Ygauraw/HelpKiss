package it.blesciasw.getmicrophoneamplitude;



import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{

	private AudioListnerThread _audioListnerThread;
	private Button _uiBtnRecognition;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		_uiBtnRecognition = (Button) findViewById(R.id.uiBtnRecognition);
		_uiBtnRecognition.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		LoadConfiguration();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent i = new Intent(this, Preferences.class);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void LoadConfiguration(){
		SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		ArrayList<String> numbers = new ArrayList<String>(); 	
		boolean isSendMessageEnable;
		int threshold;
		/*for(Map.Entry<String,?> setting : myPrefs.getAll().entrySet() ){
			if(setting.getValue() instanceof String && setting. ){
				numbers.add(setting.getValue().toString());
			}
		}*/
		if(myPrefs.getString("phone1", null)!=null)numbers.add(myPrefs.getString("phone1", null));
		if(myPrefs.getString("phone2", null)!=null)numbers.add(myPrefs.getString("phone2", null));
		if(myPrefs.getString("phone3", null)!=null)numbers.add(myPrefs.getString("phone3", null));
		if(myPrefs.getString("phone4", null)!=null)numbers.add(myPrefs.getString("phone4", null));
		if(myPrefs.getString("phone5", null)!=null)numbers.add(myPrefs.getString("phone5", null));
		
		isSendMessageEnable = myPrefs.getBoolean("sendsms", false);
		threshold = Integer.parseInt(myPrefs.getString("threshold", "0"));
		
		ApplicationContext.numbers = numbers;
		ApplicationContext.isSendMessageEnable = isSendMessageEnable;
		ApplicationContext.threshold = threshold;
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.uiBtnRecognition){
			if( _uiBtnRecognition.getTag().toString() != "1"){
				_audioListnerThread = new AudioListnerThread(getApplicationContext());
				
				_audioListnerThread.start();
				_uiBtnRecognition.setTag("1");
				_uiBtnRecognition.setText("Ferma riconoscimento");
			}else{
				_audioListnerThread.setRequestStop(true);
				_uiBtnRecognition.setTag("0");
				_uiBtnRecognition.setText("Avvia Riconoscimento");
			}
			
		}
		
	}
}
