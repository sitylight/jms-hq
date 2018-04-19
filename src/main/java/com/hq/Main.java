// Copyright (c) 1998-2018 Core Solutions Limited. All rights reserved.
// ============================================================================
// CURRENT VERSION CNT.5.0.1
// ============================================================================
// CHANGE LOG
// CNT.5.0.1 : 2018-04-17, derrick.liang, creation
// ============================================================================
package com.hq;

import com.hq.client.Consumer2;
import com.hq.client.HornetQClient;
import com.hq.client.HornetQConsumer;
import com.hq.client.HornetQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Session;

/**
 * @author derrick.liang
 */
public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String HOST = "localhost";
    private static final int PORT = 5445;
    private static final String USERNAME = "guest";
    private static final String PASSWORD = "guest";

    // need to change hornetQ -> config\stand-alone\non-clustered\hornetq-configuration.xml
    // add "createDurableQueue" permission for "guest"
    public static void main(String[] args) throws JMSException, InterruptedException {
        String topicName = "derrick-test";
        Session session = new HornetQClient(HOST, PORT).connect(USERNAME, PASSWORD);
        HornetQProducer producer = new HornetQProducer(session);
        HornetQConsumer hornetQConsumer = new HornetQConsumer(session);

        new Consumer2(session, topicName);
        hornetQConsumer.createSubscriber(topicName);
        while (true) {
            String msg = "derrick is testing";
            producer.send(topicName, msg);
            logger.info("send msg:" + msg);
            Thread.sleep(3000L);
        }
        /*String receiveMsg = hornetQConsumer.receiveMessage();
        logger.info("receive message is: " + receiveMsg);*/
    }
}
