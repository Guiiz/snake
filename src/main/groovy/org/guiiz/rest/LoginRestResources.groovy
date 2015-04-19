package org.guiiz.rest

import javax.servlet.http.HttpServletRequest
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Context
import javax.ws.rs.core.NoContentException
import javax.ws.rs.core.Response

import org.guiiz.config.SpringContextHelper
import org.guiiz.data.UserData
import org.openid4java.OpenIDException
import org.openid4java.consumer.ConsumerManager
import org.openid4java.consumer.VerificationResult
import org.openid4java.discovery.DiscoveryInformation
import org.openid4java.discovery.Identifier
import org.openid4java.message.AuthRequest
import org.openid4java.message.AuthSuccess
import org.openid4java.message.ParameterList
import org.openid4java.message.ax.AxMessage
import org.openid4java.message.ax.FetchRequest
import org.openid4java.message.ax.FetchResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory



/**
 * REST resource to handle login
 * 
 * https://crisdev.wordpress.com/2011/03/23/openid4java-login-example/
 */
@Path('login')
class LoginRestResources {

	Logger logger = LoggerFactory.getLogger(LoginRestResources)

	@Context
	private HttpServletRequest request

	static private ConsumerManager manager = new ConsumerManager()

	// TODO Save user in session to allow multiple session
	static UserData currentUser

	@Path('{who}')
	@GET
	Response login(@PathParam('who') String who, @QueryParam('url') String url) {
		def ret = Response.seeOther(new URI('../html/snake.html')).build()
		if (url) {
			try {
				// configure the return_to URL where your application will receive
				// the authentication responses from the OpenID provider
				String returnToUrl = 'http://fluted-depth-91400.appspot.com/api/verify'

				// perform discovery on the user-supplied identifier
				List discoveries = manager.discover(who)

				// attempt to associate with the OpenID provider
				// and retrieve one service endpoint for authentication
				DiscoveryInformation discovered = manager.associate(discoveries)

				// store the discovery information in the user's session
				req.getSession().setAttribute("openid-disc", discovered)

				// obtain a AuthRequest message to be sent to the OpenID provider
				AuthRequest authReq = manager.authenticate(discovered, returnToUrl)
				FetchRequest fetch = FetchRequest.createFetchRequest()

				// attach the extension to the authentication request
				authReq.addExtension(fetch)

				ret = Response.seeOther(new URI(url)).build()

			} catch (OpenIDException e) {
				logger.error('Error', e)
			}

		} else {

			def res = SpringContextHelper.repositories.userRepository.findByUsername(who)
			if (res) {
				currentUser = res.first()
			} else {
				currentUser = new UserData()
				currentUser.username = who
				SpringContextHelper.repositories.userRepository.save(currentUser)
			}
		}
		ret
	}

	@Path('verify')
	@POST
	Response verify() {
		def ret = Response.seeOther(new URI('html/login.html')).build()
		try {
			// extract the parameters from the authentication response
			// (which comes in as a HTTP request from the OpenID provider)
			ParameterList response = new ParameterList(req.getParameterMap())

			// retrieve the previously stored discovery information
			DiscoveryInformation discovered = (DiscoveryInformation) req.getSession().getAttribute("openid-disc")

			// extract the receiving URL from the HTTP request
			StringBuffer receivingURL = req.getRequestURL()
			String queryString = req.getQueryString()
			if (queryString != null && queryString.length() > 0) {
				receivingURL.append("?").append(req.getQueryString())
			}

			// verify the response; ConsumerManager needs to be the same
			// (static) instance used to place the authentication request
			VerificationResult verification = manager.verify(receivingURL.toString(), response, discovered)

			// examine the verification result and extract the verified
			// identifier
			Identifier verified = verification.getVerifiedId()
			if (verified != null) {
				AuthSuccess authSuccess = (AuthSuccess) verification.getAuthResponse()

				if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
					FetchResponse fetchResp = (FetchResponse) authSuccess.getExtension(AxMessage.OPENID_NS_AX)
					List emails = fetchResp.getAttributeValues("email")
					String email = (String) emails.get(0)
					logger.info("OpenIdlogin done with email: " + email)
				}

				def res = SpringContextHelper.repositories.userRepository.findByUsername(verified.getIdentifier())
				if (res) {
					currentUser = res.first()
				} else {
					currentUser = new UserData()
					currentUser.username = who
					SpringContextHelper.repositories.userRepository.save(currentUser)
				}
				ret =  Response.seeOther(new URI('html/snake.html')).build()
			}
		} catch (OpenIDException e) {
			logger.error('Error', e)
		}
		ret
	}

	@Path('logout')
	@POST
	void logout() {
		// TODO open id logout
		currentUser = null
	}

	@Path('who')
	@GET
	String who() {
		if (!currentUser) {
			throw new NoContentException()
		}
		currentUser.username
	}
}
