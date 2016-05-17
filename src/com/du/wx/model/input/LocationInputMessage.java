package com.du.wx.model.input;

import com.du.wx.model.common.BaseInputMessage;

public class LocationInputMessage extends BaseInputMessage{

	//位置消息
    private String LocationX ;
    private String LocationY ;
    private String Scale ;
    private String Label ;
	public String getLocationX() {
		return LocationX;
	}
	public void setLocationX(String locationX) {
		LocationX = locationX;
	}
	public String getLocationY() {
		return LocationY;
	}
	public void setLocationY(String locationY) {
		LocationY = locationY;
	}
	public String getScale() {
		return Scale;
	}
	public void setScale(String scale) {
		Scale = scale;
	}
	public String getLabel() {
		return Label;
	}
	public void setLabel(String label) {
		Label = label;
	}
    
    
}
