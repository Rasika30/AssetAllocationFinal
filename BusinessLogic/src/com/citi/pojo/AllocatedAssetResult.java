package com.citi.pojo;

import java.util.List;

public class AllocatedAssetResult {
private long clientId;
private int goalsMet;
private List<Tuple<Integer,Double>> allocatedAssetResult;
public AllocatedAssetResult() {
	//changed
	// TODO Auto-generated constructor stub
	this.clientId = -1;
	this.goalsMet = -2;
}

public AllocatedAssetResult(long clientId) {
	super();
	this.clientId = clientId;
}


public AllocatedAssetResult(long clientId, List<Tuple<Integer, Double>> allocatedAssetResult , int goalsMet) {
	super();
	this.clientId = clientId;
	this.goalsMet = goalsMet;
	this.allocatedAssetResult=allocatedAssetResult;

}

public long getClientId() {
	return clientId;
}

public void setClientId(long clientId) {
	this.clientId = clientId;
}


public List<Tuple<Integer, Double>> getAllocatedAssetResult() {
	return allocatedAssetResult;
}

public void setAllocatedAssetResult(List<Tuple<Integer, Double>> allocatedAssetResult) {
	this.allocatedAssetResult = allocatedAssetResult;
}

public int getGoalsMet() {
	return goalsMet;
}

public void setGoalsMet(int goalsMet) {
	this.goalsMet = goalsMet;
}

}
