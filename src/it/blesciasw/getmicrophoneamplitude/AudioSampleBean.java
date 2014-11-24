package it.blesciasw.getmicrophoneamplitude;

import android.util.Log;

public class AudioSampleBean {

	private static double _average;
	private static CustomSparseArray<Double> _bufferInDb;
	private static PeakBean _firstPeak;
	private static PeakBean _secondPeak;
	private static int _timing;
	
	public double get_average() {
		return _average;
	}

	public void set_average(double average) {
		Log.i(ApplicationContext.TAGLOG,String.valueOf(average));
		_average = average;
	}
	
	public CustomSparseArray<Double> get_bufferInDb() {
		return _bufferInDb;
	}

	public void set_bufferInDb(CustomSparseArray<Double> bufferInDb) {
		_bufferInDb = bufferInDb;
	}

	public PeakBean get_firstPeak() {
		return _firstPeak;
	}

	public void set_firstPeak(PeakBean _firstPeak) {
		AudioSampleBean._firstPeak = _firstPeak;
	}

	public PeakBean get_secondPeak() {
		return _secondPeak;
	}

	public  void set_secondPeak(PeakBean _secondPeak) {
		AudioSampleBean._secondPeak = _secondPeak;
	}

	public  int get_timing() {
		return _timing;
	}

	public  void set_timing(int _timing) {
		AudioSampleBean._timing = _timing;
	}



	
	
	
}
