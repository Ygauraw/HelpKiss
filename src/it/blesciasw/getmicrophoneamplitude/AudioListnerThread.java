package it.blesciasw.getmicrophoneamplitude;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaRecorder;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.util.Log;

@SuppressLint("NewApi")
public class AudioListnerThread extends Thread implements OnMessageSended, OnInitListener,OnUtteranceCompletedListener{
	private MediaRecorder _mediaRecorder;
	private boolean isRequestStop= false;
	private Sampling _sampling;
	private TextToSpeech _tts;
	private boolean _isSpeaking;
	
	
	public void setRequestStop(boolean isRequestStop) {
		this.isRequestStop = isRequestStop;
	}

	@SuppressWarnings("deprecation")
	public AudioListnerThread(Context _context){
		
		
		_tts = new TextToSpeech(_context,this);
		this.isRequestStop = false;
		_sampling = new Sampling(10000, 1000);
		SoundAnalyzer._onMessageSended = this;
		
		_mediaRecorder = new MediaRecorder();
		_mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		_mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		_mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		_mediaRecorder.setOutputFile("dev/null");
		
		try {
			_mediaRecorder.prepare();
		} catch (IllegalStateException e) {
			Log.e(ApplicationContext.TAGLOG, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Log.e(ApplicationContext.TAGLOG, e.getMessage());
			e.printStackTrace();
		}
		Log.i(ApplicationContext.TAGLOG, "Media Recorder Prepared!");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//super.run();
		_mediaRecorder.start();
		_sampling.set_mediaRecorder(this._mediaRecorder);
		while(!isRequestStop) 
		{
			if(!_sampling.isRunning() && !_isSpeaking){
				_sampling.start();	
			
			}
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		//Is request the stop of this thread
		try {
			Log.i(ApplicationContext.TAGLOG, "Thread Finished!");
			_sampling.cancel();
			_mediaRecorder.release();
			this.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onMessageSended() {
		_isSpeaking = true;
		HashMap<String, String> myHash = new HashMap<String, String>();
        myHash.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,"done");
		_tts.speak("Richesta di aiuta inviata correttamente!", TextToSpeech.QUEUE_ADD,myHash);
	
	}

	@Override
	public void onInit(int status) {
		_tts.setLanguage(Locale.ITALIAN);
		_tts.setOnUtteranceCompletedListener(this);
		
	}

	@Override
	public void onUtteranceCompleted(String utteranceId) {
		_mediaRecorder.getMaxAmplitude();
		_mediaRecorder.getMaxAmplitude();
		_isSpeaking = false;
		
	}

	

}
