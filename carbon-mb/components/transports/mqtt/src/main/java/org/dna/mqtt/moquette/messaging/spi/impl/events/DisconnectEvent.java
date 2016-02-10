package org.dna.mqtt.moquette.messaging.spi.impl.events;

import org.dna.mqtt.moquette.server.ServerChannel;

public class DisconnectEvent extends MessagingEvent {
    
    ServerChannel session;
    
    public DisconnectEvent(ServerChannel session) {
        this.session = session;
    }

    public ServerChannel getSession() {
        return session;
    }
    
    
}