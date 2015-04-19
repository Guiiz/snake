package org.guiiz.data

import org.springframework.data.repository.CrudRepository


/**
 * Neo4j repository for user entity
 */
interface UserRepository extends CrudRepository<UserData, Long> {
	boolean findByUsernameIsNull(String username)
	List<UserData> findByUsername(String username)
	List<UserData> findByUsernameNot(String username)
}
