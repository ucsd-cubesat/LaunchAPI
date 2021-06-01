package com.tritoncubed.launchapi;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/db")
public class DBServlet extends HttpServlet {

    private static final long serialVersionUID = 8090576189806039189L;

    public DBServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String utc = request.getParameter("utc");
        if (utc == null) {
            Payload payload = LaunchDB.get();
            response.getWriter().append(payload.toString());
        } else {
            Payload payload = LaunchDB.get(Long.parseLong(utc));
            response.getWriter().append(payload.toString());
        }
    }
  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	byte[] raw = request.getInputStream().readAllBytes(); 
		ByteBuffer buffer = ByteBuffer.wrap(raw).order(ByteOrder.LITTLE_ENDIAN);
				
		System.out.println("Buffer's capacity: " + buffer.capacity());
		
		if(buffer.capacity() != 48) { 
			response.setStatus(400); 
			response.getWriter().append("{\"error\" : \"Payload is not 48 bytes. Got " + buffer.capacity() + "\" }" ); 
			return;
		} 
		// Normalize values and insert into payload 
		short accel_X = buffer.getShort(); // 2 bytes
		short accel_Y = buffer.getShort(); // 2 bytes
		short accel_Z = buffer.getShort(); // 2 bytes
		short gyro_X = buffer.getShort(); // 2 bytes
		short gyro_Y = buffer.getShort(); // 2 bytes
		short gyro_Z = buffer.getShort(); // 2 bytes
		short mag_X = buffer.getShort(); // 2 bytes
		short mag_Y = buffer.getShort(); // 2 bytes
		short mag_Z = buffer.getShort(); // 2 bytes
		float temp_C = buffer.getFloat(); // 4 bytes
		
		byte[] lat = new byte[12];
		byte[] lon = new byte[12];
		buffer.get(lat);
		String latitude = stringFromBytes(lat);
		char NS = (char) buffer.get(); // N S as 1 byte (N +, S -)
		buffer.get(lon);
		String longitude = stringFromBytes(lon); 
		char EW = (char) buffer.get(); //E W as 1 byte (E +, W -)
		// convert to float
		if(latitude.isEmpty()) {
			response.setStatus(400);
			//TODO throw JSON error
			return;
		}
		
		float lat_float = Integer.valueOf(latitude.substring(0,2)) + (Float.valueOf(latitude.substring(2)) / 60);
		float lon_float = Integer.valueOf(longitude.substring(0,3)) + (Float.valueOf(longitude.substring(3)) / 60);
		
		if(NS == 'S') {
			lat_float *= -1;
		}
		if(EW == 'W') {
			lon_float *= -1;
		}
	
		long utc = Calendar.getInstance().getTimeInMillis();
		
		Payload payload = new Payload(accel_X, accel_Y, accel_Z, gyro_X, gyro_Y, gyro_Z, mag_X, mag_Y, mag_Z, temp_C, lat_float, lon_float, utc);
		
		System.out.println(payload.toString());
	
		response.getWriter().append(payload.toString());
		LaunchDB.put(payload); // inserting payload into DB
    }
    
    private static String stringFromBytes(byte[] bytes) {
        int idxOfNull;
        for (idxOfNull = 0; idxOfNull < bytes.length && bytes[idxOfNull] != 0; idxOfNull++);
        bytes = Arrays.copyOfRange(bytes, 0, idxOfNull);
        return new String(bytes);
    }
    
    
}
