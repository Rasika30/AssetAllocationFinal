package com.citi.dao;
import java.util.List;
import com.citi.pojo.ClientGoal;
import com.citi.pojo.ClientResponse;
import com.citi.pojo.Question;
public class ClientDAO {
	public ClientResponse retrieveClientResponsesAndGoals(long clientId){
		ClientGoalDAO clientGoalDao=new ClientGoalDAO();
		ClientResponseDAO clientResponseDao=new ClientResponseDAO();
		ClientResponse clientResponse=new ClientResponse();
		List<Question> questions=clientResponseDao.fetchResponses(clientId);
		List<ClientGoal> goals=clientGoalDao.fetchGoals(clientId);
		clientResponse=new ClientResponse(clientId, questions,goals );
		return clientResponse;
		//clientResponse returned will be default constructor values if client
		//is not present. Check condition: clientId==-1 for client not present
	}
	
	
	
}
