package com.indizen.tdocs.client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;

public final class ObtenerConexion {
	protected static Logger LOGGER = Logger.getLogger(ObtenerConexion.class);
	private static final String URLS = "https:";

	private ObtenerConexion(){};


	public static URLConnection getConnection(String url) throws IOException,NoSuchAlgorithmException,KeyManagementException {
		if(url.startsWith(URLS))
		{
			return getConnectionHttps(new URL(url));
		}
		else
		{
			return getConnectionHttp(new URL(url));
		}
	}

	private static URLConnection getConnectionHttp(URL url) throws IOException{
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	// Se establece un timeout de 15sg para realizar la conexion.
		conn.setConnectTimeout(Constants.TIME_OUT * Constants.MILI_SECONDS);
		return conn;
	}

	private static URLConnection getConnectionHttps(URL url) throws IOException,NoSuchAlgorithmException,KeyManagementException {
		SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
        SSLContext.setDefault(ctx);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
     // Se establece un timeout de 15sg para realizar la conexion.
        conn.setConnectTimeout(Constants.TIME_OUT * Constants.MILI_SECONDS);
        conn.setHostnameVerifier(new HostnameVerifier() {
         
        	public boolean verify(String hostname, SSLSession session) {
        		return true;
        	}
        });
        return conn;
	}

	private static class DefaultTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] arg0, String arg1)
        		throws CertificateException {
        	
        }
        
        public void checkServerTrusted(X509Certificate[] chain, String authType)
        		throws CertificateException {
        	
        }
        
        public X509Certificate[] getAcceptedIssuers() {
        	return null;
        }

    }

}
