package org.guiiz.data.appdirect.event.subscription

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

import org.guiiz.data.appdirect.event.Event
import org.guiiz.data.appdirect.event.EventType

/**
 * JAXB Bean for AppDirect event
 */
@XmlAccessorType( XmlAccessType.FIELD )
@XmlRootElement(name = "event")
public class SubscriptionOrderEvent extends Event {

    private SubscriptionOrderPayload payload

    public SubscriptionOrderEvent() {
        type = EventType.SUBSCRIPTION_ORDER
    }
}
