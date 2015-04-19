package org.guiiz.data

import org.neo4j.graphdb.Direction
import org.springframework.data.neo4j.annotation.Fetch
import org.springframework.data.neo4j.annotation.GraphId
import org.springframework.data.neo4j.annotation.NodeEntity
import org.springframework.data.neo4j.annotation.RelatedTo


/**
 * Neo4j entity for game
 */
@NodeEntity
class GameData {

	@GraphId
	Long id

	UserData currentWinner

	Long highScore
}
