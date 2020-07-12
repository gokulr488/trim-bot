package TrimBot.model;

import TrimBot.readers.ReadModeEnum;

public class MCodeObject implements InputFileLines{
	private String mCode;
	private int speed;
	
	public String getmCode() {
		return mCode;
	}
	public void setmCode(String mCode) {
		this.mCode = mCode;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	@Override
	public ReadModeEnum getType() {
		
		return ReadModeEnum.M_CODE;
	}
	
}
