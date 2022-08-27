package org.datepollsystems.dfkcommon.auth

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

abstract class AAuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val authDetailsService: UserDetailsService,
    private val jwtUtils: AJwtService
) : BasicAuthenticationFilter(authenticationManager) {

    @Throws(ServletException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader("Authorization")

        // If no auth token is available we do not assume the request to be authenticated
        if (header == null || !header.startsWith("Bearer")) {
            filterChain.doFilter(request, response)
            return
        }

        try {
            val bearerToken = header.replace("Bearer ", "")
            // Decode bearer token and access entity id
            // Possible throws ExpiredJwtException
            val entityId = jwtUtils.getAuthTokenEntityId(bearerToken)

            // Load entity details with filled in username (id), password (empty) and authorities (empty)
            val entityAuthDetails = authDetailsService.loadUserByUsername(entityId)

            // Set authentication in java spring security; Later accessible via variable injection in controllers.
            SecurityContextHolder.getContext().authentication =
                UsernamePasswordAuthenticationToken(entityId, entityAuthDetails.password, entityAuthDetails.authorities)

            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            response.setHeader("WWW-Authenticate", "Bearer")
            response.sendError(401)
        }
    }
}