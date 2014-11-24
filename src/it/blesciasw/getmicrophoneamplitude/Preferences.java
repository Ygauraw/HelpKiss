package it.blesciasw.getmicrophoneamplitude;


import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

public class Preferences extends PreferenceActivity implements OnPreferenceChangeListener {

	EditTextPreference _uiTxtphone1,_uiTxtphone2,_uiTxtphone3,_uiTxtphone4,_uiTxtphone5, _uiThreshold;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.preferences);
		 
		_uiTxtphone1 = (EditTextPreference)findPreference("phone1");
		_uiTxtphone2 = (EditTextPreference)findPreference("phone2");
		_uiTxtphone3 = (EditTextPreference)findPreference("phone3");
		_uiTxtphone4 = (EditTextPreference)findPreference("phone4");
		_uiTxtphone5 = (EditTextPreference)findPreference("phone5");
		_uiThreshold = (EditTextPreference)findPreference("threshold");
		
		_uiTxtphone1.setOnPreferenceChangeListener(this);
		_uiTxtphone2.setOnPreferenceChangeListener(this);
		_uiTxtphone3.setOnPreferenceChangeListener(this);
		_uiTxtphone4.setOnPreferenceChangeListener(this);
		_uiTxtphone5.setOnPreferenceChangeListener(this);
		_uiThreshold.setOnPreferenceChangeListener(this);
		
		setSummary();
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		EditTextPreference changed = (EditTextPreference)preference;
		changed.setSummary(String.valueOf(newValue));
		changed.setText(String.valueOf(newValue));
		return false;
	}
	
	private void setSummary(){
		_uiTxtphone1.setSummary(_uiTxtphone1.getText());
		_uiTxtphone2.setSummary(_uiTxtphone2.getText());
		_uiTxtphone3.setSummary(_uiTxtphone3.getText());
		_uiTxtphone4.setSummary(_uiTxtphone4.getText());
		_uiTxtphone5.setSummary(_uiTxtphone5.getText());
		_uiThreshold.setSummary(_uiThreshold.getText());
		
	}
	

}
