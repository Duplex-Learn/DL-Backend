package com.duplexlearn.service.impl;

import com.duplexlearn.dao.PUserDAO;
import com.duplexlearn.dao.UserDAO;
import com.duplexlearn.exception.IllegalPUserException;
import com.duplexlearn.exception.UserAlreadyExistsException;
import com.duplexlearn.model.dto.PUserDTO;
import com.duplexlearn.model.dto.PreRegisterDTO;
import com.duplexlearn.model.dto.UserDTO;
import com.duplexlearn.model.po.PUserPO;
import com.duplexlearn.model.po.UserPO;
import com.duplexlearn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


/**
 * 用户服务的默认实现
 *
 * 使用发送邮件的方式通知用户 UUID
 *
 * @author LoveLonelyTime
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final PUserDAO pUserDAO;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, PUserDAO pUserDAO, JavaMailSender javaMailSender, TemplateEngine templateEngine, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.pUserDAO = pUserDAO;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.passwordEncoder = passwordEncoder;
    }

    @Value("${mail.from}")
    private String mailFrom;


    @Override
    public void preRegister(PreRegisterDTO preRegisterDTO) {
        // 如果用户已经存在，那么抛出用户已存在异常
        if (userDAO.findByEmail(preRegisterDTO.getEmail()).isPresent())
            throw new UserAlreadyExistsException(preRegisterDTO.getEmail());

        // 创建预注册用户的 PO 对象
        PUserPO pUserPO = new PUserPO();
        pUserPO.setEmail(preRegisterDTO.getEmail());

        // 保存预注册用户
        pUserPO = pUserDAO.save(pUserPO);

        // 发送邮件通知用户 UUID
        try {
            sendActivationEmail(pUserPO.getEmail(), pUserPO.getUuid());
        } catch (MessagingException e) {
            throw new RuntimeException("Mail not sent", e);
        }

    }

    /**
     * 发送激活邮件
     *
     * @param email email 目标邮箱地址
     * @param uuid uuid 要通知的 UUID
     * @throws MessagingException 如果邮件未能成功发送
     */
    private void sendActivationEmail(String email, String uuid) throws MessagingException {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);

        // 设置好来源和目的站、主题等信息
        helper.setFrom(mailFrom);
        helper.setTo(email);
        helper.setSubject("Duplex Learn");

        // 生成 HTML 文本
        Context context = new Context();
        context.setVariable("uuid", uuid);
        context.setVariable("email", email);
        String htmlText = templateEngine.process("puser.html", context);
        helper.setText(htmlText,true);

        // 发送
        javaMailSender.send(mailMessage);
    }

    /**
     * 从 PO 到 DTO 的映射
     * @param userPO PO 对象
     * @return DTO 对象
     */
    private UserDTO createUserDTOFromUserPO(UserPO userPO){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(userPO.getEmail());
        userDTO.setId(userPO.getId());
        userDTO.setNickname(userPO.getNickname());
        userDTO.setAge(userPO.getAge());
        userDTO.setGender(userDTO.getGender());
        userDTO.setHomeUrl(userPO.getHomeUrl());
        userDTO.setPosition(userDTO.getPosition());
        userDTO.setOrganization(userDTO.getOrganization());
        return userDTO;
    }

    @Override
    public UserDTO register(PUserDTO pUserDTO) {
        // 验证预注册用户的合法性
        PUserPO pUserPO = pUserDAO.findByEmail(pUserDTO.getEmail()).orElseThrow(() -> new IllegalPUserException(pUserDTO.getEmail()));
        if (!pUserPO.getUuid().equals(pUserDTO.getUuid())) throw new IllegalPUserException(pUserDTO.getEmail());

        // 创建用户的 PO 对象
        UserPO userPO = new UserPO();
        userPO.setEmail(pUserDTO.getEmail());
        userPO.setPassword(passwordEncoder.encode(pUserDTO.getPassword()));
        // 新用户的昵称默认为邮箱
        userPO.setNickname(pUserDTO.getEmail());
        // 其他信息默认为空，保存
        userPO = userDAO.save(userPO);

        // 删除预注册的 PO 对象
        pUserDAO.delete(pUserPO);

        // 创建用户的 DTO 对象
        return createUserDTOFromUserPO(userPO);
    }

    @Override
    public UserDTO getCurrentUser() {
        // 获取已鉴权的用户
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserPO userPO = userDAO.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));
        return createUserDTOFromUserPO(userPO);
    }
}
