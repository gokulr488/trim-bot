package TrimBot.model;

import TrimBot.readers.ReadModeEnum;

public class WPosObject implements PosObj{
	private float x;
	private float y;
	private float z;
	
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
		
		return ReadModeEnum.W_POS;
	}
	
}
