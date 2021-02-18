package com.tritoncubed.launchapi;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

public class LaunchDB {
	
	private static final AmazonDynamoDB db;
	
	static {
		db = AmazonDynamoDBClient.builder()
				.withRegion(Regions.US_EAST_2)
				.build();
	}
	
	public static void put(Payload payload) {
		Map<String, AttributeValue> map = mapFromPayload(payload);
		db.putItem("LaunchDB", map);
	}
	
	public static Payload get(String utc) {
		Map<String, AttributeValue> map = new HashMap<>();
		map.put("utc", new AttributeValue().withN(utc));
		map = db.getItem("LaunchDB", map).getItem();
		return payloadFromMap(map);
	}
	
	private static Map<String, AttributeValue> mapFromPayload(Payload payload) {
		HashMap<String, AttributeValue> map = new HashMap<>();
		map.put("utc", new AttributeValue().withN(payload.utc));
		map.put("temp", new AttributeValue().withS(payload.temp));
		return map;
	}
	
	private static Payload payloadFromMap(Map<String, AttributeValue> map) {
		Payload payload = new Payload();
		payload.utc = map.get("utc").getN();
		payload.temp = map.get("temp").getS();
		return payload;
	}
}
