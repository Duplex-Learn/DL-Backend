package com.duplexlearn.service;

import com.duplexlearn.dao.PUserDao;
import com.duplexlearn.dao.UserDao;
import com.duplexlearn.exception.IllegalPUserException;
import com.duplexlearn.exception.UserAlreadyExistsException;
import com.duplexlearn.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
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
    private UserDao userDao;
    private PUserDao pUserDao;
    private JavaMailSender javaMailSender;
    private TemplateEngine templateEngine;
    private PasswordEncoder passwordEncoder;

    private String mailFrom;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Autowired
    public void setpUserDao(PUserDao pUserDao) {
        this.pUserDao = pUserDao;
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
    public void preRegister(String email) {
        if (userDao.findByEmail(email).isPresent())
            throw new UserAlreadyExistsException(email);

        String uuid = pUserDao.createPUser(email);

        try {
            sendActivationEmail(email, uuid);
        } catch (MessagingException e) {
            throw new RuntimeException("Mail not sent", e);
        }
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
        String htmlText = templateEngine.process("puser.html", context);
        mailMessage.setContent(htmlText, MimeTypeUtils.TEXT_HTML_VALUE);

        javaMailSender.send(mailMessage);
    }

    @Override
    public User register(String email, String password, String uuid) {
        String uuidIn = pUserDao.getPUserUUIDByEmail(email).orElseThrow(() -> new IllegalPUserException(email));
        if (uuidIn == null || !uuidIn.equals(uuid)) throw new IllegalPUserException(email);

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        return userDao.save(user);
    }
}
