package TrimBot.readers;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import TrimBot.model.InputFileLines;

public interface ReaderInterface {
	void readInputFile(File file) throws IOException;
	InputFileLines getLine(int lineNumber);
	Map<Integer, InputFileLines> getInputFileMap();
	int getNoOfLines();
	
}
