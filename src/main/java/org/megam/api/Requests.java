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

import org.megam.api.exception.APIContentException;
import org.megam.api.exception.APIInvokeException;
import org.megam.api.info.RequestInfo;
import org.megam.api.result.AppDefnResult;
import org.megam.api.result.NodeResult;
import org.megam.api.result.RequestResult;

import com.google.gson.Gson;

import javax.inject.Inject;

import org.json.simple.parser.ParseException;

import java.util.MissingResourceException;
/**
 * @author rajthilak
 * 
 */
public class Requests<R extends APIFascade> {
	
	private NodeResult node;
	private static final String GET = "/appreqs";
	private static final String POST = "/appreqs/content";
	
	@Inject
	private APIClient client;
	
	public Requests(APIClient client) {
		this.client = client;
	}
	
	/* (non-Javadoc)
	 * @see org.megam.api.APIFascade#list()
	 */	
	public <RequestResult> RequestResult list(String s, String dummy) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.megam.api.APIFascade#post(java.lang.Object)
	 */	
	public <R> R post(NodeResult node) throws APIInvokeException {	
		if (client == null) {
			throw new MissingResourceException(
					"Make sure an APIClient is instantiated before you call Node.",
					"APIClient", "client");
		}
		try {
			AppDefnResult res = (AppDefnResult) new AppDefns(client).list((String)node.getJson().get("appdefnsid"), (String)node.getJson().get("node_name"));
			Gson gson = new Gson();		
			RequestInfo req_info = gson.fromJson(res.json(), RequestInfo.class);
			String pass_parms_in_input_info = req_info.json();			
			String jsonString = client.execute("POST", client.builder(POST, pass_parms_in_input_info));			
			return (R) new RequestResult(jsonString);			
		} catch (APIContentException apce) {
			throw new APIInvokeException("", apce);
		}			
	}	
	
}