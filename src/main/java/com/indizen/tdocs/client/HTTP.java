package com.indizen.tdocs.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;

public final class HTTP {	
	protected static Logger LOGGER = Logger.getLogger(HTTP.class);
	private static final String CHARSET = "UTF-8";
	
	
	private HTTP() {
	}	
	
	public static StringBuffer recibir(String urlConnect) throws ProtocolException{
	    BufferedReader in = null;
	    StringBuffer sb = new StringBuffer();
	    BufferedReader data = null;
	    PrintWriter writer = null;
		URLConnection connection = null;
		int code = 0;
	    try 
	    {
			LOGGER.info(urlConnect);
	        connection = ObtenerConexion.getConnection(urlConnect);
	        
	        connection.setRequestProperty("Accept-Charset", CHARSET);
	        connection.connect();
	        code = ((HttpURLConnection)connection).getResponseCode();
	        if(code == HttpURLConnection.HTTP_MOVED_TEMP)
	        {
	        	LOGGER.info("Recurso movido a :");
	        	return recibir(connection.getHeaderField("Location"));	        	
	        }
	        if(code < HttpURLConnection.HTTP_BAD_REQUEST)
	        {
	        	InputStream inStream = connection.getInputStream();
	        	Charset charset = Charset.forName("UTF-8");
	        	InputStreamReader inStreamReader = new InputStreamReader(inStream, charset);
	        	in = new BufferedReader(inStreamReader);
	        	String line;
	        	while ((line = in.readLine()) != null) {
	        		sb.append(line).append("\n");
	        	}
	        }
	 
	    } catch (Exception e) {
	    	LOGGER.error("Error al obtener recurso "+e.getMessage());
	    } finally {
	        try {
	            if (in != null) {
	                in.close();
	            }
	            if (data != null) {
	                data.close();
	            }
	            if (writer != null) {
	            	writer.close();
	            }
	        } catch (IOException e) {
	        	 LOGGER.error("Error al cerrar un recurso recibido por internet "+e.getMessage());	        	
	        }
	        if(connection != null)
			{
				((HttpURLConnection)connection).disconnect();
				try
				{
					if(code >= HttpURLConnection.HTTP_BAD_REQUEST)
					{
						if(code == HttpURLConnection.HTTP_UNAUTHORIZED)
						{
							throw new ProtocolException("Server returned HTTP response code: "+code);
						}					
					}
				}catch (IOException e) {
					
				}
			}
	    }
	    return sb;
	}
}
