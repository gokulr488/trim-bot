package TrimBot.connection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.fazecast.jSerialComm.SerialPort;

import TrimBot.model.MPosObject;
import TrimBot.model.PosObj;
import TrimBot.model.WPosObject;
import TrimBot.readers.ReadModeEnum;

public class PositionHandler {

	private SerialPort port;
	private BlockingQueue<String> serialOutQueue;
	private BlockingQueue<PosObj> posObjQueue = new LinkedBlockingQueue<>(8);
	private MPosObject mPos;
	private WPosObject wPos;

	public PositionHandler(SerialPort port, BlockingQueue<String> serialOutQueue) {
		this.port = port;
		this.serialOutQueue = serialOutQueue;
	}

	public boolean startPositionDetection() {
		if (port.isOpen()) {
			new Thread(new CurrentPosition(serialOutQueue, posObjQueue, port)).start();
			return true;
		} else {
			return false;
		}

	}

	public MPosObject getmPos() throws InterruptedException {
		try {
			setPos();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mPos;
	}

	public WPosObject getwPos() {
		try {
			setPos();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wPos;
	}

	private void setPos() throws InterruptedException {

		if (!posObjQueue.isEmpty()) {
			if (ReadModeEnum.W_POS == posObjQueue.element().getType()) {
				this.wPos = (WPosObject) posObjQueue.take();
			} else if (ReadModeEnum.M_POS == posObjQueue.element().getType()) {
				this.mPos = (MPosObject) posObjQueue.take();

			}
		}

	}

	public SerialPort getPort() {
		return port;
	}

}
