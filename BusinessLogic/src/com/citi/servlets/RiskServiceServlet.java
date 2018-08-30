package com.citi.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.citi.main.MainClass;

/**
 * Servlet implementation class RiskServiceServlet
 */
@WebServlet("/riskService")
public class RiskServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public RiskServiceServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String clientID = request.getParameter("clientId");
		System.out.println("myclientid:"+clientID);
		MainClass.DetectTriggerResponse(Long.valueOf(clientID));
		response.setContentType("charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write("success");
	}
}
