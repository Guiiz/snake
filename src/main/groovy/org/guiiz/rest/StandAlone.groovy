package org.guiiz.rest

import org.glassfish.grizzly.http.server.CLStaticHttpHandler
import org.glassfish.grizzly.http.server.HttpServer
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.glassfish.jersey.server.ResourceConfig
import org.guiiz.config.SpringContextHelper
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Grizzly stand alone server
 */
class StandAlone {
	private static final URI BASE_URI = URI.create("http://0.0.0.0:8090/api/")
	private static boolean finish = false

	Logger LOG = LoggerFactory.getLogger(StandAlone)

	public static void main(String[] args) {
		SpringContextHelper.initDatabase()
		final ResourceConfig resourceConfig = new ResourceConfig(AppDirectRestResources,
				GameRestResources, LoginRestResources)

		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig)
		final handler = new CLStaticHttpHandler(StandAlone.classLoader)
		server.serverConfiguration.addHttpHandler(handler, '/')

		while (!finish) {
			Thread.sleep(1000)
		}
		server.shutdownNow()
	}

	public static void stop(){
		finish = true
	}
}
