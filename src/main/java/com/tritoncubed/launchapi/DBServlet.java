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
    	byte[] raw = request.getInputStream().readAllBytes(); // Potential problem for large amounts of data (File channel?)
		ByteBuffer buffer = ByteBuffer.wrap(raw).order(ByteOrder.LITTLE_ENDIAN);
		
			short accel_X = buffer.getShort();
			short accel_Y = buffer.getShort();
			short accel_Z = buffer.getShort();
			float temp_C = buffer.getFloat();
			
			Payload payload = new Payload(accel_X, accel_Y, accel_Z, temp_C);
			
			if(accel_X == 0) { response.setStatus(400); response.getWriter().append("{\"error\" : \"No accel_X value.\" }"); }
			else { payload.setAccel_X(accel_X); }
			if(accel_Y == 0) { response.setStatus(400); response.getWriter().append("{\"error\" : \"No accel_Y value.\"}"); }
			else { payload.setAccel_Y(accel_Y); }
			if(accel_Z == 0) { response.setStatus(400); response.getWriter().append("{\"error\" : \"No accel_Z value.\"}"); }
			else { payload.setAccel_Z(accel_Z); }
			if(temp_C == 0) { response.setStatus(400); response.getWriter().append("{\"error\" : \"No temp_C value.\"}"); }
			else { payload.setTemp_C(temp_C); }
		try {
			if(payload.checkPL() == true) { //checks if payload is null and inserts into DB if true
				LaunchDB.put(payload); // inserting payload into DB
			}
		}
		catch(Exception e) {
			response.sendError(400, "Payload is null for DB insertion");
		}
    }
}
