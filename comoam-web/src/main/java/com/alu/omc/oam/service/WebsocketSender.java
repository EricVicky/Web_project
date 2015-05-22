package com.alu.omc.oam.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.stereotype.Service;

@Service
public class WebsocketSender implements ApplicationListener<BrokerAvailabilityEvent> {
    private static Logger log = LoggerFactory.getLogger(WebsocketSender.class);

    private SimpMessagingTemplate template;
    
    @Autowired
    public WebsocketSender(SimpMessagingTemplate template) {
        this.template = template;
    }
    
    public void send(String topic, Object payload) {
        if (log.isDebugEnabled()) {
            log.debug("Send to topic " + topic + " with payload of " + payload.toString());
        }
        
        template.convertAndSend(topic, payload);
    }

    @Override
    public void onApplicationEvent(BrokerAvailabilityEvent event) {
        log.info("Broker is ready for websocket sender!");
        template.convertAndSend("/topic/event", "This is a test....");
    }
}