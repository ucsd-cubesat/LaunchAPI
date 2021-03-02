package com.tritoncubed.launchapi;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.tritoncubed.launchapi.exceptions.APIException;

public class LaunchDB {

    /**
     * The Dynamo instance to our table.
     */
    private static final AmazonDynamoDB TABLE;

    static {
        TABLE = AmazonDynamoDBClient.builder()
                .withRegion(Regions.US_EAST_2)
                .build();
    }

    /**
     * Puts a Payload into the Dynamo table.
     *
     * @param payload The Payload to put into the table.
     * @throws APIException Upon Dynamo error, or internal error
     */
    public static void put(Payload payload) throws APIException {
        try {
			DynamoDBMapper mapper = new DynamoDBMapper(TABLE);
			mapper.save(payload);
        } catch (Exception e) {
            throw new APIException(e);
        }
    }

    /**
     * Retrieves a Payload from the Dynamo table.
     *
     * @param utc The UTC time of the payload.
     * @return A Payload matching the given UTC time
     * @throws APIException Upon Dynamo error, or internal error
     */
    public static Payload get(Long utc) throws APIException {
        try {
			DynamoDBMapper mapper = new DynamoDBMapper(TABLE);
			return mapper.load(Payload.class, utc);
        } catch (Exception e) {
            throw new APIException(e);
        }
    }
}
