package TrimBot.connection;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import com.fazecast.jSerialComm.SerialPort;

public class SerialConnectionManager {

	private static BlockingQueue<String> serialOutQueue;
	private static SerialPort port;
	private SerialPort[] availablePorts;

	public SerialConnectionManager(BlockingQueue<String> serialOutQueue) {
		SerialConnectionManager.serialOutQueue = serialOutQueue;
		getPorts();
	}

	private void getPorts() {
		availablePorts = SerialPort.getCommPorts();

		/*
		 * int i = 1; for (SerialPort port : ports) { System.out.println(i++ + ". " +
		 * port.getSystemPortName()); availablePorts.add(port); }
		 */

	}

	public void setPort(int choosenPort, int choosenBaudRate) {
		port = availablePorts[choosenPort - 1];
		if (port.openPort()) {

			port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
			if (choosenBaudRate > 0) {
				port.setBaudRate(choosenBaudRate);
			} else {
				port.setBaudRate(115200);
			}
			System.out.println("Successfully opened the port.");
		} else {
			System.out.println("Unable to open the port.");

		}
	}

	public void startWritingToPort() {
		new Thread(new PortWritesHandler(serialOutQueue, port)).start();

	}

	public PositionHandler startPositionDetection() {
		PositionHandler posHandler = new PositionHandler(port, serialOutQueue);
		if (posHandler.startPositionDetection()) {
			System.out.println("Port opened. Starting position detection");
		} else {
			System.out.println("Unable to open port.");
		}
		return posHandler;
	}

		public SerialPort getPort() {
		SerialPort ports[] = SerialPort.getCommPorts();
		System.out.println("Select a port:");
		int i = 1;
		for (SerialPort port : ports) {
			System.out.println(i++ + ". " + port.getSystemPortName());

		}
		Scanner scanner = new Scanner(System.in);
		int chosenPort = scanner.nextInt();

		// open and configure the port
		port = ports[chosenPort - 1];
		if (port.openPort()) {
			System.out.println("Successfully opened the port.");
			port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
			scanner.close();
			return port;
		} else {
			System.out.println("Unable to open the port.");

		}
		scanner.close();
		return null;
	}

	public SerialPort[] getAvailablePorts() {
		return availablePorts;
	}

}
