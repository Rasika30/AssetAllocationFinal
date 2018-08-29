package com.citi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.citi.connections.MyConnection;
import com.citi.pojo.Question;

public class ClientResponseDAO {
	public List<Question> fetchResponses(long clientId) {
		Connection conn=MyConnection.getMyConnection();
		String FIND_CLIENT_RESPONSES="SELECT ClientResponse.ResponseID,ClientResponse.ResponseOption,ResponseValueToWeightsAllocated.WeightsAllocated FROM ClientResponse INNER JOIN ResponseValueToWeightsAllocated ON ClientResponse.ResponseID = ResponseValueToWeightsAllocated.ResponseID WHERE ClientResponse.ClientID = ?";
		PreparedStatement preparedStatement;
		List<Question> questions = new ArrayList<>();
		try {
			preparedStatement = conn.prepareStatement(FIND_CLIENT_RESPONSES);
			preparedStatement.setLong(1,clientId);
			ResultSet rs=preparedStatement.executeQuery();
			while(rs.next()) { 
				int responseId; 
				String response;
				double responseValue,weightAllocated;
				responseId=rs.getInt(1);
				response=rs.getString(2);
				weightAllocated=rs.getDouble(3);
				responseValue=retrieveResponseValue(conn,responseId,response);
				Question question=new Question(responseId,response,responseValue,weightAllocated);
				questions.add(question);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return questions;
	}
	public double retrieveResponseValue(Connection conn,int responseId,String response) {
		String FIND_RESPONSE_VALUE="SELECT ResponseValue FROM ResponseToValue WHERE ResponseId=? AND ResponseOption=?";
		PreparedStatement preparedStatement;
		double responseValue=-1;
		try {
			preparedStatement=conn.prepareStatement(FIND_RESPONSE_VALUE);
			preparedStatement.setInt(1, responseId);
			preparedStatement.setString(2, response);
			ResultSet rs=preparedStatement.executeQuery();
			if(rs.next()) {
			responseValue=rs.getDouble("ResponseValue");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseValue;
	}
}
