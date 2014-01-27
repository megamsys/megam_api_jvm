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
import java.lang.*;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.entity.ContentType;
import org.megam.api.exception.APIContentException;
import org.megam.api.http.APIHttpRequest;
import org.megam.api.http.APIHttpResponse;
import org.megam.api.http.TransportMachinery;
import org.megam.api.http.TransportTools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.NoSuchAlgorithmException;
import java.net.MalformedURLException;
import java.security.InvalidKeyException;

/**
 * @author rajthilak
 * 
 */
public class APIClient {

	private String email;
	private String api_key;
	private APIContentBuilder content;
	private String contentToEncode;
	private Map<String, String> headers = new HashMap<String, String>();

	public APIClient(String email, String api_key)
			throws NoSuchAlgorithmException, MalformedURLException,
			InvalidKeyException {
		this.email = email;
		this.api_key = api_key;
		create();
	}

	public void create() throws NoSuchAlgorithmException,
			MalformedURLException, InvalidKeyException {
		String urlSuffix = "nodes/content";
		content = new APIContentBuilder(email, api_key);

		/*
		 * contentToEncode sample request body create nodes request body json
		 */
		//contentToEncode = "{\"comment\" : {\"message\":\"blaat\" , \"from\":\"blaat\" , \"commentFor\":123}}";
		contentToEncode = "";
		content.setHeadersAndBody(contentToEncode, urlSuffix);
	}

	public void post() throws IOException {
		TransportTools tst = new TransportTools(
				"https://api.megam.co/v1/nodes/sandy@megamsandbox.com", null,
				content.getHeaders());
		// Gson obj = new GsonBuilder().setPrettyPrinting().create();
		//tst.setContentType(ContentType.APPLICATION_JSON, contentToEncode);
		String responseBody = TransportMachinery.post(tst).entityToString();
		System.out.println(responseBody);

		// APIHttpResponse res = new APIHttpRequest(content).post();
		// System.out.println(res.entityToString());
		// System.out.println(res.localeToString());
		// System.out.println(res.statusLineToString());

	}
}