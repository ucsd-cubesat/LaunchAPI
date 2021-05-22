package com.tritoncubed.launchapi;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import java.util.HashMap;

/**
 * A POJO containing the data for a payload made through an HTTP request.
 * Payloads are serialized to, and deserialized from, the Dynamo DB as an intermediate format. Both
 * the front-facing HTTP API and back-facing Amazon API should conform to the Payload format.
 *
 * To better automate the connection to the back-end, fields must follow these guidelines:
 *   - Compound types (arrays, containers, other POJOs) are not allowed
 *   - Numeric values must be of their object type (i.e. Integer, Double, etc)
 *   - Getters and setters named by standard Java conventions must be publicly available
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
    @DynamoDBHashKey
    private Long utc;
    public Long getUtc() { return utc; }
    public void setUtc(Long utc) { this.utc = utc; }

    /**
     * TODO
     
    private String temp;
    public String getTemp() { return temp; }
    public void setTemp(String temp) { this.temp = temp; }
    */
    
    /*
     * accel_X from accelerometer 
     */
    @DynamoDBHashKey
    private Short accel_X;
    public Short getAccel_X() { return accel_X; }
    public void setAccel_X(Short accel_X) { this.accel_X = accel_X; }
   
    /*
     * accel_Y from accelerometer 
     */
    @DynamoDBHashKey
    private Short accel_Y;
    public Short getAccel_Y() { return accel_Y; }
    public void setAccel_Y(Short accel_Y) { this.accel_Y = accel_Y; }
    
    /*
     * accel_Z from accelerometer 
     */
    @DynamoDBHashKey
    private Short accel_Z;
    public Short getAccel_Z() { return accel_Z; }
    public void setAccel_Z(Short accel_Z) { this.accel_Z = accel_Z; }
    
    /*
     * temp_C from thermometer  
     */
    @DynamoDBHashKey
    private float temp_C;
    public float getTemp_C() { return temp_C; }
    public void setTemp_C(float temp_C) { this.temp_C = temp_C; }
    
    public boolean checkPL() {
    	if (utc != null && (temp != null && accel != null)) {
    		return true;
    	}
    	return false;
    }
    
    /*
     * Payload Constructor 
     * @param accel_x, accel_y, accel_z: acceleration from payload. temp_c: temperature from payload
     */
    public Payload(short accel_x, short accel_y, short accel_z, float temp_c) {
    	this.accel_X = accel_x;
    	this.accel_Y = accel_y;
    	this.accel_Z = accel_z;
    	this.temp_C = temp_c;
    }
    
    @Override
    public String toString() {
    	return String.format("{\"temp_C\" : %d, \"utc\" : %d, \"accel_X\" : %d, \"accel_Y\" : %d, \"accel_Z\" : %d}" , this.getTemp_C(), this.getUtc(), this.getAccel_X(), this.getAccel_Y(), this.getAccel_Z());
    }
}
