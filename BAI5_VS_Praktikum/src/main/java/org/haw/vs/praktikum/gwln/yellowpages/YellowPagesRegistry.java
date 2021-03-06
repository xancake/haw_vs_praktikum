package org.haw.vs.praktikum.gwln.yellowpages;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.mashape.unirest.http.exceptions.UnirestException;

public class YellowPagesRegistry {
	private YellowPagesRestClient _yellow;
	
	private YellowPagesRegistry() {
		try {
			_yellow = new YellowPagesRestClient(new URL(YellowPagesRestClient.HAW_YELLOW_PAGES_INTERNAL));
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static YellowPagesRegistry getInstance() {
		return Holder.INSTANCE;
	}
	
	public String registerOrUpdateService(String name, String description, String type, String uri) throws YellowPagesNotAvailableException {
		try {
			Service overwriteService = findFirstDeadService(name, description, type, uri);
			if(overwriteService == null) {
				return _yellow.postService(new Service(name, type, uri, description));
			} else {
				_yellow.putService(overwriteService.getId(), new Service(name, type, uri, description));
				return overwriteService.getId();
			}
		} catch(UnirestException e) {
			throw new YellowPagesNotAvailableException(e);
		}
	}
	
	private Service findFirstDeadService(String name, String description, String type, String uri) throws UnirestException {
		// Alle Services sammeln, die zu unserem Namen und Typen passen
		List<Service> services = _yellow.getServicesOfName(name);
		services.retainAll(_yellow.getServicesOfType(type));
		
		// Einen "toten" Service zurückgeben, wenn es einen gibt
		for(Service service : services) {
			if("dead".equals(service.getStatus())) {
				return service;
			}
		}
		
		// Es gibt keinen Service, also null zurückgeben
		return null;
	}
	
	private static class Holder {
		private static final YellowPagesRegistry INSTANCE = new YellowPagesRegistry();
	}
}
