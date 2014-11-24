package it.blesciasw.getmicrophoneamplitude;

import android.util.SparseArray;

public class CustomSparseArray<E> extends SparseArray<E> {

	public int[] values(){
		int values[] = new int[super.size()];
		for(int idx = 0; idx<super.size();idx++){
			
			values[idx] = (Integer) super.get(idx);
			
		}
		
		return values;
	}
}
