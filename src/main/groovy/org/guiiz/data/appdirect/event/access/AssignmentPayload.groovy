package org.guiiz.data.appdirect.event.access

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType

import org.guiiz.data.appdirect.Account
import org.guiiz.data.appdirect.User

/**
 * JAXB Bean for AppDirect event
 */
@XmlAccessorType( XmlAccessType.FIELD )
public class AssignmentPayload {
    Account account
    User user
}
