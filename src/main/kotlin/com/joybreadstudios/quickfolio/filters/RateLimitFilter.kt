package com.joybreadstudios.quickfolio.filters

import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap

@Component
@Order(1)
class RateLimitFilter : OncePerRequestFilter(), Ordered {
    override fun getOrder(): Int = 1

    private val buckets = ConcurrentHashMap<String, Bucket>()

    private fun resolveBucket(ip: String): Bucket {
        return buckets.computeIfAbsent(ip) {
            Bucket.builder()
                .addLimit(Bandwidth.builder().capacity(100).refillGreedy(10,Duration.ofMinutes(10)).build())
                .build()
        }
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val ip = request.remoteAddr
        val bucket = resolveBucket(ip)

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response)
        } else {
            response.status = 429
            response.writer.write("Too Many Requests - Rate limit exceeded")
        }
    }
}
