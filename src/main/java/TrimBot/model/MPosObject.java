package TrimBot.model;

import TrimBot.readers.ReadModeEnum;

public class MPosObject implements PosObj{
	private String currentState;
	private float x;
	private float y;
	private float z;
	
	
	public String getCurrentState() {
		return currentState;
	}
	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}
	public float getX() {
		return x;
	}
	public void setX(String x) {
		this.x = Float.parseFloat(x);
	}
	public float getY() {
		return y;
	}
	public void setY(String y) {
		this.y = Float.parseFloat(y);
	}
	public float getZ() {
		return z;
	}
	public void setZ(String z) {
		this.z = Float.parseFloat(z);
	}
	@Override
	public ReadModeEnum getType() {
		
		return ReadModeEnum.M_POS;
	}
	
	
}
