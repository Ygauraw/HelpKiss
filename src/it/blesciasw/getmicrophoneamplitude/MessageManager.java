package it.blesciasw.getmicrophoneamplitude;


import android.telephony.SmsManager;

public class MessageManager {

	private SmsManager _smsManager;
	private String MSG_TEXT = "Richiesta di aiuto!";
	
	public MessageManager(){
		_smsManager = SmsManager.getDefault();
	}
	
	public void SendMessageToNumbers(){
		for(String number : ApplicationContext.numbers){
			if(number.length()> 0){
				_smsManager.sendTextMessage(number, null, MSG_TEXT, null, null);
			}
		}
			
	}
}
