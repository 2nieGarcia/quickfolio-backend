@Component
class JwtTokenProvider(
    @Value("\${jwt.secret}") private val jwtSecret: String,
    @Value("\${jwt.expiration}") private val jwtExpirationMs: Long
) {
    fun generateToken(authentication: Authentication): String {
        val userPrincipal = authentication.principal as UserDetails
        val now = Date()
        val expiryDate = Date(now.time + jwtExpirationMs)

        return Jwts.builder()
            .setSubject(userPrincipal.username)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(Keys.hmacShaKeyFor(jwtSecret.toByteArray()), SignatureAlgorithm.HS512)
            .compact()
    }

    fun getUsernameFromToken(token: String): String =
        Jwts.parserBuilder().setSigningKey(jwtSecret.toByteArray()).build()
            .parseClaimsJws(token).body.subject

    fun validateToken(token: String): Boolean = try {
        Jwts.parserBuilder().setSigningKey(jwtSecret.toByteArray()).build().parseClaimsJws(token)
        true
    } catch (ex: Exception) {
        false
    }
}