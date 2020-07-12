package TrimBot.connection;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

import com.fazecast.jSerialComm.SerialPort;

import TrimBot.model.MPosObject;
import TrimBot.model.PosObj;
import TrimBot.model.WPosObject;

public class CurrentPosition implements Runnable {

	protected BlockingQueue<String> serialOutQueue = null;
	protected BlockingQueue<PosObj> posObjQueue = null;
	protected SerialPort port = null;

	public CurrentPosition(BlockingQueue<String> serialOutQueue, BlockingQueue<PosObj> posObjQueue, SerialPort port) {
		this.serialOutQueue = serialOutQueue;
		this.port = port;
		this.posObjQueue = posObjQueue;
	}

	@Override
	public void run() {
		
		Scanner dataFromPort = new Scanner(port.getInputStream());
		MPosObject mPos = new MPosObject();
		WPosObject wPos = new WPosObject();
		while (port.isOpen()) {
			try {

				if (0 == port.bytesAvailable()) {
					serialOutQueue.put("?");
				}

				Thread.sleep(100);
				String positionStr = null;
				while (!(0 == port.bytesAvailable())) {
					positionStr = dataFromPort.nextLine();
					// System.out.println(positionStr); // TODO do something with "ok" message
				}
				if (positionStr != null && positionStr.length() > 40) {
					positionStr = positionStr.substring(1, (positionStr.length() - 1));

					String posStrArr[] = positionStr.split(",");
					mPos.setCurrentState(posStrArr[0].trim());
					mPos.setX(getXorY(posStrArr[1]));
					mPos.setY(posStrArr[2]);
					mPos.setZ(posStrArr[3]);
					wPos.setX(getXorY(posStrArr[4]));
					wPos.setY(posStrArr[5]);
					wPos.setZ(posStrArr[6]);
					if (0 == posObjQueue.remainingCapacity()) {
						posObjQueue.clear();
					}
					// System.out.println("remaining Q capacity= "+posObjQueue.remainingCapacity());
					posObjQueue.put(mPos);
					posObjQueue.put(wPos);
					
				}

			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
		dataFromPort.close();

	}

	private String getXorY(String string) {
		String[] str = string.split(":");
		return str[1].trim();
	}

}
