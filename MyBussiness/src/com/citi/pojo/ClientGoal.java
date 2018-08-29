package com.citi.pojo;

public class ClientGoal {
private long goalId;
private long goalAmount;
private int currentYear;
private int targetYear;
private boolean goalMet;
public ClientGoal() {
	super();
	this.goalId = -1;
	this.goalAmount = -1;
	this.currentYear = -1;
	this.targetYear = -1;
	this.goalMet = false;
}
public ClientGoal(long goalId, long goalAmount, int currentYear, int targetYear,boolean goalMet) {
	super();
	this.goalId = goalId;
	this.goalAmount = goalAmount;
	this.currentYear = currentYear;
	this.targetYear = targetYear;
	this.goalMet = goalMet;
}


public boolean isGoalMet() {
	return goalMet;
}
public void setGoalMet(boolean goalMet) {
	this.goalMet = goalMet;
}
public long getGoalId() {
	return goalId;
}
public void setGoalId(long goalId) {
	this.goalId = goalId;
}
public long getGoalAmount() {
	return goalAmount;
}
public void setGoalAmount(long goalAmount) {
	this.goalAmount = goalAmount;
}
public int getCurrentYear() {
	return currentYear;
}
public void setCurrentYear(int currentYear) {
	this.currentYear = currentYear;
}
public int getTargetYear() {
	return targetYear;
}
public void setTargetYear(int targetYear) {
	this.targetYear = targetYear;
}

}
