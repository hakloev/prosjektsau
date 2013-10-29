package serverconnection;

import characters.Farmer;
import serverconnection.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;


/**
 * Class to handle JsonObjects
 * @author H��kon ��deg��rd L��vdal
 */

public class JsonHandler {
	
	/**
	 * Method to parse login-json and return a farmer object
	 * @param jsonObject
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

}