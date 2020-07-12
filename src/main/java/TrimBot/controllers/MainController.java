package TrimBot.controllers;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.fazecast.jSerialComm.SerialPort;

import TrimBot.connection.PositionHandler;
import TrimBot.connection.SerialConnectionManager;
import TrimBot.model.MPosObject;
import TrimBot.model.WPosObject;
import TrimBot.readers.MachiningProcessHandler;
import TrimBot.readers.MapForReaders;
import TrimBot.readers.ReadModeEnum;
import TrimBot.readers.ReaderInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class MainController {

	@FXML
	private ChoiceBox<String> portChoiceBox;

	@FXML
	private ChoiceBox<Integer> baudRateChoiceBox;

	@FXML
	private Button refreshPortBtn;

	@FXML
	private Label wLabelX;

	@FXML
	private Label wLabelZ;

	@FXML
	private Label wLabelY;

	@FXML
	private Label mLabelX;

	@FXML
	private Label mLabelZ;

	@FXML
	private Label mLabelY;

	@FXML
	private Label currentStateLabel;
	
	@FXML
    private Label fileNameLabel;

	@FXML
	private Button chooseFileButton;

	@FXML
	private Button connectPortButton;
	
	@FXML
	private Button startButton;

	@FXML
	void chooseInputFile(MouseEvent event) {
		FileChooser fileChooser = new FileChooser();
		Window primaryStage = null;
		MapForReaders readerMap = new MapForReaders();
		File selectedFile = fileChooser.showOpenDialog(primaryStage);
		reader = readerMap.initialiseReader(ReadModeEnum.MILLING, selectedFile);
		fileNameLabel.setText(selectedFile.getName());
		
		try {
			reader.readInputFile(selectedFile);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	@FXML
	void onClickedStartButton(MouseEvent event) {
		
		MachiningProcessHandler machiningProcessHandler = new MachiningProcessHandler(serialOutQueue, posHandler,
				reader.getInputFileMap(), reader.getNoOfLines());

		try {
			Thread.sleep(3000);
			machiningProcessHandler.startMachining();
		} catch (InterruptedException e1) { // TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	ReaderInterface reader;
	private static BlockingQueue<String> serialOutQueue = new LinkedBlockingQueue<>(1);
	private SerialConnectionManager connectionManager = new SerialConnectionManager(serialOutQueue);
	PositionHandler posHandler;

	@FXML
	void connectBtnOnClicked(MouseEvent event) {
		String portChoice = portChoiceBox.getValue();
		Integer baudRate;
		if ((baudRate = baudRateChoiceBox.getValue()) != null) {
			connectionManager.setPort(Character.getNumericValue(portChoice.charAt(0)), baudRate);
		} else {
			connectionManager.setPort(Character.getNumericValue(portChoice.charAt(0)), 115200);
		}
		connectionManager.startWritingToPort();
		posHandler = connectionManager.startPositionDetection();
	}

	@FXML
	void refreshBtnOnClicked(MouseEvent event) {
		refreshPortList();
	}

	private void refreshPortList() {
		portChoiceBox.getItems().clear();
		int i = 1;
		for (SerialPort port : connectionManager.getAvailablePorts()) {
			portChoiceBox.getItems().add(i++ + ". " + port.getSystemPortName());
			port.getSystemPortName();
		}
		baudRateChoiceBox.getItems().clear();
		baudRateChoiceBox.getItems().add(2400);
		baudRateChoiceBox.getItems().add(4800);
		baudRateChoiceBox.getItems().add(9600);
		baudRateChoiceBox.getItems().add(38400);
		baudRateChoiceBox.getItems().add(115200);
		baudRateChoiceBox.getItems().add(230400);
	}

	public void positionUpdater(MPosObject mPos, WPosObject wPos) {
		currentStateLabel.setText("testing");
		System.out.println(wLabelX.getText());
		wLabelX.setText(String.valueOf(wPos.getX()));
		wLabelY.setText(String.valueOf(wPos.getY()));
		wLabelZ.setText(String.valueOf(wPos.getZ()));
		mLabelX.setText(String.valueOf(mPos.getX()));
		mLabelX.setText(String.valueOf(mPos.getY()));
		mLabelX.setText(String.valueOf(mPos.getZ()));
		currentStateLabel.setText(String.valueOf(mPos.getCurrentState()));
	}

}
