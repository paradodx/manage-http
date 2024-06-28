package org.example.managehttp;


import io.jsonwebtoken.Claims;
import org.example.managehttp.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class JwtTest {

    @Test
    public void testGenerate(){
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", "Aa");
        claims.put("password", 123);

        String jwt = JwtUtils.generateJwt(claims);

        System.out.println("Jwt: " + jwt);
    }

    @Test
    public void testParse(){
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6MTIzLCJ1c2VybmFtZSI6IkFhIiwiZXhwIjozNDM4Nzg5MTE3fQ.zGWK8pr68UQgcHq3tY5FTcwVad4ZxudfYqEWzqhXM2g";
        Claims claims = JwtUtils.parseJwt(jwt);
        System.out.println(claims);
    }
}
