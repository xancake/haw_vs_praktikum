package org.haw.vs.praktikum.gwln.praktikum1.b;

import static spark.Spark.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import org.haw.vs.praktikum.gwln.yellowpages.YellowPagesNotAvailableException;
import org.haw.vs.praktikum.gwln.yellowpages.YellowPagesRegistry;
import org.json.JSONObject;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;

public class EventManagerWebService {
	private static final String NAME = "Event Manager Service 42_1337_69";
	private static final String DESCRIPTION = "Bester Event Manager 42_1337_69";
	private static final String SERVICE = "Event Manager Service";
//	private static final String URI = "http://abs969-events:4567/events";
	
	private static final EventManager MANAGER = new EventManager();
	
	private static String postEvent(Request request, Response response) {
		JSONObject bodyJson = new JSONObject(request.body());
		String game = bodyJson.getString("game");
		String type = bodyJson.getString("type");
		String name = bodyJson.getString("name");
		String reason  = bodyJson.getString("reason");
		String resource = bodyJson.optString("resource");
		String player = bodyJson.optString("player");
		String time = bodyJson.optString("time");
		
		Event event = new Event(game, type, name, reason, resource, player, time);
		MANAGER.addEvent(event);
		
		response.status(201);
		return "ok";
	}
	
	private static String getEvents(Request request, Response response) {
		String game = request.queryParams("game");
		String type = request.queryParams("type");
		String name = request.queryParams("name");
		String reason  = request.queryParams("reason");
		String resource = request.queryParams("resource");
		String player = request.queryParams("player");
		
		List<Event> filteredEvents = MANAGER.getMatchingEvents(game, type, name, reason, resource, player);
		
		return new Gson().toJson(filteredEvents);
	}
	
	private static String deleteEvent(Request request, Response response) {
		String game = request.queryParams("game");
		String type = request.queryParams("type");
		String name = request.queryParams("name");
		String reason  = request.queryParams("reason");
		String resource = request.queryParams("resource");
		String player = request.queryParams("player");
		
		MANAGER.deleteEvent(game, type, name, reason, resource, player);
		
		response.status(204);
		return "ok";
	}
	
	private static String getEvent(Request request, Response response) {
		String id = request.params(":eventid");
		
		Event event = MANAGER.getEvent(id);
		
		return new Gson().toJson(event);
	}
	
	public static void main(String[] args) throws UnknownHostException {
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			String uri = "http://"+ ip + ":4567/events";
			YellowPagesRegistry.registerOrUpdateService(NAME, DESCRIPTION, SERVICE, uri);
		} catch(YellowPagesNotAvailableException e) {
			e.printStackTrace();
		}
		
		post("/events", EventManagerWebService::postEvent);
		get("/events", EventManagerWebService::getEvents);
		delete("/events", EventManagerWebService::deleteEvent);
		get("/events/:eventid", EventManagerWebService::getEvent);
	}
}
