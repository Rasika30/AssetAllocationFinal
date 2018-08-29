package com.citi.pojo;

public abstract class Asset {
protected int assetId;
protected String assetName;
protected double risk;
protected double reward;
public Asset(int assetId, String assetName, double risk, double reward) {
	super();
	this.assetId = assetId;
	this.assetName=assetName;
	this.risk = risk;
	this.reward = reward;
}
public Asset() {
	super();
	this.assetId = -1;
	this.assetName=" ";
	this.risk = -1;
	this.reward = -1;
}
public int getAssetId() {
	return assetId;
}
public void setAssetId(int assetId) {
	this.assetId = assetId;
}
public double getRisk() {
	return risk;
}
public void setRisk(double risk) {
	this.risk = risk;
}
public double getReward() {
	return reward;
}
public String getAssetName() {
	return assetName;
}
public void setAssetName(String assetName) {
	this.assetName = assetName;
}
public void setReward(double reward) {
	this.reward = reward;
}

}
