package TrimBot.readers;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

import TrimBot.connection.PositionHandler;
import TrimBot.model.GCodeObject;
import TrimBot.model.InputFileLines;
import TrimBot.model.MCodeObject;

public class MachiningProcessHandler {

	private BlockingQueue<String> serialOutQueue;
	private PositionHandler posHandler;
	private Map<Integer, InputFileLines> inputFileMap;
	private int noOfLines;

	public MachiningProcessHandler(BlockingQueue<String> serialOutQueue, PositionHandler posHandler,
			Map<Integer, InputFileLines> inputFileMap, int noOfLines) {
		this.serialOutQueue = serialOutQueue;
		this.posHandler = posHandler;
		this.inputFileMap = inputFileMap;
		this.noOfLines = noOfLines;
	}

	public boolean startMachining() throws InterruptedException {

		int lineNumber = 1;
		// Scanner dataFromPort = new Scanner(posHandler.getPort().getInputStream());
		while (lineNumber <= noOfLines) {

			if ((inputFileMap.get(lineNumber)) != null) {
				switch (inputFileMap.get(lineNumber).getType()) {
				case G_CODE:
					GCodeObject gCode = (GCodeObject) inputFileMap.get(lineNumber);

					if (serialOutQueue.remainingCapacity() > 0) {
						System.out.println(gCode.getgCode());
						serialOutQueue.add(gCode.getgCode());
						lineNumber++;
					}

					break;
				case M_CODE:
					MCodeObject mCode = (MCodeObject) inputFileMap.get(lineNumber);

					if (serialOutQueue.remainingCapacity() > 0) {
						System.out.println(mCode.getmCode());
						serialOutQueue.add(mCode.getmCode());
						// System.out.println(dataFromPort.nextLine());
						lineNumber++;
					}
					break;

				default:
					lineNumber++;
					break;
				}
				Thread.sleep(250);
				while (isMachineRunning()) {

					Thread.sleep(1000);

				}

			} else {
				lineNumber++;
			}
		}

		return false;

	}

	private boolean isMachineRunning() throws InterruptedException {
		if (posHandler.getmPos() != null && posHandler.getmPos().getCurrentState() != null) {
			System.out.println("X =" + posHandler.getwPos().getX() + "  Y =" + posHandler.getwPos().getY() + "  Z ="
					+ posHandler.getwPos().getZ());
			if (posHandler.getmPos().getCurrentState().trim().equals("Run")) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
}
