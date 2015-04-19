package org.guiiz.data.appdirect

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

/**
 * JAXB Bean for AppDirect event
 */
@XmlAccessorType( XmlAccessType.FIELD )
@XmlRootElement
public class User {
	private String email
	private String firstName
	private String lastName
	private String language
	private String openId
	private String uuid
}
