package org.guiiz.rest

import groovy.json.JsonOutput

import javax.ws.rs.GET
import javax.ws.rs.NotFoundException
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.QueryParam

import org.guiiz.config.SpringContextHelper
import org.guiiz.data.GameData
import org.guiiz.data.UserData
import org.slf4j.Logger
import org.slf4j.LoggerFactory


/**
 * REST resouces for game actions
 */
@Path('snake')
class GameRestResources {

	Logger logger = LoggerFactory.getLogger(GameRestResources)

	/**
	 * Set new high score
	 * @param score score
	 * @param beaten beaten user if any
	 */
	@Path('newHigh')
	@POST
	void beat(@QueryParam('score') Long score, @QueryParam('beaten') String beaten) {
		def tx
		try {
			GameData game = new GameData()
			game.highScore = score
			tx = SpringContextHelper.repositories.startTransaction()
			UserData user = LoginRestResources.currentUser
			user.game = game
			SpringContextHelper.repositories.userRepository.save(user)
			if (beaten) {
				// Reset beaten user high score
				def other = SpringContextHelper.repositories.userRepository.findByUsername(beaten)
				if (other) {
					other.first().game = null
				}
			}
			tx.success()
		} catch (Exception e) {
			logger.error('Error saving user data', e)
		} finally {
			tx.close()
		}
	}

	/**
	 * Get high score
	 * @return high score
	 */
	@Path('highscore')
	@GET
	Long highscore() {
		def tx
		def res = null
		try{
			tx = SpringContextHelper.repositories.startTransaction()
			def users = LoginRestResources.currentUser
			res = users?.game?.highScore
			tx.success()
		} catch (Exception e) {
			logger.error('Error getting high score', e)
		} finally {
			tx.close()
		}
		if (!res) {
			throw new NotFoundException()
		}
		res
	}

	/**
	 * List of ongoing high scores
	 * @return list of ongoing highscore as JSON
	 */
	@Path('games')
	@GET
	String games() {
		def tx
		def res = []
		try{
			tx = SpringContextHelper.repositories.startTransaction()
			def users = SpringContextHelper.repositories.userRepository.findByUsernameNot(LoginRestResources.currentUser.username)
			if (users) {
				users.each {
					if (it.game) {
						res.add(['user':it.username, 'score': it.game.highScore])
					}
				}
			}
			tx.success()
		} catch (Exception e) {
			logger.error('Error getting high score', e)
		} finally {
			tx.close()
		}
		if (res.empty) {
			throw new NotFoundException()
		}
		new JsonOutput().toJson(res)
	}
}
