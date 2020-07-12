package application;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;

public class SerialTempClass {

	public static void main(String[] args) throws InterruptedException, IOException {
		// determine which serial port to use
	    SerialPort ports[] = SerialPort.getCommPorts();
	    System.out.println("Select a port:");
	    int i = 1;
	    for(SerialPort port : ports) {
	            System.out.println(i++ + ". " + port.getSystemPortName());
	    }
	    Scanner s = new Scanner(System.in);
	    int chosenPort = 1;

	    // open and configure the port
	    SerialPort port = ports[chosenPort - 1];
	    if(port.openPort()) {
	            System.out.println("Successfully opened the port.");
	    } else {
	            System.out.println("Unable to open the port.");
	            s.close();
	            return;
	    }
	    port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
	    port.setBaudRate(115200);
	    
	    
	   
	    // enter into an infinite loop that reads from the port and updates the GUI
	    Scanner data = new Scanner(port.getInputStream());
	    BufferedOutputStream writer=new BufferedOutputStream(port.getOutputStream());
	    
	    String input="$\n";
	    System.out.println(data.nextLine());
	    System.out.println(data.nextLine());
	    
	    
		port.writeBytes(input.getBytes(), input.length());
	    //writer.write(input.getBytes());
	    //writer.flush();
	    
	    System.out.println(data.nextLine());
	    input="$\n";
	    writer.write(input.getBytes());
	    writer.flush();
	    System.out.println(data.nextLine());
	    System.out.println(data.nextLine());
	    System.out.println(data.nextLine());
	    
        
        
        writer.write(input.getBytes());
        writer.flush();
	    
	    
	    
	    
	    
	   
	    while(port.isOpen()) {
	           	
	           	
	    
	    } 
	    writer.close();
	    data.close();
	    s.close();
	}
	
}
