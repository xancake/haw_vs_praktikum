package org.haw.vs.praktikum.gwln.praktikum2.b.client.restclient.user;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.haw.vs.praktikum.gwln.rest.client.AbstractRestClient;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class UserRestClient extends AbstractRestClient {
	public UserRestClient(String url) {
		super(url, "/users");
	}
	
	public String registerUser(String username) throws UnirestException{
		
		try {
			JSONObject json = new JSONObject();
			json.put("id", "/users/" + username.toLowerCase());
			json.put("name", username);
			
			String url = "http://" + InetAddress.getLocalHost().getHostAddress() + ":4567";
			
			json.put("uri", url);
			
			HttpResponse<String> response = Unirest.post(getUrl())
					.header("Content-Type", "application/json")
					.body(json)
					.asString();
			
			return response.getHeaders().getFirst("Location");
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
	}
}
