package com.citi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

import com.citi.connections.MyConnection;
import com.citi.pojo.AllocatedAssetResult;
import com.citi.pojo.Tuple;

public class AllocatedAssetResultDAO {
	
	public String createSql(long clientId,int assetId, double assetRatio) {
		return  "INSERT INTO AllocatedAssetResult VALUES("+clientId+","+assetId+","+assetRatio+")"; 
	}
	
	
	public int storeAllocatedAssets(AllocatedAssetResult result) {
		int rowsAffected=0; 
		Connection conn = MyConnection.getMyConnection();

		try {
			int whileCount=0;
//			PreparedStatement preparedStatement=conn.prepareStatement(INSERT_ALLOCATED_ASSETS);		
//			preparedStatement.setLong(1,result.getClientId());
			List<Tuple<Integer,Double>> allocatedAssetResult=result.getAllocatedAssetResult();
			Iterator<Tuple<Integer,Double>> iterator=allocatedAssetResult.iterator();
			Statement statement = conn.createStatement();
			conn.setAutoCommit(false);
			while(iterator.hasNext()) {
				Tuple<Integer,Double> tuple=iterator.next();
				int assetId=tuple.getX();
				double allocatedRatio=tuple.getY();
				statement.addBatch(createSql(result.getClientId(), assetId, allocatedRatio));
//				preparedStatement.setInt(2,assetId);
//				preparedStatement.setDouble(3,allocatedRatio);
//				rowsAffected=rowsAffected+preparedStatement.executeUpdate();
				whileCount++;
			}
//			if(whileCount!=rowsAffected) {
//				System.out.println("Error in insertion of allocated results!");
//				return -1;
//				
//			}
			int updateCount = IntStream.of(statement.executeBatch()).sum();
			if(updateCount == whileCount) {
				conn.commit();
			}
			else {
				conn.rollback();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowsAffected;
	}
}
