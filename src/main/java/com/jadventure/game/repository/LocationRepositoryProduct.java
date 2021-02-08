package com.jadventure.game.repository;


import com.jadventure.game.GameBeans;
import com.jadventure.game.navigation.ILocation;
import com.google.gson.JsonObject;
import com.jadventure.game.navigation.Coordinate;
import com.jadventure.game.navigation.LocationType;
import com.jadventure.game.navigation.Location;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LocationRepositoryProduct {
	private ItemRepository itemRepo = GameBeans.getItemRepository();
	private NpcRepository npcRepo = GameBeans.getNpcRepository();

	public ILocation loadLocation(JsonObject json) {
		Coordinate coordinate = new Coordinate(json.get("coordinate").getAsString());
		String title = json.get("title").getAsString();
		String description = json.get("description").getAsString();
		LocationType locationType = LocationType.valueOf(json.get("locationType").getAsString());
		ILocation location = new Location(coordinate, title, description, locationType);
		location.setDangerRating(json.get("danger").getAsInt());
		if (json.has("items")) {
			List<String> items = new Gson().fromJson(json.get("items"), new TypeToken<List<String>>() {
			}.getType());
			for (String id : items) {
				location.addItem(itemRepo.getItem(id));
			}
		}
		if (json.has("npcs")) {
			List<String> npcs = new Gson().fromJson(json.get("npcs"), new TypeToken<List<String>>() {
			}.getType());
			for (String npc : npcs) {
				location.addNpc(npcRepo.getNpc(npc));
			}
		}
		return location;
	}
}