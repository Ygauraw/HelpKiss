package it.blesciasw.getmicrophoneamplitude;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import android.os.Environment;
import android.util.SparseArray;

public class BufferSerializer {

	private File _file;
	private FileWriter _fileWriter;
	
	public void WriteBufferToFile(SparseArray<Integer> _buffer) throws IOException{
		_file = new File(Environment.getExternalStorageDirectory(), Calendar.getInstance().getTimeInMillis()+".txt");
		_file.createNewFile();
		_fileWriter = new FileWriter(_file, true);
		
		for(int idx =0 ;idx<_buffer.size();idx++){
			_fileWriter.write(_buffer.keyAt(idx)+";"+_buffer.valueAt(idx)+"\r\n");
		}
		_fileWriter.flush();
		_fileWriter.close();
	}
	
}
