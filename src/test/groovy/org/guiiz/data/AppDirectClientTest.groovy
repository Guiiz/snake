package org.guiiz.data

import javax.inject.Inject

import oauth.signpost.OAuthConsumer
import oauth.signpost.basic.DefaultOAuthConsumer

import org.guiiz.client.AppDirectClient
import org.guiiz.config.SpringConfiguration
import org.guiiz.data.appdirect.event.EventType
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner


@RunWith(SpringJUnit4ClassRunner)
@ContextConfiguration(classes=[SpringConfiguration])
class AppDirectClientTest {

	@Inject AppDirectClient client

	// Modify Spring configuration in order to use local database to pass this test
	@Test
	//@Ignore
	void testOAuth() {
		//def event = client.getEventInfo('https://www.appdirect.com/AppDirect/rest/api/events/dummyOrder', EventType.SUBSCRIPTION_ORDER)
		def event = client.getEventInfo('https://www.appdirect.com/api/integration/v1/events/68c55104-1d3c-4cf6-8b4a-395df9927afe', EventType.SUBSCRIPTION_ORDER)
		//assert event.creator.email == 'test-email+creator@appdirect.com'
	}

	@Test
	void testOAuthBase() {
		//		OAuthConsumer consumer = new DefaultOAuthConsumer('Dummy', 'secret')
		OAuthConsumer consumer = new DefaultOAuthConsumer('myproduct-20834', 'mqsWnx2CPxaLlAXh')
		//URL url = new URL('https://www.appdirect.com/AppDirect/rest/api/events/dummyOrder')
		URL url = new URL('https://www.appdirect.com/api/integration/v1/events/68c55104-1d3c-4cf6-8b4a-395df9927afe')
		HttpURLConnection request = (HttpURLConnection) url.openConnection()
		consumer.sign(request)
		//request.connect()
		assert request.getResponseCode() < 400
	}
}
