package org.guiiz.data.appdirect.event.access

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
public class UserUnassignedEvent extends Event {

	private AssignmentPayload payload

	public UserUnassignedEvent() {
		type = EventType.USER_UNASSIGNED
	}
}
