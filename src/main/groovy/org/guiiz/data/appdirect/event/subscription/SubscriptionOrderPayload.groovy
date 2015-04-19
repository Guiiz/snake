package org.guiiz.data.appdirect.event.subscription

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

import org.guiiz.data.appdirect.Company
import org.guiiz.data.appdirect.Order

/**
 * JAXB Bean for AppDirect event
 */
@XmlAccessorType( XmlAccessType.FIELD )
@XmlRootElement(name = "payload")
public class SubscriptionOrderPayload {
	private Company company
	private Order order
}
