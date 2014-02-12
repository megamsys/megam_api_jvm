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

import javax.inject.Inject;

import java.util.MissingResourceException;

import org.megam.api.result.AppDefnResult;
import org.megam.api.exception.APIContentException;
import org.megam.api.exception.APIInvokeException;
import org.megam.api.info.NodeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

/**
 * @author rajthilak
 * 
 */
public class AppDefns<A extends APIFascade> {

	private static final String GET = "/appdefns";
	private static final String POST = "/appdefns/content";
	private AppDefnResult res;

	@Inject
	private APIClient client;
	private Logger logger = LoggerFactory.getLogger(Nodes.class);
	public AppDefns(APIClient client) {
		this.client = client;
	}

	public <A> A list(String appdefns_id, String nodeName) throws APIInvokeException {
		try {
			logger.debug("AppDefns Entry <------->");
			if (client == null) {
				throw new MissingResourceException(
						"Make sure an APIClient is instantiated before you call Node.",
						"APIClient", "client");
			}
			String pass_parms_in_input_info = "";
			String jsonString = client.execute("GET", client.builder(GET + "/nodeName/" + appdefns_id, pass_parms_in_input_info));
			System.out.println(jsonString);
			return (A) new AppDefnResult(jsonString);
		} catch (APIContentException apce) {
			throw new APIInvokeException("", apce);
		} 
	}

	public <AppDefnResult> AppDefnResult post(NodeInfo ni)
			throws APIInvokeException {
           return null;
	}

}
