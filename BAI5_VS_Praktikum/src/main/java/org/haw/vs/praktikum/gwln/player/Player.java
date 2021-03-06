package org.haw.vs.praktikum.gwln.player;

import com.google.gson.JsonObject;

public class Player {

	private String myUser;
	private String myId;
	private String myPawn;
	private String myAccount;
	private Boolean myReadiness;
	private String myReadinessService;
	private String baseUri;
	
	public Player(String user, String id, String pawn, String account, String baseUri, String readyService) {
		myUser = user;
		myId = id;
		myPawn = pawn;
		myAccount = account;
		myReadiness = false;
		if(readyService==null){
			myReadinessService = myId + "/ready";
		}else {
			myReadinessService = readyService;
		}
		this.baseUri = baseUri;
	}
	
	public String getUser() {
		return myUser;
	}
	public void setUser(String user) {
		myUser = user;
	}
	public String getId() {
		return myId;
	}
	public void setId(String id) {
		myId = id;
	}
	public String getPawn() {
		return myPawn;
	}
	public void setPawn(String pawn) {
		myPawn = pawn;
	}
	public String getAccount() {
		return myAccount;
	}
	public void setAccount(String account) {
		myAccount = account;
	}
	public Boolean isReady() {
		return myReadiness;
	}
	public void setReady(Boolean readiness) {
		myReadiness = readiness;
	}
	public String getReadinessService() {
		return myReadinessService;
	}
	public void setReadinessService(String readinessService) {
		myReadinessService = readinessService;
	}
	
	public String getBaseUri(){
		return baseUri;
	}

	public static String getJsonString(Player player) {
		JsonObject json = new JsonObject();
		json.addProperty("id", player.getId());
		json.addProperty("user", player.getUser());
		json.addProperty("pawn", player.getPawn());
		json.addProperty("account", player.getAccount());
		json.addProperty("ready", player.getReadinessService());
		
		return json.toString();
	}
}
