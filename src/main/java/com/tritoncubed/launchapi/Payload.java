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
     * acceleration in the x direction (units) from accelerometer 
     * Units
     */
    private float accel_X;
    public float getAccel_X() { return accel_X; }
    public void setAccel_X(float accel_X) { this.accel_X = accel_X; }
   
    /**
     * accel_Y from accelerometer 
     */
    private float accel_Y;
    public float getAccel_Y() { return accel_Y; }
    public void setAccel_Y(float accel_Y) { this.accel_Y = accel_Y; }
    
    /**
     * accel_Z from accelerometer 
     */
    private float accel_Z;
    public float getAccel_Z() { return accel_Z; }
    public void setAccel_Z(float accel_Z) { this.accel_Z = accel_Z; }
    
    /**
     * temp_C from thermometer  
     */
    private float temp_C;
    public float getTemp_C() { return temp_C; }
    public void setTemp_C(float temp_C) { this.temp_C = temp_C; }
    
    
    /**
     * Creation
     */
    FS_XL fs_xl;
    
    /**
     *  Determines the scale and precision of the accelerometer
     *  g is the standard acceleration due to Earth's gravity
     */
    public enum FS_XL {
    	FS_XL_2((byte) 0b00),
    	FS_XL_4((byte) 0b01),
    	FS_XL_8((byte) 0b10),
    	FS_XL_16((byte) 0b11);
    	
    	private final byte FS_XL;
    	
    	private FS_XL(final byte someByte) {
    		this.FS_XL = someByte;
    	}
    	public byte getFS_XL() {
    		return FS_XL;
    	}
    }
    
    /**
     * Determines the scale and precision of the gyroscope 
     */
    public enum FS_G {
    	FS_G_245((byte)0b00),
    	FS_G_500((byte)0b01),
    	FS_G_2000((byte)0b11);
    	
    	private final byte FS_G;
    	
    	private FS_G(final byte someByte) {
    		this.FS_G = someByte;
    	}
    	
    	public byte getFS_G() {
    		return FS_G;
    	}
    }
    
    /**
     * Determines the scale and precision of the magnetosensor
     */
    enum FS_M {
    	FS_M_4((byte)0b00),
    	FS_M_8((byte)0b01),
    	FS_M_12((byte)0b10),
    	FS_M_16((byte)0b11);
    	
    	private final byte FS_M;
    	
    	private FS_M(final byte someByte) { 		
    		this.FS_M = someByte;
    	}
    	
    	public byte getFS_M() {
    		return FS_M;
    	}
    }
    
    
    /**
     * Payload Constructor 
     * @param accel_x acceleratoin on x axis
     * @param accel_y acceleratoin on y axis 
     * @param accel_z acceleration on z axis
     * @param temp_c temperature from payload
     */
    public Payload(float accel_x, float accel_y, float accel_z, float temp_c) {
    	this.accel_X = accel_x;
    	this.accel_Y = accel_y;
    	this.accel_Z = accel_z;
    	this.temp_C = temp_c;
    }
    
    @Override
    public String toString() {
    	return String.format("{\"temp_C\" : %f, \"utc\" : %f, \"accel_X\" : %f, \"accel_Y\" : %f, \"accel_Z\" : %f}" , this.getTemp_C(), this.getUtc(), this.getAccel_X(), this.getAccel_Y(), this.getAccel_Z());
    }
    public float convAccel_X(short accel_x, short accel_y, short accel_z) {
    	final FS_XL FS_XL_2 = FS_XL.FS_XL_2;
    	final FS_XL FS_XL_4 = FS_XL.FS_XL_4;
    	final FS_XL FS_XL_8 = FS_XL.FS_XL_8;
    	float resolution = this.fs_xl == FS_XL_2.getFS_XL() ? 0.061f :
    					   this.fs_xl == FS_XL_4.getFS_XL() ? 0.122f :
    					   this.fs_xl == FS_XL_8.getFS_XL() ? 0.244f : 0.732;
    	
    	
    }
    
    
}
