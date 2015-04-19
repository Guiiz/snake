package org.guiiz.data.appdirect

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

/**
 * JAXB Bean for AppDirect event
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {

    @XmlElement
    private String editionCode

    @XmlElement(name = "item")
    private List<OrderItem> orderItem

}
