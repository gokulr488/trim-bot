package TrimBot.readers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MapForReaders {
	private Map<ReadModeEnum, ReaderInterface> readerMap = new HashMap<>();
	
	public MapForReaders(){
		readerMap.put(ReadModeEnum.MILLING, new MillingReader());
		readerMap.put(ReadModeEnum.THREE_D_PRINTING, new ThreeDPrintingReader());
	}
	
	public ReaderInterface initialiseReader(ReadModeEnum readMode,File file) {
		ReaderInterface interfaceMapValue=readerMap.getOrDefault(readMode, new MillingReader());
		
		
		return interfaceMapValue;
		
	}
	
}
