package TrimBot;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.fazecast.jSerialComm.SerialPort;

import TrimBot.connection.PositionHandler;
import TrimBot.connection.SerialConnectionManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TrimBot extends Application {
	private static SerialPort port;
	private static BlockingQueue<String> serialOutQueue = new LinkedBlockingQueue<>(1);

	@Override
	public void start(Stage primaryStage) {
		
		//MachiningProcessHandler machiningProcessHandler =new MachiningProcessHandler(serialOutQueue,posHandler,reader.getInputFileMap(),reader.getNoOfLines());
		/*
		  try { Thread.sleep(3000); machiningProcessHandler.startMachining(); } catch
		  (InterruptedException e1) { // TODO Auto-generated catch block
		  e1.printStackTrace(); }
		 */

		try {
			VBox root =(VBox)FXMLLoader.load(getClass().getResource("./controllers//home.fxml"));
			Scene scene = new Scene(root, 1400, 800);
			scene.getStylesheets().add(getClass().getResource("./controllers//application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private PositionHandler connectPortAndGetPosition() {

		SerialConnectionManager connectionManager = new SerialConnectionManager(serialOutQueue);
		port = connectionManager.getPort();
		port.setBaudRate(115200);
		connectionManager.startWritingToPort();
		PositionHandler posHandler = new PositionHandler(port, serialOutQueue);
		if (posHandler.startPositionDetection()) {
			System.out.println("Port opened. Starting position detection");
		} else {
			System.out.println("Unable to open port.");
		}
		return posHandler;

		/*
		 * while(port.isOpen()) { MPosObject mPos=new MPosObject(); WPosObject wPos=new
		 * WPosObject(); try { mPos=posHandler.getmPos(); wPos=posHandler.getwPos();
		 * if(mPos!=null) { System.out.println("X= "+mPos.getX());
		 * System.out.println("Y= "+mPos.getY()); System.out.println("Z= "+mPos.getZ());
		 * 
		 * Thread.sleep(500); } } catch (InterruptedException e) {
		 * 
		 * e.printStackTrace(); } }
		 */
	}

	public static void main(String[] args) {
		launch(args);
	}
}
