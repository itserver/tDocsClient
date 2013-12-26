package com.indizen.tdocs.client;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class MyAutenticator extends Authenticator {
	
	private String user;
	private String pass;
	
	public MyAutenticator(String user,String pass) {
		this.user = user;
		this.pass = pass;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(user, pass.toCharArray());
	}
}
