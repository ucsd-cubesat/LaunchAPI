package com.tritoncubed.launchapi;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * A POJO containing the data for a payload made through an HTTP request.
 * Payloads are serialized to, and deserialized from, the Dynamo DB as an intermediate format. Both
 * the front-facing HTTP API and back-facing Amazon API should conform to the Payload format.
 *
 * To better automate the connection to the back-end, fields must follow these guidelines:
 *   - Compound types (arrays, containers, other POJOs) are not allowed
 *   - Numeric values must be of their object type (i.e. Integer, Double, etc)
 *
 *
 * Be sure to document the source of each field, including such information as:
 *   - The source of the data point
 *   - Units of measurement, where applicable
 *   - A description of the field
 */
@DynamoDBTable(tableName = "LaunchDB")
public class Payload {

    /**
     * UTC time in seconds, from the GPS
     */
    private Long utc;
    @DynamoDBHashKey
    public Long getUtc() { return utc; }
    public void setUtc(Long utc) { this.utc = utc; }

    /**
     * TODO
     */
    private String temp;
    @DynamoDBAttribute
    public String getTemp() { return temp; }
    public void setTemp(String temp) { this.temp = temp; }
}
