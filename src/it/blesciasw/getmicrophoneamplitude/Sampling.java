package it.blesciasw.getmicrophoneamplitude;

import android.annotation.SuppressLint;
import android.media.MediaRecorder;
import android.os.CountDownTimer;
import android.util.Log;
//import android.util.SparseArray;

@SuppressLint("UseSparseArrays")
public class Sampling extends CountDownTimer {

	private boolean isRunning = false;
	private MediaRecorder _mediaRecorder;
	private CustomSparseArray<Integer> _buffer;
	private SoundAnalyzer _soundAnalyzer;
	private int _indices=0;
	
	
	public void set_mediaRecorder(MediaRecorder _mediaRecorder) {
		this._mediaRecorder = _mediaRecorder;
	}

	public boolean isRunning() {
		return isRunning;
		
	}
	
	public Sampling(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
		// TODO Auto-generated constructor stub
		_buffer = new CustomSparseArray<Integer>();
		_soundAnalyzer = new SoundAnalyzer();
		//_bufferSerializer = new BufferSerializer();
	}

	@Override
	public void onTick(long millisUntilFinished) {
		isRunning = true;
		int amplitude = _mediaRecorder.getMaxAmplitude();
		_buffer.put(_indices, amplitude);
		_indices++;
		 Log.i(ApplicationContext.TAGLOG, amplitude+"");
		
	}

	@Override
	public void onFinish() {
		
		_soundAnalyzer.set_originalBuffer(_buffer);
		_soundAnalyzer.CalculateSampleProperties();
		
		
		
		_buffer.clear();
		isRunning = false;
		_indices =0;
		Log.i(ApplicationContext.TAGLOG,"Finished");
		
	}

	

}
