package it.blesciasw.getmicrophoneamplitude;

import java.util.Arrays;





import android.util.Log;


public class SoundAnalyzer {

	private AudioSampleBean _audioSampleBean;
	private CustomSparseArray<Integer> _originalBuffer;
	private CustomSparseArray<Double> _dbBuffer;
	private MessageManager _messageManager;
	public static OnMessageSended _onMessageSended;
	
	public void set_originalBuffer(CustomSparseArray<Integer> originalBuffer) {
		this._originalBuffer = originalBuffer;
	}
	
	public SoundAnalyzer(){
		_messageManager = new MessageManager();
		
	}
	
	public void CalculateSampleProperties(){
		
		 _audioSampleBean = new AudioSampleBean();
		 _dbBuffer = new CustomSparseArray<Double>();
		 
		  TrasformBufferIntoDecibel();
		 _audioSampleBean.set_bufferInDb(_dbBuffer);
		 
		 double average = CalculateAverage();
		 _audioSampleBean.set_average(average);
		 
		 PeakBean peaks[] = FindPeaks();
		 
		 boolean valid = areValidPeaks(peaks[0],peaks[1],average);
		 
		 if(valid){
			 _audioSampleBean.set_timing(Math.abs(peaks[1].getIndex() - peaks[0].getIndex()) * 500);
			 if(ApplicationContext.isSendMessageEnable){
				 _messageManager.SendMessageToNumbers();
				 _onMessageSended.onMessageSended();
			 }
		 }
			  
		 
	}
	
	private boolean areValidPeaks(PeakBean firstBean, PeakBean secondBean, double average){
		
		double threshold = ApplicationContext.threshold;
		boolean result = false;
		
		double firstBeanValue = firstBean.getValue();
		double secondBeanValue = secondBean.getValue();
		
		boolean first = ( firstBeanValue - average) > threshold;
		boolean second = (secondBeanValue- average) > threshold;
		
		if( average != Double.POSITIVE_INFINITY && average != Double.NEGATIVE_INFINITY)
			result = first&&second;
		
		Log.i(ApplicationContext.TAGLOG, String.valueOf((firstBean.getValue() - average))+"|"+String.valueOf((secondBean.getValue() - average)));
		
	
		return result;
	}
	
	/**
	 * Calcola la media del vettore
	 * @return
	 */
	private double CalculateAverage(){
		double sum =0;
		
		for(int idx=0;idx<_dbBuffer.size();idx++){
			sum+=_dbBuffer.get(idx);
		}
		
		return sum/_dbBuffer.size();
	}
	
	/**
	 * Trasformo il vettore rappresentato in ampiezza in decibel
	 */
	private void TrasformBufferIntoDecibel(){
		double temp =0;
		for(int idx=0; idx<_originalBuffer.size();idx++){
			temp =  (double) 20*Math.log10(_originalBuffer.get(idx)/32768.0);
			_dbBuffer.put(idx,temp) ;
			//Log.i(ApplicationContext.TAGLOG,idx +":" +String.valueOf(temp));
		 }
	
	}

	/**
	 * Ritorno un vettore di picchi su una base decimale
	 * @return
	 */
	private PeakBean[] FindPeaks(){
		PeakBean peaks[] = new PeakBean[2];
		PeakBean firstPeak = new PeakBean();
		PeakBean secondPeak = new PeakBean();
		
		double temp[] = new double[_dbBuffer.size()];
		for(int idx =0;idx< _dbBuffer.size();idx++)
			temp[idx] = _dbBuffer.get(idx);
		
		Arrays.sort(temp);
		
		firstPeak.setValue(temp[temp.length-1]);
		secondPeak.setValue(temp[temp.length-2]);
		
		for(int idx =0;idx<_dbBuffer.size();idx++)
		{
			if(_dbBuffer.get(idx) == firstPeak.getValue())
				firstPeak.setIndex(idx);
			if(_dbBuffer.get(idx) == secondPeak.getValue())
				secondPeak.setIndex(idx);
		}
			
		
		peaks[0] = firstPeak;
		peaks[1] = secondPeak;
		
		return peaks;
		
		
	}
	
	
	
}
