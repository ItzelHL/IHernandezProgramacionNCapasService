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
    
    public void correoVerificacion(String destino, String nombre, String username, String password, String token) throws MessagingException 
    {
//        String enlace = "http://localhost:8080/api/usuario/verify?token=" + token;
        String enlace = "http://localhost:8081/login";
        
        Context context = new Context();
        context.setVariables(Map.of("nombre", nombre, "username", username, "password", password, "enlace", enlace));
        
        String contenidoHtml = templateEngine.process("CorreoVerificacion.html", context);
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(destino);
        helper.setSubject("Verificación de cuenta");
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
    
    public void notificacionLogin(String destino, String ipAddress, String location) throws MessagingException 
    {   
        Context context = new Context();
        context.setVariables(Map.of("IPAddress", ipAddress, "location", location));
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String subject = "New Login Detected";

        helper.setTo(destino);
        helper.setSubject(subject);
//        helper.setText(htmlContent, true);  // Set to true to indicate the content is HTML

        mailSender.send(message);
    }
}