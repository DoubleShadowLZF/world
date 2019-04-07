package org.webserver.demo.task;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

@Slf4j
public class RequestTask implements Runnable {
    private Socket connection;

    public RequestTask(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            handlerRequest(connection);
        } catch (IOException e) {
            log.error("{}",e);
        }
    }

    private void handlerRequest(Socket connection) throws IOException {
        log.info("handle the request ...");
        InputStream in = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            in = connection.getInputStream();
            isr = new InputStreamReader(in);
            br = new BufferedReader(isr);
            String text ;
            while ((text = br.readLine()) != null) {
                log.info(">>>{}", text);
                br.readLine();
            }
        } finally {
            connection.shutdownInput();
            br.close();
            isr.close();
            in.close();
        }

    }
}
