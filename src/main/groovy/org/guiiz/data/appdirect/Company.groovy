package org.guiiz.data.appdirect

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

/**
 * JAXB Bean for AppDirect event
 */
@XmlRootElement
@XmlAccessorType( XmlAccessType.FIELD )
public class Company {
	private String uuid
	private String email
	private String name
	private String phoneNumber
	private String website
}
