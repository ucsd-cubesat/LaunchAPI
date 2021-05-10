package com.tritoncubed.launchapi;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

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
            response.getWriter().append("Requires utc field.\n");
        } else {
            Payload payload = LaunchDB.get(Long.parseLong(utc));
            response.getWriter().append(String.format("{temp: %s}\n", payload.getTemp()));
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Payload payload = new Payload();

        String utc = request.getParameter("utc");
        if (utc == null) {
            utc = Long.toUnsignedString(Calendar.getInstance().getTimeInMillis());
            payload.setUtc(Long.parseLong(utc));
        }

        String temp = request.getParameter("temp");
        if (temp == null) {
            response.setStatus(400);
            response.getWriter().append("Requires temp field.\n");
        } else {
            //payload.setUtc(Long.parseLong(utc));
            payload.setTemp(temp);
            //LaunchDB.put(payload);
        }
        String accelXYZ = request.getParameter("accelXYZ");
        if (accelXYZ == null) {
            response.setStatus(400);
            response.getWriter().append("Requires accelXYZ field.\n");
        }
        else {
        	payload.setAccel(accelParse(accelXYZ));
        }
        if (payload.checkPL()) LaunchDB.put(payload);
    }
    
	private static HashMap<String, String> accelParse(String accelXYZ) {
		HashMap<String, String> accel = new HashMap<String, String>();
		
		String[] XYZ = accelXYZ.split("_", 3);
		accel.put("accelX", XYZ[0]);
		accel.put("accelY", XYZ[1]);
		accel.put("accelZ", XYZ[2]);
		return accel;
	}
}
