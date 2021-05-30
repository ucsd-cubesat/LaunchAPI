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
     * acceleration in the x direction from accelerometer 
     * Units are meters per second
     * @directive  getAccel_X returns acceleration of x axis
     * @directive  setAccel_X takes acceleration as arg and sets private var 
     */
    private float accel_X;
    Float accel_x = accel_X;
    public float getAccel_X() { return accel_x; }
    public void setAccel_X(float accel_X) { this.accel_x = accel_X; }
   
    /**
     * acceleration in the y direction from accelerometer 
     * Units are meters per second
     * @directive  getAccel_Y returns acceleration of y axis
     * @directive  setAccel_Y takes acceleration as arg and sets private var 
     */
    private float accel_Y;
    Float accel_y = accel_Y;
    public float getAccel_Y() { return accel_y; }
    public void setAccel_Y(float accel_Y) { this.accel_y = accel_Y; }
    
    /**
     * 
     * acceleration in the z direction from accelerometer 
     * Units are meters per second
     * @directive  getAccel_Z returns acceleration of z axis
     * @directive  setAccel_Z takes acceleration as arg and sets private var 
     */
    private float accel_Z;
    Float accel_z = accel_Z;
    public float getAccel_Z() { return accel_z; }
    public void setAccel_Z(float accel_Z) { this.accel_z = accel_Z; }
    
    /**
     * angular motion in the x direction from gyroscope
     * Units are millidegrees per second
     * @param getGyro_X return angular motion of x axis
     * @param setGryo_X takes ang motion as arg and sets private var  
     */
    private float gyro_X;
    Float gyro_x = gyro_X;
    public float getGyro_X() { return gyro_x; }
    public void setGyro_X(float gyro_X) { this.gyro_x = gyro_X; }
    
    /**
     * angular motion in the y direction from gyroscope
     * Units are millidegrees per second
     * @directive  getGyro_Y return angular motion of y axis
     * @directive  setGryo_Y takes ang motion as arg and sets private var  
     */
    private float gyro_Y;
    Float gyro_y = gyro_Y;
    public float getGyro_Y() { return gyro_y; }
    public void setGyro_Y(float gyro_Y) { this.gyro_y = gyro_Y; }
    
    /**
     * angular motion in the z direction from gyroscope
     * Units are millidegrees per second
     * @directive  getGyro_Z return angular motion of z axis
     * @directive  setGryo_Z takes ang motion as arg and sets private var  
     */
    private float gyro_Z;
    Float gyro_z = gyro_Z;
    public float getGyro_Z() { return gyro_z; }
    public void setGyro_Z(float gyro_Z) { this.gyro_z = gyro_Z; }
    
    /**
     * magnetic field in the x direction from Magnetosensor
     * Units are milli gauss
     * @directive  getMag_X return magnetic field of x axis
     * @directive  setMag_X takes magnetic field as arg and sets private var  
     */
    private float mag_X;
    Float mag_x = mag_X;
    public float getMag_X() { return mag_x; }
    public void setMag_X(float mag_X) { this.mag_x = mag_X; }
    
    /**
     * magnetic field in the y direction from Magnetosensor
     * Units are milli gauss
     * @directive  getMag_Y return magnetic field of y axis
     * @directive  setMag_Y takes magnetic field as arg and sets private var  
     */
    private float mag_Y;
    Float mag_y = mag_Y;
    public float getMag_Y() { return mag_y; }
    public void setMag_Y(float mag_Y) { this.mag_y = mag_Y; }
    
    /**
     * magnetic field in the z direction from Magnetosensor
     * Units are milli gauss
     * @directive  getMag_Z return magnetic field of z axis
     * @directive  setMag_Z takes magnetic field as arg and sets private var  
     */
    private float mag_Z;
    Float mag_z = mag_Z;
    public float getMag_Z() { return mag_z; }
    public void setMag_Z(float mag_Z) { this.mag_z = mag_Z; }
    
    /**
     * temp_C from thermometer  
     * Units are Celsius
     * @directive  getTemp_C return temperature in celsius
     * @directive  setTemp_C takes temperature as arg and sets private var 
     */
    private float temp_C;
    Float temp_c = temp_C;
    public float getTemp_C() { return temp_c; }
    public void setTemp_C(float temp_C) { this.temp_c = temp_C; }
    
    
    /**
     * Creates static instance of enum 
     */
    public static final FS_XL fs_xl = FS_XL.FS_XL_2;
    
    /**
     *  Determines the scale and precision of the accelerometer
     */
    public enum FS_XL {
    	FS_XL_2(0.061f),
    	FS_XL_4(0.122f),
    	FS_XL_8(0.244f),
    	FS_XL_16(0.732f);
    	
    	public final float conversionFactor;
    	
    	private FS_XL(float convFact) {	
    		this.conversionFactor = convFact * 9.80665f / 1000;
    	}	
    }
    
    /**
     * Determines the scale and precision of the gyroscope  
     */
    public enum FS_G {
    	FS_G_245(8.75f),
    	FS_G_500(17.5f),
    	FS_G_2000(70.0f);
    	
    	public final float conversionFactor;
    	
    	private FS_G(float convFact) {
    		this.conversionFactor = convFact / 1000;
    	}
    }
    
    /**
     * Determines the scale and precision of the magnetosensor
     */
    enum FS_M {
    	FS_M_4(0.14f),
    	FS_M_8(0.29f),
    	FS_M_12(0.43f),
    	FS_M_16(0.58f);
    	
    	public final float conversionFactor;
    	
    	private FS_M(float convFact) {
    		this.conversionFactor = convFact / 1000;
    	}
    }
    
    /**
     * Payload Constructor 
     * @param accel_x acceleratoin on x axis
     * @param accel_y acceleratoin on y axis 
     * @param accel_z acceleration on z axis
     * @param gyro_x angular motion in the x direction
     * @param gyro_y angular motion in the y direction
     * @param gyro_z angular motion in the z direction
     * @param mag_x magnetic field in the x direction
     * @param mag_y magnetic field in the y direction
     * @param mag_z magnetic field in the z direction
     * @param temp_c temperature from payload
     */
    public Payload(short accel_xx, short accel_yy, short accel_zz, 
    short gyro_xx, short gyro_yy, short gyro_zz, short mag_xx, 
    short mag_yy, short mag_zz, float temp_cc) {
    	
    	this.accel_x = accel_xx * fs_xl.conversionFactor;
    	this.accel_y = accel_yy * fs_xl.conversionFactor;
    	this.accel_z = accel_zz * fs_xl.conversionFactor;
    	this.gyro_x = gyro_xx * fs_xl.conversionFactor;
    	this.gyro_y = gyro_yy * fs_xl.conversionFactor;
    	this.gyro_z = gyro_zz * fs_xl.conversionFactor;
    	this.mag_x = mag_xx * fs_xl.conversionFactor;
    	this.mag_y = mag_yy * fs_xl.conversionFactor;
    	this.mag_z = mag_zz * fs_xl.conversionFactor;
    	this.temp_c = temp_cc;
    }
    
    /**
     * Generates all payload attributes in a JSON format
     */
    @Override
    public String toString() {
    	return String.format("{ \"accel_X\" : %f, \"accel_Y\" : %f, \"accel_Z\" : %f, \"gyro_X\" : %f, \"gyro_Y\" : %f, \"gyro_Z\" : %f, \"mag_X\" : %f, \"mag_Y\" : %f, \"mag_Z\" : %f, \"temp_C\" : %f, \"utc\" : %f,}" , this.getAccel_X(), this.getAccel_Y(), this.getAccel_Z(), this.getGyro_X(), this.getGyro_Y(), this.getGyro_Z(), this.getMag_X(), this.getMag_Y(), this.getMag_Z(), this.getTemp_C(), this.getUtc());
    } 
}
