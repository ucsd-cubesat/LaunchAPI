package com.tritoncubed.launchapi;

import java.io.IOException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet that performs a small ping for the user. A simple GET request is made, and a simple
 * acknowledgement is sent back.
 */
@WebServlet("/ping")
public class PingServlet extends HttpServlet {

    private static final long serialVersionUID = 6659111915305916881L;

    public PingServlet() {
        super();
    }

    /**
     * This method contains the logic of the ping request. The request itself is contained in its
     * namesake object, and the response is created in its namesake object.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject json = Json.createObjectBuilder()
                .add("response", "pong")
                .build();
        response.getWriter().append(json.toString());
    }
}
