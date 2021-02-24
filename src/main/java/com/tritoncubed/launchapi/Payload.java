package com.tritoncubed.launchapi;

/** 
 * A POJO containing the data for a payload made through an HTTP request. Payloads are serialized
 * to, and deserialized from, the Dynamo DB as an intermediate format. Both the front-facing HTTP
 * API and back-facing Amazon code should conform to the Payload format.
 * 
 * To better automate the connection to the back-end, fields must follow these guidelines:
 * 
 * - Compound types are not allowed, such as:
 *   - Arrays
 *   - Containers (List, Map, etc)
 *   - Other POJOs
 * - Numeric values must be of their object type; i.e. Integer, Double, etc
 * 
 * Be sure to document the source of each field, including such information as:
 *   - The source of the data point
 *   - Units of measurement, where applicable
 *   - A description of the field
 */
public class Payload {

	/**
	 * UTC time in seconds, from the GPS
	 */
	public Long utc;
	
	/**
	 * TODO
	 */
	public String temp;

}
