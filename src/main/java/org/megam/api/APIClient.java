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
import org.megam.api.exception.*;

import org.megam.api.http.TransportMachinery;
import org.megam.api.http.TransportTools;



/**
 * @author rajthilak
 * 
 */
public class APIClient {

	private String email;
	private String api_key;

	public APIClient(String email, String api_key) {
		this.email = email;
		this.api_key = api_key;
	}

	protected APIContentBuilder builder(String urlSuffix, String contentToEncode)
			throws APIContentException {
		APIContentBuilder content = new APIContentBuilder(email, api_key);
		content.buildHeadersAndBody(contentToEncode, urlSuffix);
		return content;
	}

	public void execute(APIContentBuilder content) throws APIInvokeException {
		try {
			TransportTools tst = new TransportTools(content.getPath(), null,
					content.getSignedHeadersAndBody());
			// tst.setContentType(ContentType.APPLICATION_JSON,
			// contentToEncode);
			String responseBody = TransportMachinery.post(tst).entityToString();
			System.out.println(responseBody);
		} catch (IOException ioe) {
			throw new APIInvokeException("...",ioe);
		}
	}
}