package com.tritoncubed.launchapi;

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
     * UTC time in milliseconds, from the GPS
     */
    @DynamoDBHashKey
    private Long utc;
    public Long getUtc() { return utc; }
    public void setUtc(Long utc) { this.utc = utc; }

    /**
     * acceleration in the x direction from accelerometer 
     * Units are meters per second
     */
    private Float accel_X;
    public Float getAccel_X() { return accel_X; }
    public void setAccel_X(Float accel_X) { this.accel_X = accel_X; }
   
    /**
     * acceleration in the y direction from accelerometer 
     * Units are meters per second
     */
    private Float accel_Y;
    public Float getAccel_Y() { return accel_Y; }
    public void setAccel_Y(Float accel_Y) { this.accel_Y = accel_Y; }
    
    /**
     * 
     * acceleration in the z direction from accelerometer 
     * Units are meters per second
     */
    private Float accel_Z;
    public Float getAccel_Z() { return accel_Z; }
    public void setAccel_Z(Float accel_Z) { this.accel_Z = accel_Z; }
    
    /**
     * angular motion in the x direction from gyroscope
     * Units are millidegrees per second
     */
    private Float gyro_X;
    public Float getGyro_X() { return gyro_X; }
    public void setGyro_X(Float gyro_X) { this.gyro_X = gyro_X; }
    
    /**
     * angular motion in the y direction from gyroscope
     * Units are millidegrees per second
     */
    private Float gyro_Y;
    public Float getGyro_Y() { return gyro_Y; }
    public void setGyro_Y(Float gyro_Y) { this.gyro_Y = gyro_Y; }
    
    /**
     * angular motion in the z direction from gyroscope
     * Units are millidegrees per second
     */
    private Float gyro_Z;
    public Float getGyro_Z() { return gyro_Z; }
    public void setGyro_Z(Float gyro_Z) { this.gyro_Z = gyro_Z; }
    
    /**
     * magnetic field in the x direction from Magnetosensor
     * Units are milli gauss
     */
    private Float mag_X;
    public Float getMag_X() { return mag_X; }
    public void setMag_X(Float mag_X) { this.mag_X = mag_X; }
    
    /**
     * magnetic field in the y direction from Magnetosensor
     * Units are milli gauss
     */
    private Float mag_Y;
    public Float getMag_Y() { return mag_Y; }
    public void setMag_Y(Float mag_Y) { this.mag_Y = mag_Y; }
    
    /**
     * magnetic field in the z direction from Magnetosensor
     * Units are milli gauss
     */
    private Float mag_Z;
    public Float getMag_Z() { return mag_Z; }
    public void setMag_Z(Float mag_Z) { this.mag_Z = mag_Z; }
    
    /**
     * temp_C from thermometer  
     * Units are Celsius
     */
    private Float temp_C;
    public Float getTemp_C() { return temp_C; }
    public void setTemp_C(Float temp_C) { this.temp_C = temp_C; }
    
    /**
     * Latitude from GPS
     * Units are trigonometric degrees
     */
    private Float latitude;
    public Float getLatitude() { 
    	return latitude;
    }
    public void setLatitude(Float latitude) {
    	this.latitude = latitude;
    }
    
    /**
     * Longitude from GPS
     * Units are trigonometric degrees
     */
    private Float longitude;
    public Float getLongitude() {
    	return longitude;
    }
    public void setLongitude(Float longitude) {
    	this.longitude = longitude;
    }
    
    /**
     * Creates static instance of enum 
     */
    public static final FS_XL fs_xl = FS_XL.FS_XL_4;
    public static final FS_G fs_g = FS_G.FS_G_2000;
    public static final FS_M fs_m = FS_M.FS_M_8;
    
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
     * @param latitude latitudinal position on earth 
     * @param longitude longitudinal position on earth
     */
    public Payload(short accel_x, short accel_y, short accel_z, 
    short gyro_x, short gyro_y, short gyro_z, short mag_x, 
    short mag_y, short mag_z, float temp_c, float latitude, float longitude, long utc) {
    	
    	this.accel_X = accel_x * fs_xl.conversionFactor;
    	this.accel_Y = accel_y * fs_xl.conversionFactor;
    	this.accel_Z = accel_z * fs_xl.conversionFactor;
    	this.gyro_X = gyro_x * fs_g.conversionFactor;
    	this.gyro_Y = gyro_y * fs_g.conversionFactor;
    	this.gyro_Z = gyro_z * fs_g.conversionFactor;
    	this.mag_X = mag_x * fs_m.conversionFactor;
    	this.mag_Y = mag_y * fs_m.conversionFactor;
    	this.mag_Z = mag_z * fs_m.conversionFactor;
    	this.temp_C = temp_c;
    	this.latitude = latitude;
    	this.longitude = longitude;
    	this.utc = utc;
    }
    
    /**
     * No-args constructor for creating blank Payload
     */
    public Payload() {
        
    }
    
    /**
     * Generates all payload attributes in a JSON format
     */
    @Override
    public String toString() {
    	return String.format(
    			"{ \"accel_X\" : %f, \"accel_Y\" : %f, \"accel_Z\" : %f, \"gyro_X\" : %f, \"gyro_Y\" : %f, \"gyro_Z\" : %f, "
    			+ "\"mag_X\" : %f, \"mag_Y\" : %f, \"mag_Z\" : %f, \"temp_C\" : %f, \"utc\" : %d, \"latitude\" : %f, \"longitude\" : %f}" , 
    			this.accel_X, this.accel_Y, this.accel_Z, this.gyro_X, this.gyro_Y, 
    			this.gyro_Z, this.mag_X, this.mag_Y, this.mag_Z, this.temp_C, this.utc, this.latitude, this.longitude);
    } 
}
