package com.digis01.IHernandezProgramacionNCapas.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Map;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class EmailService 
{
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    
    public EmailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine)
    {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    } 
    
    public void cuentaCreada(String destino, String nombre, String username, String password, String loginUrl) throws MessagingException
    {   
        Context context = new Context();
        context.setVariables(Map.of("nombre", nombre, "username", username, "password", password, "loginUrl", loginUrl));
        String contenidoHtml = templateEngine.process("CuentaCreada.html", context);
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        helper.setTo(destino);
        helper.setSubject("Se creó tu cuenta.");
        helper.setText(contenidoHtml, true);
        
        mailSender.send(message);
    }
    
    public void correoVerificacion(String destino, String nombre, String verifyUrl) throws MessagingException 
    {   
        Context context = new Context();
        context.setVariables(Map.of("nombre", nombre, "enlace", verifyUrl));
        
        String contenidoHtml = templateEngine.process("CorreoVerificacion.html", context);
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(destino);
        helper.setSubject("Verifica tu cuenta");
        helper.setText(contenidoHtml, true);

        mailSender.send(message);
    }
    
    public void recuperacionContraseña(String destino, String nombre, String token) throws MessagingException
    {
        String enlace = "http://localhost:8081/login/resetpassword?token=" + token;
        
        Context context = new Context();
        context.setVariables(Map.of("nombre", nombre, "enlace", enlace));
        
        String contenidoHtml = templateEngine.process("RecuperacionContraseña.html", context);
        
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");
        helper.setTo(destino);
        helper.setSubject("Recuperación de contraseña");
        helper.setText(contenidoHtml, true);
        
        mailSender.send(mensaje);
    }
}