package org.guiiz.rest

import org.glassfish.jersey.server.ResourceConfig

public class Application extends javax.ws.rs.core.Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>()
        s.add(AppDirectRestResources)
		s.add(GameRestResources)
		s.add(LoginRestResources)
        s
    }
}

