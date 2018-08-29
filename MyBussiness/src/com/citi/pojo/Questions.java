package com.citi.pojo;

public class Questions {
private long questionId;
private double responseValue;
private double questionWeightage;

public Questions() {
	this.questionId = -1;
	this.responseValue = -1;
	this.questionWeightage = -1;
}

public Questions(long questionId, double responseValue, double questionWeightage) {
	super();
	this.questionId = questionId;
	this.responseValue = responseValue;
	this.questionWeightage = questionWeightage;
}

public long getQuestionId() {
	return questionId;
}

public void setQuestionId(long questionId) {
	this.questionId = questionId;
}

public double getResponseValue() {
	return responseValue;
}

public void setResponseValue(double responseValue) {
	this.responseValue = responseValue;
}

public double getQuestionWeightage() {
	return questionWeightage;
}

public void setQuestionWeightage(double questionWeightage) {
	this.questionWeightage = questionWeightage;
}

}
