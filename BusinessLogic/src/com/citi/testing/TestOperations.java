package com.citi.testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.citi.operations.Operations;
import com.citi.pojo.Asset;
import com.citi.pojo.ClientGoal;
import com.citi.pojo.ClientResponse;
import com.citi.pojo.Commodity;
import com.citi.pojo.Equity;
import com.citi.pojo.FixedIncome;
import com.citi.pojo.Question;
import com.citi.pojo.Tuple;

public class TestOperations {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCalculateRatio() {
		fail("Not yet implemented");
	}

	@Test
	public void testCalculateRatioPositive() {
		Equity equity = new Equity(1.0,4.0);
		FixedIncome fixedIncome = new FixedIncome(2.0,5.0);
		Commodity commodity = new Commodity(3.0, 7.0);
		List<Asset> assets = new ArrayList<Asset>();
		Collections.addAll(assets, equity,fixedIncome,commodity);
		double calculatedRisk = 2.1;
		double calculatedReward = 5.4;
		List<Tuple<Integer,Double>> ans = new ArrayList<Tuple<Integer,Double>>();
		ans.add(new Tuple<Integer,Double>(1,0.2));
		ans.add(new Tuple<Integer,Double>(2,0.5));
		ans.add(new Tuple<Integer,Double>(3,0.3));
		List<Tuple<Integer,Double>> myAns = Operations.calculateRatio(assets, calculatedRisk, calculatedReward);
		int z = ans.size();
		Tuple<Integer,Double> curr;
		for(Tuple<Integer,Double> t:myAns){
			System.out.println(t.getX()+":"+ t.getY());
		}
		double  actual,expected, delta;
		delta = 0.05;
		Map<Integer,Double> myMap = myAns.stream().collect(Collectors.toMap(tuple -> tuple.getX(),tuple -> tuple.getY()));
		for(int i = 0; i < z ; i++){
			curr = ans.get(i);
			expected =curr.getY();
			actual = myMap.get(curr.getX());
			assertEquals(expected, actual, delta);
		}
	}
	@Test
	public void testCalculateRatioNegative(){
		Equity equity = new Equity(1.0,4.0);
		FixedIncome fixedIncome = new FixedIncome(2.0,5.0);
		Commodity commodity = new Commodity(3.0, 7.0);
		List<Asset> assets = new ArrayList<Asset>();
		Collections.addAll(assets, equity,fixedIncome,commodity);
		double calculatedRisk = -2.1;
		double calculatedReward = -5.4;
		List<Tuple<Integer,Double>> ans = new ArrayList<Tuple<Integer,Double>>();
		List<Tuple<Integer,Double>> myAns = Operations.calculateRatio(assets, calculatedRisk, calculatedReward);
		ans.add(new Tuple<Integer,Double>(1,0.0));
		ans.add(new Tuple<Integer,Double>(2,0.0));
		ans.add(new Tuple<Integer,Double>(3,0.0));
		int z = ans.size();
		Tuple<Integer,Double> curr;
//		for(Tuple<Integer,Double> t:myAns){
//			System.out.println(t.getX()+":"+ t.getY());
//		}
		double  actual,expected, delta;
		delta = 0.05;
		Map<Integer,Double> myMap = myAns.stream().collect(Collectors.toMap(tuple -> tuple.getX(),tuple -> tuple.getY()));
		for(int i = 0; i < z ; i++){
			curr = ans.get(i);
			expected =curr.getY();
			actual = myMap.get(curr.getX());
			assertEquals(expected, actual, delta);
		}
	}
	@Test
	public void testCalculateRiskNegative() {
		List<Question> questions=new ArrayList<>();
		for(int i=0;i<5;i++){
	//		questions.add(new Question(i,"a", -1, 1));
		}
		List<Asset> assets=new ArrayList<>();
		Equity equity=new Equity(3, 3);
		boolean testBoolValue=false;
		FixedIncome fixedIncome=new FixedIncome(1, 1);
		Commodity commodity=new Commodity(2, 2);
		assets.add(fixedIncome);
		assets.add(equity);
		assets.add(commodity);
		int random = (int)(Math.random()*100);
		if(random%2==0) {
			testBoolValue=true;
		}
		else {
			testBoolValue=false;
		}
		List<ClientGoal> goals=new ArrayList<>();
		for(int i=0;i<5;i++){
			goals.add(new ClientGoal(i, 20000, 2018, 2020,testBoolValue));//change true to random bool
		}
		ClientResponse clientResponse=new ClientResponse(1, questions, goals);
		double ans=Operations.calculateRisk(assets, clientResponse);
		assertEquals(0,ans,0);
	}
	@Test
	public void testCalculateRiskPositive() {
		List<Question> questions=new ArrayList<>();
		for(int i=0;i<5;i++){
	//		questions.add(new Question(i,"b", 0.5, 1));
		}
		List<Asset> assets=new ArrayList<>();
		Equity equity=new Equity(3, 3);
		FixedIncome fixedIncome=new FixedIncome(1, 1);
		Commodity commodity=new Commodity(2, 2);
		boolean testBoolValue=false;
		int random = new Random().nextInt();
		if(random%2==0) {
			testBoolValue=true;
		}
		else {
			testBoolValue=false;
		}
		assets.add(fixedIncome);
		assets.add(equity);
		assets.add(commodity);
		List<ClientGoal> goals=new ArrayList<>();
		for(int i=0;i<5;i++){


			goals.add(new ClientGoal(i, 20000, 2018, 2020,testBoolValue));
		}
		ClientResponse clientResponse=new ClientResponse(1, questions, goals);
		double ans=Operations.calculateRisk(assets, clientResponse);
		assertEquals(2,ans,0);
	}
}
