package com.tritoncubed.launchapi;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.tritoncubed.launchapi.exceptions.APIException;
import com.tritoncubed.launchapi.exceptions.UnsupportedAttributeException;

public class LaunchDB {
	
	/**
	 * The Dynamo instance to our table.
	 */
	private static final AmazonDynamoDB db;
	
	static {
		db = AmazonDynamoDBClient.builder()
				.withRegion(Regions.US_EAST_2)
				.build();
	}

	/**
	 * Puts a Payload into the Dynamo table.
	 * @param payload The Payload to put into the table.
	 * @throws APIException Upon Dynamo error, or internal error
	 */
	public static void put(Payload payload) throws APIException {
		try {
			Map<String, AttributeValue> map = mapFromPayload(payload);
			db.putItem("LaunchDB", map);
		}
		catch (Exception e) {
			throw new APIException(e);
		}
	}
	
	/**
	 * Retrieves a Payload from the Dynamo table.
	 * @param utc The UTC time of the payload.
	 * @return A Payload matching the given UTC time
	 * @throws APIException Upon Dynamo error, or internal error
	 */
	public static Payload get(String utc) throws APIException {
		try {
			Map<String, AttributeValue> map = new HashMap<>();
			map.put("utc", new AttributeValue().withN(utc));
			map = db.getItem("LaunchDB", map).getItem();
			return payloadFromMap(map);
		}
		catch (Exception e) {
			throw new APIException(e);
		}
	}
	
	/**
	 * Generates an attribute map from a Payload object.
	 * @param payload The Payload to serialize.
	 * @return An attribute map that can be passed to {@link AmazonDynamoDB#putItem} and the like.
	 * @throws APIException Upon reflection error, or if a field is unsupported.
	 */
	private static Map<String, AttributeValue> mapFromPayload(Payload payload) throws APIException {
		HashMap<String, AttributeValue> map = new HashMap<>();
		
		Field fields[] = payload.getClass().getDeclaredFields();
		for (Field field : fields) try {
			
			// gather metadata
			String name = field.getName();
			Class<?> type = field.getType();
			AttributeValue attr = new AttributeValue();
			
			// parse the field
			if (type == String.class) {
				String s = (String) field.get(payload);
				attr.withS(s);
			}
			else if (type == Byte.class) {
				Byte n = (Byte) field.get(payload);
				attr.withN(n.toString());
			}
			else if (type == Short.class) {
				Short n = (Short) field.get(payload);
				attr.withN(n.toString());
			}
			else if (type == Integer.class) {
				Integer n = (Integer) field.get(payload);
				attr.withN(n.toString());
			}
			else if (type == Long.class) {
				Long n = (Long) field.get(payload);
				attr.withN(n.toString());
			}
			else if (type == Float.class) {
				Float n = (Float) field.get(payload);
				attr.withN(n.toString());
			}
			else if (type == Double.class) {
				Double n = (Double) field.get(payload);
				attr.withN(n.toString());
			}
			else throw new UnsupportedAttributeException(name, type); // fail-fast
			
			map.put(name, attr);
		}
		catch (Exception e) { // fail-fast
			throw new APIException(e);
		}
		
		return map;
	}
	
	/**
	 * Generates a Payload object from the attribute map returned by Dynamo.
	 * @param map Returned by {@link AmazonDynamoDB#getItem} and the like.
	 * @return A deserialized Payload.
	 * @throws APIException Upon reflection error, or if a field is unsupported.
	 */
	private static Payload payloadFromMap(Map<String, AttributeValue> map) throws APIException {
		Payload payload = new Payload();
		
		Field fields[] = payload.getClass().getDeclaredFields();
		for (Field field : fields) try {
			
			// gather metadata
			String name = field.getName();
			Class<?> type = field.getType();
			AttributeValue attr = map.get(name);
			
			// parse the field
			if (type == String.class) {
				String s = attr.getS();
				field.set(payload, s);
			}
			else if (type == Byte.class) {
				Byte n = Byte.parseByte(attr.getN());
				field.set(payload, n);
			}
			else if (type == Short.class) {
				Short n = Short.parseShort(attr.getN());
				field.set(payload, n);
			}
			else if (type == Integer.class) {
				Integer n = Integer.parseInt(attr.getN());
				field.set(payload, n);
			}
			else if (type == Long.class) {
				Long n = Long.parseLong(attr.getN());
				field.set(payload, n);
			}
			else if (type == Float.class) {
				Float n = Float.parseFloat(attr.getN());
				field.set(payload, n);
			}
			else if (type == Double.class) {
				Double n = Double.parseDouble(attr.getN());
				field.set(payload, n);
			}
			else throw new UnsupportedAttributeException(name, type); // fail-fast
		}
		catch (Exception e) { // fail-fast
			throw new APIException(e);
		}
		
		return payload;
	}
}
