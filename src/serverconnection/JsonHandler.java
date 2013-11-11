package serverconnection;

import characters.*;

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
 * @author maxmelander
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
	
	/**
	 * Method for parsing getFarmFromSheepID json from nethandler and returning a farm object which contains a list of area objects
	 * @param jsonObject Respons containing a json
	 * @return Returns a Farm object containing a list of Area objects
	 */
	public static Farm parseJsonAndReturnFarm(Response jsonObject) {
		//System.out.println(jsonObject.msg);
		ArrayList<Area> areaList = new ArrayList<Area>();
		Map<String, JsonNode> farmMap = new HashMap<String, JsonNode>();

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory factory = mapper.getJsonFactory();
			JsonParser parser = factory.createJsonParser(jsonObject.msg);
			JsonNode input = mapper.readTree(parser);
			
			Iterator<Entry<String, JsonNode>> nodeIterator = input.getFields();
			while (nodeIterator.hasNext()) {
				Entry<String, JsonNode> entry = nodeIterator.next();
				farmMap.put(entry.getKey(), entry.getValue());
			}			
		} catch (IOException e) {
			e.printStackTrace();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (farmMap.get("areas").get("count") == null){
			return null;
		}
		int count = farmMap.get("areas").get("count").asInt();	
		for (int i = 0; i < count; i++){
			int farmID = farmMap.get("areas").get(""+(i+1)).get("farm_id").asInt();
			String areaName = farmMap.get("areas").get(""+(i+1)).get("area_name").asText();
			String[] latList = farmMap.get("areas").get(""+(i+1)).get("area_latitude").asText().split(",");
			String[] longList = farmMap.get("areas").get(""+(i+1)).get("area_longitude").asText().split(",");
			ArrayList<Double> latListDouble = new ArrayList<Double>();
			ArrayList<Double> longListDouble = new ArrayList<Double>();
			ArrayList<Position> areaPositionList = new ArrayList<Position>();
			
			for (String d : latList){
				latListDouble.add(Double.parseDouble(d));
			}
			
			for (String d : longList){
				longListDouble.add(Double.parseDouble(d));
			}
			
			for (int x = 0; x < latListDouble.size(); x++){
				areaPositionList.add(new Position(latListDouble.get(x), longListDouble.get(x)));
			}
			
			areaList.add(new Area(areaName, farmID, areaPositionList, farmMap.get("areas").get(""+(i+1)).get("id").asInt()));
			
		}
		
		return new Farm(areaList, farmMap.get("farm").get("id").asInt(), farmMap.get("farm").get("owner_id").asInt(), 
				farmMap.get("farm").get("farm_name").asText(), farmMap.get("farm").get("farm_address").asText());

	}
	
	/**
	 * Method for parsing a new farm from farmcode after making a new farm.
	 * @param jsonObject Respons containing a json
	 * @return Returns a Farm object
	 */
	public static Farm parseJsonAndReturnNewFarm(Response jsonObject) {
		System.out.println(jsonObject.msg);
		ArrayList<Area> areaList = new ArrayList<Area>();
		Map<String, JsonNode> farmMap = new HashMap<String, JsonNode>();
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory factory = mapper.getJsonFactory();
			JsonParser parser = factory.createJsonParser(jsonObject.msg);
			JsonNode input = mapper.readTree(parser);
			
			Iterator<Entry<String, JsonNode>> nodeIterator = input.getFields();
			while (nodeIterator.hasNext()) {
				Entry<String, JsonNode> entry = nodeIterator.next();
				farmMap.put(entry.getKey(), entry.getValue());
			}			
		} catch (IOException e) {
			e.printStackTrace();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Farm(areaList, farmMap.get("id").asInt(), farmMap.get("owner_id").asInt(), 
				farmMap.get("farm_name").asText(), farmMap.get("farm_address").asText());
	}

	/**
	 * Method to parse alarm-json and return ArrayList of alarm-objects
	 * @param jsonObject Response containing a json
	 * @return
	 */
	public static ArrayList<Alarm> parseJsonAndReturnAlarms(Response jsonObject, ProgramFrame pf) {
		ArrayList<Alarm> listOfAlarms = new ArrayList<Alarm>();

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory factory = mapper.getJsonFactory();
			JsonParser parser = factory.createJsonParser(jsonObject.msg);
			JsonNode input = mapper.readTree(parser);

			int count = input.get("count").asInt();
			if (count == 0)  {
				return new ArrayList<Alarm>();
			}
			Iterator<Entry<String,JsonNode>> nodeIt = input.getFields();
			int countField = 0;
			while (nodeIt.hasNext()) {
				Entry<String, JsonNode> alarm = nodeIt.next();
				if (countField <= 2) {
					countField++;
					continue;
				}
				Map<String, JsonNode> alarmMap = new HashMap<String, JsonNode>();
				JsonNode alarmString = alarm.getValue();
				Iterator<Entry<String, JsonNode>> alarmIterator = alarmString.getFields();
				while (alarmIterator.hasNext()) {

					Entry<String, JsonNode> entry = alarmIterator.next();
					alarmMap.put(entry.getKey(), entry.getValue());
				}
				Alarm a = new Alarm(alarmMap.get("id").asInt(), pf.getSheepPanel().getSheep(alarmMap.get("sheep_id").asInt()),
						alarmMap.get("alarm_start_date").asText(), alarmMap.get("alarm_text").asText());
				a.getSheep().setLocation(alarmMap.get("sheep_latitude").asDouble(), alarmMap.get("sheep_longitude").asDouble());
				listOfAlarms.add(a);

				// LEGGER NÅ KUN TIL FØRSTE ALARM
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfAlarms;
	}
	
	/**
	 * Method for parsing getAlarms json from nethandler and returning an array list with area objects
	 * @param jsonObject json from the getAlarms method in the NetHandler class
	 * @return arraylist with area objects
	 */
	public static ArrayList<Area> parseJsonAndReturnAreas(Response jsonObject){
		Map<String, JsonNode> areaMap = new HashMap<String, JsonNode>();
		ArrayList<Area> areaList = new ArrayList<Area>();

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory factory = mapper.getJsonFactory();
			JsonParser parser = factory.createJsonParser(jsonObject.msg);
			JsonNode input = mapper.readTree(parser);
			
			Iterator<Entry<String, JsonNode>> nodeIterator = input.getFields();
			while (nodeIterator.hasNext()) {
				Entry<String, JsonNode> entry = nodeIterator.next();
				areaMap.put(entry.getKey(), entry.getValue());
			}			
		} catch (IOException e) {
			e.printStackTrace();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (areaMap.get("request_response_message").asText().equals("Feil: Ugyldig farmID")) {
			return new ArrayList<Area>();
		}
		int count = areaMap.get("count").asInt();
		for (int i = 0; i < count; i++){
			String[] latList = areaMap.get(""+(i+1)).get("area_latitude").asText().split(",");
			String[] longList = areaMap.get(""+(i+1)).get("area_longitude").asText().split(",");
			ArrayList<Double> latListDouble = new ArrayList<Double>();
			ArrayList<Double> longListDouble = new ArrayList<Double>();
			ArrayList<Position> areaPositionList = new ArrayList<Position>();
			
			for (String d : latList){
				latListDouble.add(Double.parseDouble(d));
			}
			
			for (String d : longList){
				longListDouble.add(Double.parseDouble(d));
			}
			
			for (int x = 0; x < latListDouble.size(); x++){
				areaPositionList.add(new Position(latListDouble.get(x), longListDouble.get(x)));
			}
	
			areaList.add(new Area(areaMap.get(""+(i+1)).get("area_name").asText(), areaMap.get(""+(i+1)).get("farm_id").asInt(), areaPositionList, areaMap.get(""+(i+1)).get("id").asInt()));
		}
		return areaList;
	
	}

	/**
	 * Method to parse json and return Log-object
	 * @param jsonObject Response containing json
	 * @return logItem-object with information about log
	 */
	public static ArrayList<LogItem> parseJsonAndReturnLog(Response jsonObject) {
	   	ArrayList<LogItem> logList = new ArrayList<LogItem>();

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory factory = mapper.getJsonFactory();
			JsonParser parser = factory.createJsonParser(jsonObject.msg);
			JsonNode input = mapper.readTree(parser);

			int count = input.get("count").asInt();
			for (int i = 1; i <= count; i++) {
				Map<String, JsonNode> logMap = new HashMap<String, JsonNode>();
				JsonNode log = input.get(Integer.toString(i));
				Iterator<Entry<String, JsonNode>> nodeIterator = log.getFields();
				while (nodeIterator.hasNext()) {
					Entry<String, JsonNode> entry = nodeIterator.next();
					logMap.put(entry.getKey(), entry.getValue());
				}

				LogItem l = new LogItem(logMap.get("id").asInt(), logMap.get("stat_date").asText(), logMap.get("last_latitude").asDouble(), logMap.get("last_longitude").asDouble()
					, logMap.get("last_pulse").asInt(), logMap.get("last_highest_pulse").asInt(), logMap.get("last_highest_pulse_date").asText()
					, logMap.get("last_weight_grams").asInt(), logMap.get("last_age").asInt(), logMap.get("last_nickname").asText(), logMap.get("sheep_id").asInt());
				logList.add(l);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return logList;
	}



}
