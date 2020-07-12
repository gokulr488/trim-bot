package TrimBot.connection;

import java.util.concurrent.BlockingQueue;

import com.fazecast.jSerialComm.SerialPort;

public class PortWritesHandler implements Runnable {
	
	private static BlockingQueue<String> serialOutQueue;
	private static SerialPort port;
	
	public PortWritesHandler(BlockingQueue<String> serialOutQueue, SerialPort port) {
		PortWritesHandler.serialOutQueue=serialOutQueue;
		PortWritesHandler.port=port;
	}

	@Override
	public void run() {
		while(port.isOpen()) {    //!serialOutQueue.isEmpty() && (0==port.bytesAvailable())
			
			try {
				String toWrite = serialOutQueue.take()+"\n";
				port.writeBytes(toWrite.getBytes(), toWrite.length());
				//System.out.println("writing to Port: "+toWrite);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		System.out.println("Port Writing Queue dead");
	}

}
