package com.wang.wangblog.utils;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class ConnectionManager {
	public static CloseableHttpClient getHttpClient() {
		try {
			// SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
			// null, new TrustStrategy() {
			// // 信任所有
			// public boolean isTrusted(X509Certificate[] chain,
			// String authType) throws CertificateException {
			// return true;
			// }
			// }).build();
			// SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			return HttpClients.custom().build();
			// } catch (KeyManagementException e) {
			// e.printStackTrace();
			// } catch (NoSuchAlgorithmException e) {
			// e.printStackTrace();
			// } catch (KeyStoreException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}
}
