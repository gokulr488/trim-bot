package TrimBot.readers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import TrimBot.model.GCodeObject;
import TrimBot.model.InputFileLines;
import TrimBot.model.MCodeObject;

public class MillingReader implements ReaderInterface {
	public static Map<Integer, InputFileLines> inputFileMap = new HashMap<>();
	
	private File inputFile;
	private FileReader filereader;
	private BufferedReader bufferedReader;
	private int lineNumber;
	
	
	@Override
	public void readInputFile(File file) throws IOException {
		this.inputFile=file;
		this.filereader=new FileReader(inputFile);
		this.bufferedReader=new BufferedReader(this.filereader);
		String line="";
		lineNumber=1;
		while ((line=bufferedReader.readLine())!=null) {
			if(line.length()>0)
			lineSepertor(line,lineNumber);
			lineNumber++;
		}
		this.bufferedReader.close();

	}

	private void lineSepertor(String line,int lineNumber) {
		if (line.charAt(0)=='G') {
			GCodeObject gCodeObj=new GCodeObject();
			gCodeObj.setgCode(line);
			inputFileMap.put(lineNumber, gCodeObj);
		}
		else if(line.charAt(0)=='M') {
			MCodeObject mCodeObj=new MCodeObject();
			mCodeObj.setmCode(line);
			inputFileMap.put(lineNumber, mCodeObj);
		}
		
	}

	@Override
	public InputFileLines getLine(int lineNumber) {
		
		return inputFileMap.get(lineNumber);
	}
	@Override
	public Map<Integer, InputFileLines> getInputFileMap() {
		return inputFileMap;
	}

	@Override
	public int getNoOfLines() {
		return lineNumber;
	}


	

}
