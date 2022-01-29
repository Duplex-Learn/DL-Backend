package com.duplexlearn.service;

import com.duplexlearn.dao.PUserDAO;
import com.duplexlearn.dao.UserDAO;
import com.duplexlearn.exception.IllegalPUserException;
import com.duplexlearn.exception.UserAlreadyExistsException;
import com.duplexlearn.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


/**
 * Default implementation of user services.
 * <p>
 * Pre register and store puser through redis.
 * Send email to notify users.
 *
 * @author LoveLonelyTime
 */
@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;
    private PUserDAO pUserDAO;
    private JavaMailSender javaMailSender;
    private TemplateEngine templateEngine;
    private PasswordEncoder passwordEncoder;

    private String mailFrom;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Autowired
    public void setPUserDAO(PUserDAO pUserDAO) {
        this.pUserDAO = pUserDAO;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Value("${mail.from}")
    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    @Autowired
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public PreRegisterDTO preRegister(PreRegisterDTO preRegisterDTO) {
        if (userDAO.findByEmail(preRegisterDTO.getEmail()).isPresent())
            throw new UserAlreadyExistsException(preRegisterDTO.getEmail());

        PUserPO pUserPO = new PUserPO();
        pUserPO.setEmail(preRegisterDTO.getEmail());

        pUserPO = pUserDAO.save(pUserPO);

//        try {
//            sendActivationEmail(pUserPO.getEmail(), pUserPO.getUuid());
//        } catch (MessagingException e) {
//            throw new RuntimeException("Mail not sent", e);
//        }

        return preRegisterDTO;
    }

    /**
     * Send activation email to notify users.
     *
     * @param email email
     * @param uuid  uuid
     * @throws MessagingException If the message fails to be sent.
     */
    private void sendActivationEmail(String email, String uuid) throws MessagingException {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);

        helper.setFrom(mailFrom);
        helper.setTo(email);
        helper.setSubject("Duplex Learn");

        // Generate HTML by puser.html
        Context context = new Context();
        context.setVariable("uuid", uuid);
        context.setVariable("email", email);
        String htmlText = templateEngine.process("puser.html", context);
        helper.setText(htmlText,true);

        javaMailSender.send(mailMessage);
    }

    @Override
    public UserDTO register(PUserDTO pUserDTO) {
        PUserPO pUserPO = pUserDAO.findByEmail(pUserDTO.getEmail()).orElseThrow(() -> new IllegalPUserException(pUserDTO.getEmail()));
        if (!pUserPO.getUuid().equals(pUserDTO.getUuid())) throw new IllegalPUserException(pUserDTO.getEmail());

        UserPO userPO = new UserPO();
        userPO.setEmail(pUserDTO.getEmail());
        userPO.setPassword(passwordEncoder.encode(pUserDTO.getPassword()));
        userPO = userDAO.save(userPO);

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(userPO.getEmail());
        userDTO.setId(userPO.getId());

        return userDTO;
    }
}
