package com.citi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.citi.connections.MyConnection;

public class UserMappingDAO {
	public long getClientId(String emailId) {
		Connection conn=MyConnection.getMyConnection();
		String GET_CLIENT_ID = "SELECT ClientID FROM ClientInfoPage WHERE ClientEmail = ?";
		PreparedStatement preparedStatement;
		long clientId=-1;
		try {
			preparedStatement=conn.prepareStatement(GET_CLIENT_ID);
			preparedStatement.setString(1, emailId);
			ResultSet rs=preparedStatement.executeQuery();
			if(rs.next()) {
				clientId=rs.getLong("ClientId");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clientId;
	}
}
