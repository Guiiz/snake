package org.guiiz.config

import oauth.signpost.OAuthConsumer
import oauth.signpost.basic.DefaultOAuthConsumer

import org.glassfish.jersey.client.HttpUrlConnectorProvider
import org.guiiz.client.AppDirectClient
import org.guiiz.data.Repositories
import org.neo4j.graphdb.GraphDatabaseService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.neo4j.config.EnableNeo4jRepositories
import org.springframework.data.neo4j.config.Neo4jConfiguration
import org.springframework.data.neo4j.rest.SpringCypherRestGraphDatabase


/**
 * Spring configuration
 */
@Configuration
@EnableNeo4jRepositories(basePackages = 'org.guiiz.data')
class SpringConfiguration extends Neo4jConfiguration {

	final static private CONSUMER_KEY = 'myproduct-20834'
	final static private SECRET = 'mqsWnx2CPxaLlAXh'

	//	final static private CONSUMER_KEY = 'Dummy'
	//	final static private SECRET = 'secret'

	SpringConfiguration() {
		setBasePackage('org.guiiz.data')
	}

	@Bean
	GraphDatabaseService graphDatabaseService() {
		//return new SpringCypherRestGraphDatabase('http://10.240.206.38:7474/db/data')
		return new SpringCypherRestGraphDatabase('http://104.197.48.159:7474/db/data')
		//return new GraphDatabaseFactory().newEmbeddedDatabase('accessingdataneo4j.db')
	}

	@Bean Repositories repositories() {
		new Repositories()
	}

	@Bean OAuthConsumer getOAuthConsumer() {
		new DefaultOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY)
	}

	@Bean HttpUrlConnectorProvider getOAuthConnectionHandler() {
		def connFac = new HttpUrlConnectorProvider.ConnectionFactory() {
					public HttpURLConnection getConnection(URL url) throws IOException {
						//println 'Use OAuth connection'
						def oAuthConsumer = new DefaultOAuthConsumer(CONSUMER_KEY, SECRET)
						HttpURLConnection request = (HttpURLConnection) url.openConnection()
						oAuthConsumer.sign(request)
						request
					}
				}
		new HttpUrlConnectorProvider().connectionFactory(connFac)
	}

	@Bean AppDirectClient getAppDirectClient() {
		new AppDirectClient()
	}
}
