// Copyright (c) 1998-2018 Core Solutions Limited. All rights reserved.
// ============================================================================
// CURRENT VERSION CNT.5.0.1
// ============================================================================
// CHANGE LOG
// CNT.5.0.1 : 2018-04-17, derrick.liang, creation
// ============================================================================
package com.hq.client;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 * @author derrick.liang
 */
public class HornetQProducer {
    private final Session session;

    public HornetQProducer(Session session) {
        this.session = session;
    }

    public void send(String topicName, String message) throws JMSException {
        Topic topic = session.createTopic(topicName);
        MessageProducer producer = session.createProducer(topic);
        producer.setDeliveryDelay(3000);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        TextMessage textMessage = session.createTextMessage(message);
        producer.send(textMessage);
    }
}
