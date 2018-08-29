package com.citi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.citi.connections.MyConnection;
import com.citi.pojo.ClientGoal;
import com.citi.pojo.Tuple;

public class ClientGoalDAO {
	public List<ClientGoal> fetchGoals(long clientId) {
		Connection conn=MyConnection.getMyConnection();
		String FIND_CLIENT_GOALS="SELECT GoalId,GoalAmount,CurrentYear,GoalYear,GoalsMet FROM FinancialGoals WHERE ClientId=?";
		PreparedStatement preparedStatement;
		List<ClientGoal> goals = new ArrayList<>();
		try {
			preparedStatement = conn.prepareStatement(FIND_CLIENT_GOALS);
			preparedStatement.setLong(1,clientId);
			ResultSet rs=preparedStatement.executeQuery();
			while(rs.next()) { 
				int goalId,goalYear,currentYear; 
				long goalAmount;
				boolean goalsMet;
				goalId=rs.getInt("GoalId");
				goalAmount=rs.getLong("GoalAmount");
				currentYear=rs.getInt("CurrentYear");
				goalYear=rs.getInt("GoalYear");
				goalsMet=rs.getBoolean("GoalsMet");	
				ClientGoal goal=new ClientGoal(goalId,goalAmount,currentYear,goalYear,goalsMet);
				goals.add(goal);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return goals;
	}
	
	public int updateGoalsMet (Tuple<Long, List<Tuple<Long, Boolean>>> clientGoalsList) {
		Connection conn=MyConnection.getMyConnection();
		String UPDATE_GOAL_STATUS="UPDATE FinancialGoals SET GoalsMet=? WHERE ClientId=? AND GoalId=?";
		PreparedStatement preparedStatement;
		int rowsAffected=0;
		try {
			preparedStatement = conn.prepareStatement(UPDATE_GOAL_STATUS);
			long clientId=clientGoalsList.getX();
			preparedStatement.setLong(2,clientId);
			List<Tuple<Long, Boolean>> goalsList=clientGoalsList.getY();
			for(Tuple<Long,Boolean> goal:goalsList) {
				preparedStatement.setBoolean(1, goal.getY());
				preparedStatement.setLong(3, goal.getX());	
				rowsAffected+=preparedStatement.executeUpdate();
			}
			if(rowsAffected==0) {
				System.out.println("Error in updation. Cannot commit.");
				return -1;//check this condition for not commiting
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowsAffected;	
	}
	
}
	