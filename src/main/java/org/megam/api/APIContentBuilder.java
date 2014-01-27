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

import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.megam.api.exception.APIContentException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author rajthilak
 * 
 */
public class APIContentBuilder {
	private String email;
	private String api_key;
	public static final String DATE = "X_Megam_DATE";
	public static final String CONTENT_TYPE = "Content_Type";
	public static final String EMAIL = "X_Megam_EMAIL";
	public static final String APIKEY = "X_Megam_APIKEY";
	public static final String ACCEPT = "Accept";
	public static final String HMAC = "X_Megam_HMAC";
	private final static String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	private Map<String, String> headers = new HashMap<String, String>();
	private final static String DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss z";
	private String signedHMAC;

	public APIContentBuilder(String email, String api_key) {
		this.email = email;
		this.api_key = api_key;
	}

	public void setBody(String jsonBody) {
		String hdr = header(urlSuffix);
		String hmac = null;
		if (jsonBody != null) {
			String bdy = hdr + "\n" + calculateMD5(jsonBody);
			 /*String signedWithHMAC =
		     calculateHMAC((headerMap.getOrElse(X_Megam_APIKEY, "blank_key")),
			 signWithHMAC)
			hmac = calculatedHMAC();*/
		} else {
			throw new APIContentException("Body is null");
		}
	}
	
	protected String signedHMAC() {
		return signedHMAC;
	}

	private String header(String urlSuffix) {
		URL path = new URL("https://api.megam.co/v1/" + urlSuffix);
		String currentDate = new SimpleDateFormat(DATE_FORMAT).format(new Date());
	    String signWithHMAC = currentDate + "\n" + path 
	}

	private String calculatedHMAC(String secret, String data)
			throws APIContentException {
		SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(),
				HMAC_SHA1_ALGORITHM);
		Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
		mac.init(signingKey);
		byte[] rawHmac = mac.doFinal(data.getBytes());
		String result = new String(Base64.encodeBase64(rawHmac));
		return result;
	}

	private String calculateMD5(String contentToEncode)
			throws APIContentException {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(contentToEncode.getBytes());
		String result = new String(Base64.encodeBase64(digest.digest()));
		return result;
	}

}