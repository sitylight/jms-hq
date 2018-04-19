// Copyright (c) 1998-2018 Core Solutions Limited. All rights reserved.
// ============================================================================
// CURRENT VERSION CNT.5.0.1
// ============================================================================
// CHANGE LOG
// CNT.5.0.1 : 2018-04-17, derrick.liang, creation
// ============================================================================
package com.hq.client;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.core.remoting.impl.netty.TransportConstants;
import org.hornetq.jms.client.HornetQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;
import java.util.HashMap;
import java.util.Map;

/**
 * @author derrick.liang
 */
public class HornetQClient {
    private final HornetQConnectionFactory connectionFactory;

    public HornetQClient(String host, int port) {
        Map<String, Object> connectionParams = new HashMap<String, Object>();
        connectionParams.put(TransportConstants.PORT_PROP_NAME, port);
        connectionParams.put(TransportConstants.HOST_PROP_NAME, host);
        TransportConfiguration transportConfiguration = new TransportConfiguration(
                NettyConnectorFactory.class.getName(), connectionParams);
        connectionFactory = HornetQJMSClient.createConnectionFactoryWithHA(JMSFactoryType.CF, transportConfiguration);
    }

    public Session connect(String username, String password) throws JMSException {
        Connection connection = connectionFactory.createConnection(username, password);
        connection.setClientID("test-client" + String.format("%1$d", System.currentTimeMillis()));
        connection.start();
        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }
}
