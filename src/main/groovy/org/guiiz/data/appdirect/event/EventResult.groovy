package org.guiiz.data.appdirect.event

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

/**
 * JAXB Bean for AppDirect event
 */
@XmlRootElement(name = "result")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class EventResult {

    private boolean success
    private String message
    private String errorCode
    private String accountIdentifier
}