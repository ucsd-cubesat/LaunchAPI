package com.tritoncubed.launchapi;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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
            response.setStatus(400);
            response.getWriter().append("{\"error\" : \"No UTC value.\" }");
        } else {
            Payload payload = LaunchDB.get(Long.parseLong(utc));
            response.getWriter().append(payload.toString());
        }
    }
  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	byte[] raw = request.getInputStream().readAllBytes(); 
		ByteBuffer buffer = ByteBuffer.wrap(raw).order(ByteOrder.LITTLE_ENDIAN);
		
		System.out.println("Buffer's capacity: " + buffer.capacity());
		
		if(buffer.capacity() != 10) { 
			response.setStatus(400); 
			response.getWriter().append("{\"error\" : \"Payload is not 10 bytes. Got " + buffer.capacity() + "\" }" ); 
			return;
		} 
		
		// Normalize values and insert into payload 
		short accel_X = buffer.getShort(); // 2 bytes
		short accel_Y = buffer.getShort(); // 2 bytes
		short accel_Z = buffer.getShort(); // 2 bytes
		float temp_C = buffer.getFloat(); // 4 bytes
		
		Payload payload = new Payload(accel_X, accel_Y, accel_Z, temp_C);
		
		System.out.println(payload.toString());
	
		response.getWriter().append(payload.toString());
		//LaunchDB.put(payload); // inserting payload into DB
    }
}
