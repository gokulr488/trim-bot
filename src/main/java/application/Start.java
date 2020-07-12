package application;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JSlider;

import TrimBot.readers.MapForReaders;
import TrimBot.readers.ReadModeEnum;
import TrimBot.readers.ReaderInterface;


public class Start {

	public static void main(String[] args) {
		MapForReaders readerMap =new MapForReaders();
		File file=new File("pylearn");
		ReaderInterface reader =readerMap.initialiseReader(ReadModeEnum.MILLING, file);
		try {
			reader.readInputFile(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		JFrame window =new JFrame();
		JSlider slider = new JSlider();
        slider.setMaximum(1023);
        window.add(slider);
        window.pack();
        window.setVisible(true);
       
       
        

	}

}
