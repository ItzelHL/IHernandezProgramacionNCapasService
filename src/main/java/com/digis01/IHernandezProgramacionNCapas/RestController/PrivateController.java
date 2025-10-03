package com.digis01.IHernandezProgramacionNCapas.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class PrivateController 
{
    @GetMapping("publico")
    public String publicEndpoint()
    {
        return "perfil publico";
    }
    
    @GetMapping("private")
    public String privateEndpoint()
    {
        return "perfil privado";
    }
    
    @GetMapping("admin")
    public String adminEndpoint()
    {
        return "perfil admin";
    }
}
