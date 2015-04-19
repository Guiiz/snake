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
public class SubscriptionCancelEvent extends Event {

	private SubscriptionCancelPayload payload

	public SubscriptionCancelEvent() {
		type = EventType.SUBSCRIPTION_CANCEL
	}
}
