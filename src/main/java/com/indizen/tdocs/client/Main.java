package com.indizen.tdocs.client;

import java.net.Authenticator;

public class Main {

	public static void main(String[] argv) throws Exception {
		Authenticator.setDefault(new MyAutenticator("username","password"));
		//StringBuffer buffer = HTTP.recibir("http://www.itserver.es/ITServer/rest/cts2/map/13/version/1/entries");
		StringBuffer buffer = HTTP.recibir("http://www.itserver.es:8080/ITServer/rest/cts2/codesystems");
		if (buffer.toString().trim().length()>0){
			System.out.println(buffer.toString());
		}else{
			System.out.println("No se ha podido obtener el recurso");
		}
	}
}
