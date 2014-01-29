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

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.megam.api.APIClient;
import org.megam.api.exception.APIContentException;
import org.megam.api.exception.APIInvokeException;

import java.security.NoSuchAlgorithmException;
import java.net.MalformedURLException;
import java.security.InvalidKeyException;
/**
 * @author rajthilak
 * 
 */
public class APIClientTest {

	@Test
	public void test() throws APIInvokeException, APIContentException {
		APIClient build = new APIClient("sandy@megamsandbox.com", "IamAtlas{74}NobodyCanSeeME#07");
	    //build.execute(build.builder("nodes/sandy@megamsandbox.com", "{\"email\":\"sandy@megamsandbox.com\", \"api_key\":\"IamAtlas{74}NobodyCanSeeME#075488\", \"authority\":\"user\" }"));
		//build.execute(build.builder("nodes/appsample2.megam.co", ""));
	}
}