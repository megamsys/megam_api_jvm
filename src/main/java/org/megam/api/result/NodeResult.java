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
package org.megam.api.result;

import java.io.*;
import java.lang.*;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import org.megam.api.exception.APIInvokeException;
import org.megam.api.info.JSONAble;
import org.megam.api.info.NodeInfo;
/**
 * @author ram
 * 
 */
public class NodeResult implements JSONAble {

	private String jsonString;
	private JSONObject json;
	
	public NodeResult(String jsonString) throws APIInvokeException{
		this.jsonString = jsonString;
		jsonParser();
	}
	
	public String json() {
		return jsonString;
	}
	
	public JSONObject getJson() {
		return json;
	}
	
	public void jsonParser() throws APIInvokeException{
		try {
		JSONParser parser=new JSONParser();
		Object object = parser.parse(jsonString);		
		JSONObject tot_json = (JSONObject) object;
		JSONArray res_json = (JSONArray) tot_json.get("results");
		json = (JSONObject)res_json.get(0);	    
		}
		catch (ParseException pe) {
			throw new APIInvokeException("", pe);
		}
	}

}
