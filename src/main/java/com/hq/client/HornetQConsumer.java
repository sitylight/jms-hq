// Copyright (c) 1998-2018 Core Solutions Limited. All rights reserved.
// ============================================================================
// CURRENT VERSION CNT.5.0.1
// ============================================================================
// CHANGE LOG
// CNT.5.0.1 : 2018-04-17, derrick.liang, creation
// ============================================================================
package com.hq.client;

import org.hornetq.jms.client.HornetQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

/**
 * @author derrick.liang
 */
public class HornetQConsumer {
    private static Logger logger = LoggerFactory.getLogger(HornetQConsumer.class);
    private static final String EXTERNAL_CLIENT = "external-client";
    private final Session session;
    private TopicSubscriber subscriber;

    public HornetQConsumer(Session session) {
        this.session = session;
    }

    public void createSubscriber(String topicName) throws JMSException {
        Topic topic = session.createTopic(topicName);
//        MessageConsumer consumer = session.createConsumer(topic);
        MessageListener listener = message -> logger
                .info("receive message is: " + ((HornetQTextMessage) message).getText());
//        consumer.setMessageListener(listener);
//        subscriber.se
        subscriber = session.createDurableSubscriber(topic, EXTERNAL_CLIENT);
        subscriber.setMessageListener(listener);
//        return subscriber;
    }

    public String receiveMessage() throws JMSException {
        Message message = subscriber.receive();
        String text = ((TextMessage) message).getText();
        unsubscribe();
        return text;
    }

    public void unsubscribe() throws JMSException {
        subscriber.close();
        session.unsubscribe(EXTERNAL_CLIENT);
    }
}
