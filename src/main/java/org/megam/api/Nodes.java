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
import org.megam.api.exception.APIContentException;
import org.megam.api.exception.APIInvokeException;

/**
 * @author rajthilak
 * 
 */
public class Nodes implements APIFascade {

	private static final String GET = "/nodes";
	private static final String POST = "/nodes/content";

	@Inject
	private APIClient client;

	public Nodes() {
	}

	@Override
	public <NodeResult> NodeResult list() throws APIInvokeException {
		if (client == null) {
			throw new MissingResourceException(
					"Make sure an APIClient is instantiated before you call Node.",
					"APIClient", "client");
		}
		try {
			String pass_parms_in_input_info = null;
			// converti ti to json
			// Gson obj = new GsonBuilder().setPrettyPrinting().create();

			client.execute(client.builder(GET, pass_parms_in_input_info));
			// covert the result back to NodeResult.
			return null;
		} catch (APIContentException apce) {
			throw new APIInvokeException("",apce);
		}
	}

	// public<NodeResult> NodeResult post<NodeInfo>(NodeInfo ni) throws
	// APIInvokeException {

	// }

}