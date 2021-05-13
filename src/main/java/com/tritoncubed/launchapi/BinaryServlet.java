package com.tritoncubed.launchapi;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/binary")
public class BinaryServlet extends HttpServlet {

    private static final long serialVersionUID = -1179261162223504693L;

    public BinaryServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        byte[] raw = request.getInputStream().readAllBytes();
        ByteBuffer buffer = ByteBuffer.wrap(raw).order(ByteOrder.LITTLE_ENDIAN);
        
        int A = buffer.getInt();
        
        float B = buffer.getFloat();
        
        byte[] c = new byte[32]; buffer.get(c);
        String C = stringFromBytes(c);
        
        response.getWriter().printf("Payload{A=%d, B=%f, C=%s}\n", A, B, C);
    }
    
    private static String stringFromBytes(byte[] bytes) {
        int idxOfNull;
        for (idxOfNull = 0; idxOfNull < bytes.length && bytes[idxOfNull] != 0; idxOfNull++);
        bytes = Arrays.copyOfRange(bytes, 0, idxOfNull);
        return new String(bytes);
    }
}
