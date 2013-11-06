package serverconnection;

import characters.Area;
import characters.Farm;
import characters.Farmer;
import characters.Sheep;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import gui.ProgramFrame;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;


/**
 * Class to handle JsonObjects
 * @author Håkon Ødegård Løvdal
 */
public class JsonHandler {

	/**
	 * Method to parse sheep-json and return a sheep object
	 * @param jsonObject Response containing a json
	 * @return A SheepObject
	 */
	public static Sheep parseJsonAndReturnSheep(Response jsonObject, Farmer farmer) {
		Map<String, JsonNode> sheepMap = new HashMap<String, JsonNode>();

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory factory = mapper.getJsonFactory();
			JsonParser parser = factory.createJsonParser(jsonObject.msg);
			JsonNode input = mapper.readTree(parser);

			JsonNode sheep = input.get("1");  // Get sheep from JsonObject
			Iterator<Entry<String, JsonNode>> nodeIterator = sheep.getFields();
			while (nodeIterator.hasNext()) {
				Entry<String, JsonNode> entry = nodeIterator.next();
				sheepMap.put(entry.getKey(), entry.getValue());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Sheep(sheepMap.get("id").asInt(), sheepMap.get("nickname").asText(), sheepMap.get("birthdate").asInt(), sheepMap.get("gender").asText(), sheepMap.get("weight_grams").asInt(),
				farmer, sheepMap.get("current_pulse").asInt(), sheepMap.get("latitude").asDouble(), sheepMap.get("longitude").asDouble(), sheepMap.get("is_infected").asInt());
	}

	/**
	 * Method to parse sheep-list and return sheepObjects
	 *
	 * @param jsonObject Response containing a json
	 * @param farmer The farmer-object currently logged in
	 * @return ArrayList of sheeps
	 */
	public static ArrayList<Sheep> parseJsonAndReturnSheepList(Response jsonObject, Farmer farmer) {
		ArrayList<Sheep> listOfSheeps = new ArrayList<Sheep>();
		if (jsonObject.msg.equals("Sau ikke funnet: ")) {
				return new ArrayList<Sheep>();
		}

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory factory = mapper.getJsonFactory();
			JsonParser parser = factory.createJsonParser(jsonObject.msg);
			JsonNode input = mapper.readTree(parser);

			int count = input.get("count").asInt();
			for (int i = 1; i <= count; i++) {
				Map<String, JsonNode> sheepMap = new HashMap<String, JsonNode>();
				JsonNode sheep = input.get(Integer.toString(i));
				Iterator<Entry<String, JsonNode>> nodeIterator = sheep.getFields();
				while (nodeIterator.hasNext()) {
					Entry<String, JsonNode> entry = nodeIterator.next();
					sheepMap.put(entry.getKey(), entry.getValue());
				}
				Sheep s = new Sheep(sheepMap.get("id").asInt(), sheepMap.get("nickname").asText(), sheepMap.get("birthdate").asInt(), sheepMap.get("gender").asText(), sheepMap.get("weight_grams").asInt(),
						farmer, sheepMap.get("current_pulse").asInt(), sheepMap.get("latitude").asDouble(), sheepMap.get("longitude").asDouble(), sheepMap.get("is_infected").asInt());
				listOfSheeps.add(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfSheeps;
	}

	/**
	 * Method returning a ArrayList of sheep-objects, used only by the simulation class
	 * @param jsonObject Response containing a json
	 * @return A ArrayList<Sheep> containing all the sheep
	 */
	public static ArrayList<Sheep> parseJsonAndReturnSheepList(Response jsonObject) {
		ArrayList<Sheep> listOfSheeps = new ArrayList<Sheep>();

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory factory = mapper.getJsonFactory();
			JsonParser parser = factory.createJsonParser(jsonObject.msg);
			JsonNode input = mapper.readTree(parser);

			int count = input.get("count").asInt();
			for (int i = 1; i <= count; i++) {
				Map<String, JsonNode> sheepMap = new HashMap<String, JsonNode>();
				JsonNode sheep = input.get(Integer.toString(i));
				Iterator<Entry<String, JsonNode>> nodeIterator = sheep.getFields();
				while (nodeIterator.hasNext()) {
					Entry<String, JsonNode> entry = nodeIterator.next();
					sheepMap.put(entry.getKey(), entry.getValue());
				}
				Sheep s = new Sheep(sheepMap.get("id").asInt(), sheepMap.get("nickname").asText(), sheepMap.get("birthdate").asInt(), sheepMap.get("gender").asText(), sheepMap.get("weight_grams").asInt(),
						new Farmer(1, "besthashever", "bestfarmerever", "bestemailever@email.com"), sheepMap.get("current_pulse").asInt(), sheepMap.get("latitude").asDouble(), sheepMap.get("longitude").asDouble()
						, sheepMap.get("is_infected").asInt(), sheepMap.get("farm_id").asInt());
				listOfSheeps.add(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfSheeps;
	}

	/**
	 * Method to parse login-json and return a farmer object
	 * @param jsonObject Response containing a json
	 * @return A Farmer-object
	 */
	public static Farmer parseJsonAndReturnUser(Response jsonObject) {
		Map<String, JsonNode> farmerMap = new HashMap<String, JsonNode>();

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory factory = mapper.getJsonFactory();
			JsonParser parser = factory.createJsonParser(jsonObject.msg);
			JsonNode input = mapper.readTree(parser);
			
			Iterator<Entry<String, JsonNode>> nodeIterator = input.getFields();
			while (nodeIterator.hasNext()) {
				Entry<String, JsonNode> entry = nodeIterator.next();
				farmerMap.put(entry.getKey(), entry.getValue());
			}			
		} catch (IOException e) {
			e.printStackTrace();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Farmer(farmerMap.get("id").asInt(), farmerMap.get("usercode").getTextValue(),
				farmerMap.get("username").getTextValue(), farmerMap.get("email").getTextValue());
	}
	
	public static Farm parseJsonAndReturnFarm(Response jsonObject) {
		Map<String, JsonNode> farmerMap = new HashMap<String, JsonNode>();

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory factory = mapper.getJsonFactory();
			JsonParser parser = factory.createJsonParser(jsonObject.msg);
			JsonNode input = mapper.readTree(parser);
			
			Iterator<Entry<String, JsonNode>> nodeIterator = input.getFields();
			while (nodeIterator.hasNext()) {
				Entry<String, JsonNode> entry = nodeIterator.next();
				farmerMap.put(entry.getKey(), entry.getValue());
			}			
		} catch (IOException e) {
			e.printStackTrace();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Farm(new ArrayList<Area>(), farmerMap.get("id").asInt(), farmerMap.get("owner_id").asInt(),
				farmerMap.get("farm_name").getTextValue(), farmerMap.get("farm_address").getTextValue());
	}

	/**
	 * Method to parse alarm-json and return ArrayList of alarm-objects
	 * @param jsonObject Response containing a json
	 * @return
	 */
	public static ArrayList<Alarm> parseJsonAndReturnAlarms(Response jsonObject, ProgramFrame pf) {
		ArrayList<Alarm> listOfAlarms = new ArrayList<Alarm>();
		System.out.println(jsonObject.msg);
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory factory = mapper.getJsonFactory();
			JsonParser parser = factory.createJsonParser(jsonObject.msg);
			JsonNode input = mapper.readTree(parser);

			int count = input.get("count").asInt();
			for (int i = 1; i <= count; i++) {
				Map<String, JsonNode> alarmMap = new HashMap<String, JsonNode>();
				JsonNode alarm = input.get(Integer.toString(i));
				Iterator<Entry<String, JsonNode>> nodeIterator = alarm.getFields();
				while (nodeIterator.hasNext()) {
					Entry<String, JsonNode> entry = nodeIterator.next();
					alarmMap.put(entry.getKey(), entry.getValue());
				}

				Alarm a = new Alarm(alarmMap.get("id").asInt(), pf.getSheepPanel().getSheep(alarmMap.get("sheep_id").asInt()),
						alarmMap.get("alarm_start_date").asText(), alarmMap.get("alarm_text").asText());
				listOfAlarms.add(a);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfAlarms;
	}
}
