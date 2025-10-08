package com.digis01.IHernandezProgramacionNCapas.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil 
{
    private final Key key = Keys.hmacShaKeyFor("f9Jr4L1x!aP$6mQz7VkB#9cD2hE*3nTqU@8yZrW%5oXj&1bH+KfG^0sLpMvNtY".getBytes());
    
//    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    
    private final Map<String, Integer> usageStore = new ConcurrentHashMap<>();
    
    public String generateToken(String username, String role)
    {
        String jti = UUID.randomUUID().toString();
        usageStore.put(jti, 0);
        
        return Jwts.builder().setSubject(username)
                                          .claim("role", role)
                                          .setId(jti)
                                          .setIssuedAt(new Date())
                                          .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                                          .signWith(key)
                                          .compact();
    }
    
    public Jws<Claims> validateToken(String token)
    {
        return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
    }
    
    public boolean checkTokenUsage(Jws<Claims> claims)
    {
        String jti = claims.getBody().getId();
        
        int current = usageStore.getOrDefault(jti, 0);
        if (current > 10) 
        {
            return false;
        }
        
        usageStore.put(jti, current + 1);
        return true;
    }
}