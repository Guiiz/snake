package org.guiiz.data.appdirect.event

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType

import org.guiiz.data.appdirect.Marketplace
import org.guiiz.data.appdirect.User


/**
 * Created from
 * https://github.com/ericheiker/AppDirect_Integration_Challenge/tree/master/src/main/java/com/eheiker/appdirect/domain/appdirect
 * JAXB Bean for AppDirect event
 * TODO use @MappedSupperClass
 */
@XmlAccessorType( XmlAccessType.FIELD )
public abstract class Event {
	protected EventType type
	private Marketplace marketplace
	private User creator


	/**
	 * @return the type
	 */
	public EventType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(EventType type) {
		this.type = type;
	}

	/**
	 * @return the marketplace
	 */
	public Marketplace getMarketplace() {
		return marketplace;
	}

	/**
	 * @param marketplace the marketplace to set
	 */
	public void setMarketplace(Marketplace marketplace) {
		this.marketplace = marketplace;
	}

	/**
	 * @return the creator
	 */
	public User getCreator() {
		return creator;
	}

	/**
	 * @param creator the creator to set
	 */
	public void setCreator(User creator) {
		this.creator = creator;
	}
}
