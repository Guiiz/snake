package org.guiiz.client

import javax.inject.Inject
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.MediaType

import oauth.signpost.OAuthConsumer

import org.glassfish.jersey.client.ClientConfig
import org.glassfish.jersey.client.HttpUrlConnectorProvider
import org.guiiz.data.appdirect.event.Event
import org.guiiz.data.appdirect.event.EventType
import org.guiiz.data.appdirect.event.access.UserAssignedEvent
import org.guiiz.data.appdirect.event.access.UserUnassignedEvent
import org.guiiz.data.appdirect.event.subscription.SubscriptionCancelEvent
import org.guiiz.data.appdirect.event.subscription.SubscriptionOrderEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory


/**
 * OAuth client for AppDirect integration
 */
class AppDirectClient {

	Logger logger = LoggerFactory.getLogger(AppDirectClient)

	@Inject
	OAuthConsumer oAuthConsumer

	//	@Inject
	//	Feature oAuthFeature

	@Inject
	HttpUrlConnectorProvider oAuthConnHanlder

	// Map with event classes by type
	// TODO see if possible to remove with @MappedSuperclass
	Map<EventType, Class<? extends Event>> eventClasses = [(EventType.SUBSCRIPTION_CANCEL): SubscriptionCancelEvent,
		(EventType.SUBSCRIPTION_ORDER): SubscriptionOrderEvent,
		(EventType.USER_ASSIGNED): UserAssignedEvent,
		(EventType.USER_UNASSIGNED): UserUnassignedEvent]

	/**
	 * Get Event instance from url
	 * @param url url received from AppDirect
	 * @param type event type
	 * @return Event instance
	 */
	Event getEventInfo(String url, EventType type) {
		//		HttpURLConnection request = (HttpURLConnection) new URL(url).openConnection()
		//		oAuthConsumer.sign(request)
		//		request.connect()
		ClientConfig config = new ClientConfig()
		config.connectorProvider(oAuthConnHanlder)
		Client client = ClientBuilder.newClient(config)
		//client.register(oAuthConnHanlder)
		WebTarget webTarget = client.target(url)
		webTarget.request(MediaType.APPLICATION_XML).get(eventClasses.get(type))
	}

}
