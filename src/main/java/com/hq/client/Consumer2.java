// Copyright (c) 1998-2018 Core Solutions Limited. All rights reserved.
// ============================================================================
// CURRENT VERSION CNT.5.0.1
// ============================================================================
// CHANGE LOG
// CNT.5.0.1 : 2018-04-18, derrick.liang, creation
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
import javax.jms.Topic;

/**
 * @author derrick.liang
 */
public class Consumer2 implements MessageListener {
    Logger logger = LoggerFactory.getLogger(Consumer2.class);
    private Session session;
    String topicName;

    public Consumer2(Session session, String topicName) throws JMSException {
        this.session = session;
        this.topicName = topicName;
        Topic topic = session.createTopic(topicName);
        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(this);
    }

    @Override
    public void onMessage(Message message) {
        logger.info("consumer2 receive message:" + ((HornetQTextMessage) message).getText());
    }
}
