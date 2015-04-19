package org.guiiz.data

import javax.inject.Inject

import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.graphdb.Transaction


/**
 * Helper for repository access and database actions  
 */
class Repositories {

	@Inject
	private UserRepository userRepository
	
	@Inject
	private GraphDatabaseService database
	
	public Transaction startTransaction() {
		database.beginTx()
	}
}
