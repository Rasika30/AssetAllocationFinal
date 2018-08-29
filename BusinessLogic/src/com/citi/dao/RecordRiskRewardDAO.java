package com.citi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.citi.connections.MyConnection;

public class RecordRiskRewardDAO {
	public int storeRiskReward(long clientId,double risk,double reward) {
		int rowsAfffected=0;
		try {
			Connection conn=MyConnection.getMyConnection();
			String INSERT_RISK_REWARD="INSERT INTO RecommendedRiskReward values(?,?,?)";
			PreparedStatement preparedStatement=conn.prepareStatement(INSERT_RISK_REWARD);
			preparedStatement.setLong(1,clientId);
			preparedStatement.setDouble(2, risk);
			preparedStatement.setDouble(3, reward);
			rowsAfffected=preparedStatement.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return rowsAfffected;
	}
}
