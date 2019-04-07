package org.webserver.demo;

import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketTest {
    @Test
    public void connectServer() throws IOException {
        PrintWriter pw = null;
        OutputStream out= null;
        try{
            Socket socket = new Socket("192.168.100.124",80);
            out = socket.getOutputStream();
            pw = new PrintWriter(out);
            pw.write("Hello world");
        }finally {
            pw.flush();
            pw.close();
            out.flush();
            out.close();
        }
    }
}
