package serverconnection;

import characters.Farmer;
import characters.Sheep;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

public class GetAndParseJson {
	
	private Map<String, JsonNode> farmerMap;
	private Map<String, JsonNode> sheepMap;
	private Sheep testSheep;
	private Farmer testFarmer;
	
	public GetAndParseJson(String jsonObject) {
		//String json = "{\"Farmer\":{\"farmerId\":\"1243556\",\"farmerHash\":\"aslfkewj234HÅKONølk324jl2\",\"farmerUsername\":\"hakloev\",\"farmerEmail\":\"hakloev@derp.com\",\"SheepObject\":{\"sheepId\":\"123445656\",\"nick\":\"link\",\"birthYear\":\"1992\",\"lat\":\"62.38123\",\"long\":\"9.16686\"}}}";
		parseJson(jsonObject);
	}
	
	public Farmer getFarmer() {
		return testFarmer;
	}
	
	public Sheep getSheep() {
		return testSheep;
	}
	
	private void createSheep() {
		testSheep = new Sheep(sheepMap.get("sheepId").asInt(), sheepMap.get("nick").asText(), 
				sheepMap.get("birthYear").asInt(), testFarmer, 
				sheepMap.get("lat").asDouble(), sheepMap.get("long").asDouble());
		//System.out.println(testSheep.toString());
	}
	
	private void createFarmer() {
		testFarmer = new Farmer(farmerMap.get("farmerId").asInt(), farmerMap.get("farmerHash").asText(),
				farmerMap.get("farmerUsername").asText(), farmerMap.get("farmerEmail").asText());
		//System.out.println(testFarmer.toString());
	}

	public void parseJson(String jsonObject) {
		farmerMap = new HashMap<String, JsonNode>();
		sheepMap = new HashMap<String, JsonNode>();
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory factory = mapper.getJsonFactory();
			JsonParser parser = factory.createJsonParser(jsonObject);
			JsonNode input = mapper.readTree(parser);
			
			Iterator<Entry<String, JsonNode>> nodeIterator = input.get("Farmer").getFields();
			
			int count = 0;
			while (nodeIterator.hasNext()) {
				if (count == 4) {
					nodeIterator.next();
					continue;
				}
				Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) nodeIterator.next();
				farmerMap.put(entry.getKey(), entry.getValue());
				//Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) nodeIterator.next();
				//System.out.println("key-->" + entry.getKey() + " value-->" + entry.getValue());
				//System.out.println(farmer.keySet());
				count++;
			}	
			
			if (input.findValue("SheepObject") != null) {
			
				Iterator<Entry<String, JsonNode>> nodeIterator1 = input.get("Farmer").get("SheepObject").getFields();
			
				while (nodeIterator1.hasNext()) {
					Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) nodeIterator1.next();
					sheepMap.put(entry.getKey(), entry.getValue());
					//System.out.println("key-->" + entry.getKey() + " value-->" + entry.getValue());
				}
			}
				
		} catch (IOException e) {
			
		} catch (Exception e) {
			
		}
		
		createFarmer();
		createSheep();
		
	}
	
//	public static void main(String[] args) {
//		String json = "{\"Farmer\":{\"farmerId\":\"1243556\",\"farmerHash\":\"aslfkewj234HÅKONølk324jl2\",\"farmerUsername\":\"hakloev\",\"farmerEmail\":\"hakloev@derp.com\",\"SheepObject\":{\"sheepId\":\"123445656\",\"nick\":\"link\",\"birthYear\":\"1992\",\"lat\":\"62.38000\",\"long\":\"9.16686\"}}}";
//		GetAndParseJson jr = new GetAndParseJson(json);
//		jr.createFarmer();
//		jr.createSheep();
//		
//	}
	
}
