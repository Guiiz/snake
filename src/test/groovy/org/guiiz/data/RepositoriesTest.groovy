package org.guiiz.data

import javax.inject.Inject

import org.guiiz.config.SpringConfiguration
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner)
@ContextConfiguration(classes=[SpringConfiguration])
class RepositoriesTest {

	@Inject
	Repositories repos

	@Test
	void testGetGameByUser() {
		UserData user = new UserData()
		user.username = 'user'
		GameData game = new GameData()
		game.highScore = 120L
		user.game = game
		repos.userRepository.save(user)
		def res = repos.userRepository.findByUsername(user.username)
		assert res.first().game.highScore == game.highScore
	}

	@Test
	void testGetUser() {
		def users = repos.userRepository.findByUsername('user2')
		assert !users
	}
}
