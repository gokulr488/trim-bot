package TrimBot.model;

import TrimBot.readers.ReadModeEnum;

public class GCodeObject implements InputFileLines{
	private String gCode;
	private float x;
	private float y;
	private float z;
	private float feed;
	private int speed;
	private float I;
	private float J;
	
	public String getgCode() {
		return gCode;
	}
	public void setgCode(String gCode) {
		this.gCode = gCode;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getZ() {
		return z;
	}
	public void setZ(float z) {
		this.z = z;
	}
	public float getFeed() {
		return feed;
	}
	public void setFeed(float feed) {
		this.feed = feed;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public float getI() {
		return I;
	}
	public void setI(float i) {
		I = i;
	}
	public float getJ() {
		return J;
	}
	public void setJ(float j) {
		J = j;
	}
	@Override
	public ReadModeEnum getType() {
		
		return ReadModeEnum.G_CODE;
	}
	
	
	
	
	
}
