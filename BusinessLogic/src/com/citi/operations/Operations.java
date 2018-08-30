package com.citi.operations;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.apache.commons.math3.util.MathArrays.Function;

import com.citi.pojo.AllocatedAssetResult;
import com.citi.pojo.Asset;
import com.citi.pojo.ClientGoal;
import com.citi.pojo.ClientResponse;
import com.citi.pojo.Question;
import com.citi.pojo.Tuple;

import edu.rit.numeric.NonNegativeLeastSquares;

public class Operations {
		public   static PolynomialFunction fit_polynomial_curve(List<Asset> graphAssetPoints,int degree) {
			final WeightedObservedPoints obs = new WeightedObservedPoints();
			//x-axis is risk, y-axis is reward.
			//points in form of (x,y)
			//degree = 1 straight line etc...
			for(Asset asset:graphAssetPoints) {
				obs.add(asset.getRisk(),asset.getReward());
				System.out.println(asset.getRisk() +": "+asset.getReward());
			}
			final PolynomialCurveFitter fitter = PolynomialCurveFitter.create(degree);
			final double[] coeff = fitter.fit(obs.toList());
			for(double d: coeff) {
			
			System.out.println(d);
			}
			return new PolynomialFunction(coeff);
		}
		public static double calculateReward(List<Asset> assets,double risk,int degree) {
			//this is already scaled risk
			PolynomialFunction function=fit_polynomial_curve(assets, degree);
			return function.value(risk);
		}
		
		public static List<Tuple<Long,Boolean>> goalsMet(double presentValue,double reward,List<ClientGoal> clientGoals ){
			List<Tuple<Long, Boolean>> goalsMetList = new ArrayList<>() ;
			
			for(ClientGoal goal : clientGoals){
				if(goal.getGoalAmount()>calculateAmountValue(presentValue,reward,goal.getTargetYear())){
					Tuple<Long, Boolean> goalMet = new Tuple<Long, Boolean>(goal.getGoalId(), true);
					goalsMetList.add(goalMet);
					
				}
				else{
					Tuple<Long, Boolean> goalMet = new Tuple<Long, Boolean>(goal.getGoalId(), false);
					goalsMetList.add(goalMet);
				}
			}
			
			
			return goalsMetList;
			
		}
		public static long calculateAmountValue(double presentValue,double reward, int year){
			double den=Math.pow((1+reward),year);
			double amountVal = presentValue*den;
			return (long) amountVal;
		}
		//Ax = b
		// number of rows = equation, number of columns = variables
		private static double[][] createMatrixA(List<Asset> assets) {
			ArrayList<ArrayList<Double>> a = new ArrayList<ArrayList<Double>>();
			ArrayList<Double> risk= new ArrayList<Double>();
			ArrayList<Double> reward = new ArrayList<Double>();
			ArrayList<Integer> assetId = new ArrayList<Integer>();
			ArrayList<Double> ratioList = new ArrayList<Double>();
			assets.forEach(asset -> {risk.add(asset.getRisk()); reward.add(asset.getReward()); ratioList.add(1.0);});
			Collections.addAll(a,risk,reward,ratioList);
			List<double []> my =a.stream().map(u -> u.stream().mapToDouble(i -> i).toArray()).collect(Collectors.toList());
			double [][]b = new double[my.size()][];
			Iterator iter = my.iterator();
			int i = 0;
			while(iter.hasNext()) {
				b[i] = (double [])iter.next();
				i = i + 1;
			}
			return b;
		}
		private static double[] createMatrixB(double calculatedRisk,double calculatedReward,double ratio) {
			double[] b = new double[3];
			b[0] = calculatedRisk;
			b[1] = calculatedReward;
			b[2] =ratio;
			return b;
		}
		public static List<Tuple<Integer, Double>> calculateRatio(List<Asset> assets,double calculatedRisk,double calculatedReward) {
			int eqns = 3; 
			int variables = 3;
			double ratio =1.0;
			double [][]a = createMatrixA(assets);
			double [] b = createMatrixB(calculatedRisk, calculatedReward, ratio);
//			for(Asset asset:assets){
//				System.out.println(asset.getAssetId());
//			}
			NonNegativeLeastSquares leastSquares = solveEquation(eqns, variables, a, b);
			double[]x  = leastSquares.x;
			double mysum = 0;
			for(int i = 0; i < x.length - 1; i++) {
				DecimalFormat f = new DecimalFormat("##.00");
				x[i] = Double.valueOf(f.format(x[i]));
				mysum = mysum + x[i];
			}
			x[x.length - 1] =  1.0- mysum;
//			int [] index = leastSquares.index;
//			System.out.println(Arrays.toString(index) +"index");
			List<Tuple<Integer,Double>> allocation = new ArrayList<Tuple<Integer,Double>>();
			for(int i = 0; i < x.length; i++) {
				Tuple <Integer,Double> assetRatioTuple = new Tuple<Integer, Double>(assets.get(i).getAssetId(),x[i]);
				allocation.add(assetRatioTuple);
			}
			return allocation;
//			return null;
		}
		private static NonNegativeLeastSquares solveEquation(int eqns,int variables,double[][] a,double [] b) {
//			String pythonFile = new File("PythonResources/nnls.py").getAbsolutePath();
//			System.out.println(pythonFile);
//			System.out.println(new File("PythonResources/nnls.py").exists());
//			int z = new Random().nextInt();
//			File a_ =  new File(""+clientID+"a"+z+".txt");
//			File b_ = new File(""+clientID+"b"+z+".txt");
//			File x_ = new File(""+clientID+"x"+z+".txt");
//			System.out.println(a_.getAbsolutePath());
//			System.out.println(b_.getAbsolutePath());
//			double arr[] = null;
//			String aString ="";
//			String s;
//			for(double[] d:a){
//				s = Arrays.toString(d);
//				aString = aString + s.substring(1, s.length() - 1)+"\n";
//			}
//			s = Arrays.toString(b);
//			String bString = s.substring(1,s.length() - 1);
//			System.out.println(aString +"\n"+bString);
//			try {
//				a_.createNewFile();
//				b_.createNewFile();
//				x_.createNewFile();
//				System.out.println(a_.exists());
//				System.out.println(b_.exists());
//				FileWriter fa = new FileWriter(a_);
//				fa.write(aString);
//				fa.close();
//				FileWriter fb = new FileWriter(b_);
//				fb.write(bString);
//				fb.close();
//				Runtime rt = Runtime.getRuntime();
//				Process pr = rt.exec("python "+pythonFile +" "+ a_.getAbsolutePath() + " "+b_.getAbsolutePath()+" "+x_.getAbsolutePath());
//				List<String> lines = Files.readAllLines(Paths.get(x_.getAbsolutePath()));
//				x_.delete();
//				arr = lines.stream().map(u -> Double.valueOf(u)).mapToDouble(i -> i).toArray();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			//eqns = number of eqns
			//vars =number of variables
			NonNegativeLeastSquares leastSquares = new NonNegativeLeastSquares(eqns,variables);
			for(int eqn = 0; eqn < eqns; eqn++){ 
				leastSquares.a[eqn] = Arrays.copyOfRange(a[eqn], 0, variables);
				leastSquares.b[eqn] = b[eqn];
			}
			
			leastSquares.nsetp = variables;
			System.arraycopy(IntStream.range(0,variables).toArray(),0,leastSquares.index,0,variables);
//			for(double d:leastSquares.x){System.out.println(d);}
			leastSquares.solve();
//			for(double d:b){System.out.println(d);}
//			for(double d:leastSquares.x){System.out.println(d);}
//			System.out.println(leastSquares.x);
			return leastSquares;
//			return arr;
		}
		public static double calculateRisk(List<Asset> assets,ClientResponse clientResponse){
			double ans=0;
			List<Question> questions=clientResponse.getQuestionsResponses();
			System.out.println(questions.size());
			double sum=0;
			for(Question question:questions){
				if(question.getResponseValue()>0){
				sum=sum+(question.getResponseValue())*(question.getQuestionWeightage());
				}
			}
			
			//double avg=sum/questions.size();
			double avg=sum;
			if(avg==0)
				return 0;
			ans=riskScaler(avg,assets);
			return ans;
		}
		public static double riskScaler(double avg,List<Asset> asssets){
			double rMin=Double.MAX_VALUE,rMax=Double.MIN_VALUE;
		    for(Asset asset:asssets){
		    	rMin=Math.min(rMin, asset.getRisk());
		    	rMax=Math.max(rMax, asset.getRisk());
		    }
		    return rMin+(rMax-rMin)*avg;
		}
		public static double calculateA(ClientResponse clientResponse,double reward){
			List<ClientGoal> clientgoals=clientResponse.getGoals();
			double amt=0;
			for(ClientGoal clientGoal:clientgoals){
				double den=Math.pow((1+reward),(clientGoal.getTargetYear()-clientGoal.getCurrentYear()));
				amt=amt+(clientGoal.getGoalAmount()/den);
			}
			return amt;
		}
}
