/* “Copyright 2012 Megam Systems”
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.megam.api.http;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.net.URL;

import org.apache.http.NameValuePair;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.megam.api.APIContentBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ContentType;

public class APIHttpRequest {
	private static String urlString = null;
	private static Map<String, String> headers = null;
	private static String contentString = null;	

	public APIHttpRequest(APIContentBuilder content) {
		this.urlString = content.getPath();
		this.headers = content.getHeaders();
		this.contentString = content.getBody();
	}

	public static APIHttpResponse post() throws IOException {		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(urlString);		
		if (headers != null) {
			for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
				System.out.println(headerEntry.getKey()+"---"+headerEntry.getValue());
				httppost.addHeader(headerEntry.getKey(), headerEntry.getValue());
			}
		}
		//httppost.addHeader("Content_Type", ContentType.APPLICATION_JSON);
		//List<NameValuePair> params = new ArrayList<NameValuePair>();
		//params.add(new BasicNameValuePair("mystring", "value_of_my_string"));
		//httppost.setEntity(new UrlEncodedFormEntity(params));		
		httppost.setEntity(new StringEntity(contentString, ContentType.APPLICATION_JSON));
		System.out.println("-------------------------------");
		for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
			//System.out.println(headerEntry.getKey()+"---"+headerEntry.getValue());
			System.out.println(httppost.getHeaders(headerEntry.getKey().toString()));
			//httppost.addHeader(headerEntry.getKey(), headerEntry.getValue());
		}		
		APIHttpResponse res = null;		
		try {
			HttpResponse httpResp = httpclient.execute(httppost);
			res = new APIHttpResponse(httpResp.getStatusLine(),
					httpResp.getEntity(), httpResp.getLocale());
		} finally {
			httppost.releaseConnection();
		}
		return res;
	}

}
