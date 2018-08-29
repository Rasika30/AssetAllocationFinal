package com.citi.main;

import java.util.List;

import com.citi.dao.AssetDAO;
import com.citi.dao.ClientDAO;
import com.citi.dao.ClientGoalDAO;
import com.citi.dao.RecordRiskRewardDAO;
import com.citi.dao.UserMappingDAO;
import com.citi.operations.Operations;
import com.citi.pojo.Asset;
import com.citi.pojo.ClientResponse;
import com.citi.pojo.Tuple;

public class MainClass {
	
	public static void DetectTriggerResponse(Long clientId){
		UserMappingDAO userMappingDao=new UserMappingDAO();
//		long clientId=userMappingDao.getClientId(long1);
		AssetDAO assetDAO=new AssetDAO();
		List<Asset> assets=assetDAO.retrieveAssetDetails();
		ClientDAO clientDAO=new ClientDAO();
		ClientResponse clientResponse=clientDAO.retrieveClientResponsesAndGoals(clientId);
		double risk=Operations.calculateRisk(assets, clientResponse);
		double reward=Operations.calculateReward(assets, risk, 2);
		RecordRiskRewardDAO recordRiskRewardDAO=new RecordRiskRewardDAO();
		int result=recordRiskRewardDAO.storeRiskReward(clientId, risk, reward);
		double PresentValue=Operations.calculateA(clientResponse, reward);
		//List<Tuple<Long, Boolean>> ans=Operations.goalsMet(PresentValue, reward, clientResponse.getGoals());
		//Tuple<Long, List<Tuple<Long, Boolean>>> lists=new Tuple<Long, List<Tuple<Long,Boolean>>>(clientResponse.getClientId(),ans );
		//ClientGoalDAO clientGoalDAO=new ClientGoalDAO();
		//clientGoalDAO.updateGoalsMet(lists);
		
	}

}
