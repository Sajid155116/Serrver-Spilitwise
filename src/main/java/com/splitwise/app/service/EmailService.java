package com.splitwise.app.service;

import com.splitwise.app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${server.port:8080}")
    private String serverPort;

    public void sendVerificationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("Verify Your Email - Splitwise App");
        
        String verificationUrl = "http://localhost:" + serverPort + "/api/auth/verify-email?token=" + user.getEmailVerificationToken();
        
        String emailBody = "Dear " + user.getName() + ",\n\n" +
                "Welcome to Splitwise App! Please click the link below to verify your email address:\n\n" +
                verificationUrl + "\n\n" +
                "If you didn't create an account with us, please ignore this email.\n\n" +
                "Best regards,\n" +
                "Splitwise Team";
        
        message.setText(emailBody);
        mailSender.send(message);
    }

    public void sendPasswordResetEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("Password Reset Request - Splitwise App");
        
        String resetUrl = "http://localhost:" + serverPort + "/api/auth/reset-password?token=" + user.getPasswordResetToken();
        
        String emailBody = "Dear " + user.getName() + ",\n\n" +
                "You have requested to reset your password. Please click the link below to reset your password:\n\n" +
                resetUrl + "\n\n" +
                "This link will expire in 1 hour. If you didn't request a password reset, please ignore this email.\n\n" +
                "Best regards,\n" +
                "Splitwise Team";
        
        message.setText(emailBody);
        mailSender.send(message);
    }

    public void sendWelcomeEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("Welcome to Splitwise App!");
        
        String emailBody = "Dear " + user.getName() + ",\n\n" +
                "Welcome to Splitwise App! Your email has been successfully verified.\n\n" +
                "You can now start splitting expenses with your friends and family.\n\n" +
                "Features you can enjoy:\n" +
                "- Create groups for different occasions\n" +
                "- Add expenses and split them equally or with custom amounts\n" +
                "- Track who owes what to whom\n" +
                "- Settle debts easily\n\n" +
                "Get started by creating your first group!\n\n" +
                "Best regards,\n" +
                "Splitwise Team";
        
        message.setText(emailBody);
        mailSender.send(message);
    }

    public void sendExpenseNotificationEmail(User user, String expenseName, String groupName, String amount) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("New Expense Added - " + groupName);
        
        String emailBody = "Dear " + user.getName() + ",\n\n" +
                "A new expense has been added to your group '" + groupName + "':\n\n" +
                "Expense: " + expenseName + "\n" +
                "Amount: $" + amount + "\n\n" +
                "Please log in to your account to view the details and confirm your share.\n\n" +
                "Best regards,\n" +
                "Splitwise Team";
        
        message.setText(emailBody);
        mailSender.send(message);
    }

    public void sendSettlementNotificationEmail(User user, String payerName, String amount) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("Payment Received - Splitwise App");
        
        String emailBody = "Dear " + user.getName() + ",\n\n" +
                payerName + " has marked a payment of $" + amount + " as settled.\n\n" +
                "Please log in to your account to view the updated balances.\n\n" +
                "Best regards,\n" +
                "Splitwise Team";
        
        message.setText(emailBody);
        mailSender.send(message);
    }
}