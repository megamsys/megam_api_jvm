/* 
 ** Copyright [2012-2013] [Megam Systems]
 **
 ** Licensed under the Apache License, Version 2.0 (the "License");
 ** you may not use this file except in compliance with the License.
 ** You may obtain a copy of the License at
 **
 ** http://www.apache.org/licenses/LICENSE-2.0
 **
 ** Unless required by applicable law or agreed to in writing, software
 ** distributed under the License is distributed on an "AS IS" BASIS,
 ** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ** See the License for the specific language governing permissions and
 ** limitations under the License.
 */
package org.megam.api;

import java.io.*;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.net.MalformedURLException;
import org.apache.http.entity.ContentType;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.entity.ContentType;
import org.megam.api.exception.APIContentException;

import java.security.NoSuchAlgorithmException;
import java.net.MalformedURLException;
import java.security.InvalidKeyException;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author rajthilak
 * 
 */
public class APIContentBuilder {

	public static final String DATE = "X-Megam-DATE";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String EMAIL = "X-Megam-EMAIL";
	public static final String APIKEY = "X-Megam-APIKEY";
	public static final String ACCEPT = "Accept";
	public static final String HMAC = "X-Megam-HMAC";
	public static final String VND_MEGAM_JSON = "application/vnd.megam+json";
	private final static String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	private final static String API_GATEWAY = "https://api.megam.co";
	private final static String API_GATEWAY_VERSION = "/v1";
	final protected static char[] hexArray = "0123456789abcdef".toCharArray();
	private String email;
	private String api_key;
    private String contentString;
	private Map<String, String> headersAndBody = new HashMap<String, String>();

	private static String urlSuffix = "";

	public APIContentBuilder(String email, String api_key) {
		this.email = email;
		this.api_key = api_key;
	}

	public void buildHeadersAndBody(String jsonBody, String tmpurlSuffix)
			throws APIContentException {
		urlSuffix = tmpurlSuffix;
		try {
			String DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss z";
			String currentDate = new SimpleDateFormat(DATE_FORMAT)
					.format(new Date());

			String timeStampedPath = currentDate + "\n" + API_GATEWAY_VERSION + urlSuffix;
			String signedWithHMAC = null;
			if (jsonBody != null) {
				String md5Body = calculateMD5(jsonBody);
				String toSign = timeStampedPath + "\n" + md5Body;				
				signedWithHMAC = calculateHMAC(api_key, toSign);	
				contentString = jsonBody;
			}
			if (signedWithHMAC != null) {
				stickHeaderMap(currentDate, getFullHMAC(signedWithHMAC));
			} else {
				throw new IllegalArgumentException(
						"No arguments found to sign.");
			}
		} catch (NoSuchAlgorithmException nsae) {
			throw new APIContentException(nsae);
		} catch (InvalidKeyException inke) {
			throw new APIContentException(inke);
		}
	}

	public String getPath() {
		return API_GATEWAY + API_GATEWAY_VERSION + urlSuffix;
	}

	private String getFullHMAC(String tmpSignedWithHMAC) {
		return email + ":" + tmpSignedWithHMAC;
	}

	private void stickHeaderMap(String date, String fullHMAC) {
		headersAndBody.put(DATE, date);
		headersAndBody.put(EMAIL, email);
		headersAndBody.put(APIKEY, api_key);
		headersAndBody.put(ACCEPT, VND_MEGAM_JSON);
		headersAndBody.put(HMAC, fullHMAC);
	}

	private String calculateHMAC(String secret, String data)
			throws NoSuchAlgorithmException, InvalidKeyException {
		SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), "RAW");		
		Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
		mac.init(signingKey);		
		byte[] rawHmac = mac.doFinal(data.getBytes());		
		//String result = new String(Base64.encodeBase64(rawHmac));
		String result = bytesToHex(rawHmac);	
		return result;
	}
	
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	private String calculateMD5(String contentToEncode)
			throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(contentToEncode.getBytes());
		String result = new String(Base64.encodeBase64(digest.digest()));		
		return result;
	}

	public Map<String, String> getSignedHeadersAndBody() {
		return headersAndBody;
	}

	public String getContent() {
		return contentString;
	}
	
	public String toString() {
		return null;
	}

}