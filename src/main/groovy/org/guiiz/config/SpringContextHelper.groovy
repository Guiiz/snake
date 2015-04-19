package org.guiiz.config

import org.guiiz.client.AppDirectClient
import org.guiiz.data.Repositories
import org.neo4j.graphdb.GraphDatabaseService
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext


/**
 * Spring helper to access context
 * TODO remove and replace by Spring ContextLoaderListener
 */
class SpringContextHelper {

	static Repositories repositories
	static ApplicationContext ctx

	static {
		ctx = new AnnotationConfigApplicationContext(SpringConfiguration)
		repositories = ctx.getBean(Repositories)
	}

	static Repositories getRepositories() {
		repositories
	}

	static initDatabase() {
		ctx.getBean(GraphDatabaseService)
	}
	
	static AppDirectClient getAppDirectClient() {
		ctx.getBean(AppDirectClient)
	}
}
