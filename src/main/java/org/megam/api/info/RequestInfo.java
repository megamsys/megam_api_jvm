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
package org.megam.api.info;

import java.io.*;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import org.megam.api.exception.APIContentException;
import org.megam.api.exception.APIInvokeException;

/**
 * @author rajthilak
 * 
 */
public class RequestInfo {
	public static final String TIMETOKILL = "timetokill";
	public static final String METERED = "metered";
	public static final String LOGGING = "logging";
	public static final String RUNTIMEEXEC = "runtime_exec";
	public static final String REQTYPE = "req_type";
	public static final String NODENAME = "node_name";
	public static final String APPDEFNSID = "appdefns_id";
	public static final String LCAPPLY = "lc_apply";
	public static final String LCADDITIONAL = "lc_additional";
	public static final String LCWHEN = "lc_when";

	private String id;
	private String node_id;
	private String node_name;
	private Map<String, String> appdefns = new HashMap<String, String>();
	// private NodeAppDefns appdefns;
	private String created_at;

	// use gson to make it as JSON

	public String json() throws APIInvokeException {
		try {
		JSONObject obj = new JSONObject();
		obj.put(REQTYPE, "build");
		obj.put(NODENAME, getNodeName());
		obj.put(APPDEFNSID, getId());
		obj.put(LCAPPLY, getRunTimeExec());
		obj.put(LCADDITIONAL, "");
		obj.put(LCWHEN, "");
		StringWriter out = new StringWriter();
		obj.writeJSONString(out);
		String jsonText = out.toString();
		System.out.print(jsonText);
		return jsonText;
		} catch (IOException ioe) {
			throw new APIInvokeException("", ioe);
		}
	}

	public String getId() {
		return id;
	}

	public String getnodeId() {
		return node_id;
	}

	public String getNodeName() {
		return node_name;
	}

	public String getRunTimeExec() {
		return map().get(RUNTIMEEXEC);
	}

	public Map<String, String> map() {
		return appdefns;
	}

	public String getCreatedAt() {
		return created_at;
	}
	

}
