package org.guiiz.rest

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.QueryParam

import org.guiiz.config.SpringContextHelper
import org.guiiz.data.UserData
import org.guiiz.data.appdirect.User
import org.guiiz.data.appdirect.event.EventResult
import org.guiiz.data.appdirect.event.EventType
import org.guiiz.data.appdirect.event.subscription.SubscriptionCancelEvent
import org.guiiz.data.appdirect.event.subscription.SubscriptionOrderEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory


/**
 * REST resources for AppDirect integration
 */
@Path('appdirect')
class AppDirectRestResources {

	Logger logger = LoggerFactory.getLogger(AppDirectRestResources)

	@Path('subscription/order')
	@GET
	EventResult subscriptionOrder(@QueryParam('url') String url, @QueryParam('token') String token) {
		EventResult result
		try {
			logger.info "Receive event from appdirect at ${url}"
			SubscriptionOrderEvent event = SpringContextHelper.getAppDirectClient().getEventInfo(url, EventType.SUBSCRIPTION_ORDER)
			logger.info "Receive new subscription event from AppDirect ${event.type}"
			User user = event.creator

			def res = SpringContextHelper.repositories.userRepository.findByUsername(user.email)
			def id
			def current
			if (!res) {
				logger.info "Create new user ${user.email}"
				def currentUser = new UserData()
				currentUser.username = user.email
				id = SpringContextHelper.repositories.userRepository.save(currentUser).id
			} else {
				id = res.first().id
			}

			// return result XML
			result = new EventResult()
			result.accountIdentifier = user.email
			result.message = 'Welcome to AppDirect!'
			result.success = true
		} catch (Exception e) {
			logger.error('Error', e)
			result = new EventResult()
			result.message = e.message
			result.errorCode = '500' 
			result.success = false
		}

		result
	}

	@Path('subscription/cancel')
	@GET
	EventResult subscriptionCancel(@QueryParam('url') String url, @QueryParam('token') String token) {
		EventResult result
		try {
			logger.info "Receive event from appdirect at ${url}"
			SubscriptionCancelEvent event = SpringContextHelper.getAppDirectClient().getEventInfo(url, EventType.SUBSCRIPTION_CANCEL)
			logger.info "Receive cancel subscription event from AppDirect"
			User user = event.creator

			def res = SpringContextHelper.repositories.userRepository.findByUsername(user.email)
			def id = -1
			if (res) {
				logger.info "Delete user ${user.email}"
				id = res.first().id
				SpringContextHelper.repositories.userRepository.delete(id)
			}

			// return result XML
			result = new EventResult()
			result.accountIdentifier = user.email
			result.message = 'See you soon on AppDirect!'
			result.success = true

		} catch (Exception e) {
			logger.error('Error', e)
			result = new EventResult()
			result.message = e.message
			result.errorCode = '500' 
			result.success = false
		}
		result
	}
}
