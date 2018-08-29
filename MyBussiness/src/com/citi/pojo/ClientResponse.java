package com.citi.pojo;

import java.util.ArrayList;
import java.util.List;

public class ClientResponse {
private long clientId;
private List<Question> questionsResponses;
private List<ClientGoal> goals;
public ClientResponse() {

	this.clientId = -1;
	this.questionsResponses = new ArrayList<Question>();
	this.goals = new ArrayList<ClientGoal>();
}

public ClientResponse(long clientId, List<Question> questionsResponses, List<ClientGoal> goals) {
	super();
	this.clientId = clientId;
	this.questionsResponses = questionsResponses;
	this.goals = goals;
}

public List<ClientGoal> getGoals() {
	return goals;
}

public void setGoals(List<ClientGoal> goals) {
	this.goals = goals;
}

public long getClientId() {
	return clientId;
}
public void setClientId(long clientId) {
	this.clientId = clientId;
}
public List<Question> getQuestionsResponses() {
	return questionsResponses;
}
public void setQuestionsResponses(List<Question> questionsResponses) {
	this.questionsResponses = questionsResponses;
}


}
