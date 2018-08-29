package com.citi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.citi.pojo.Asset;
import com.citi.pojo.Commodity;
import com.citi.pojo.Equity;
import com.citi.pojo.FixedIncome;
import com.citi.connections.MyConnection;

public class AssetDAO {
	public ArrayList<Asset> retrieveAssetDetails(){
		List<Asset> assets = null;
		try {
		Connection conn=MyConnection.getMyConnection();
		assets=new ArrayList<>();
		String FIND_ALL_ASSETS="SELECT * FROM Asset;"; 
		Statement statement=conn.createStatement();
		ResultSet rs=statement.executeQuery(FIND_ALL_ASSETS);
		while(rs.next()) {
			switch(rs.getString("AssetName")) {
			case "Commodity":
				Commodity commodity=new Commodity(rs.getDouble("Risk"),rs.getDouble("Reward"));
				assets.add(commodity);
				break;
			case "Equity":
				Equity equity=new Equity(rs.getDouble("Risk"),rs.getDouble("Reward"));
				assets.add(equity);
				break;
			case "FixedIncome":
				FixedIncome fixedIncome=new FixedIncome(rs.getDouble("Risk"),rs.getDouble("Reward"));
				assets.add(fixedIncome);
				break;
			}
			
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (ArrayList<Asset>) assets;
	}
	
public Asset retrieveAssetDetails(String assetName) {
		
		Asset asset = null;
		try {
		Connection conn=MyConnection.getMyConnection();
		String FIND_ASSET="SELECT * FROM Asset where AssetName=?"; 
		PreparedStatement preparedStatement=conn.prepareStatement(FIND_ASSET);
		preparedStatement.setString(1, assetName);
		ResultSet rs=preparedStatement.executeQuery();
		while(rs.next()) {
			switch(assetName) {
			case "Commodity":
				asset=new Commodity(rs.getDouble("Risk"),rs.getDouble("Reward"));
				break;
			case "Equity":
				asset=new Equity(rs.getDouble("Risk"),rs.getDouble("Reward"));
				break;
			case "FixedIncome":
				asset=new FixedIncome(rs.getDouble("Risk"),rs.getDouble("Reward"));
				break;
			}
			
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return asset;
		
	}
	
}
