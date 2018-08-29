package com.citi.testing;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.citi.dao.UserMappingDAO;

public class TestUserMappingDAO {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetClientIdPositive() {
		UserMappingDAO userMappingDAO=new UserMappingDAO();
		long val=userMappingDAO.getClientId("abc@gmail.com");
		assertTrue(val!=-1);
	}
	@Test
	public void testGetClientIdNegative() {
		UserMappingDAO userMappingDAO=new UserMappingDAO();
		long val=userMappingDAO.getClientId("abc");
		assertTrue(val==-1);
	}
	

}
