package com.example.authuser.services;


import com.example.authuser.dto.MailBody;
import com.example.authuser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.UnifiedJedis;

import java.util.Objects;

@Service
public class ForgetPasswordService {
    private final UserRepository userRepository;
    private final UnifiedJedis jedis;
    private final EmailService emailService;

    private static final int OTP_EXPIRED_TIME = 15 * 60;

    @Autowired
    public ForgetPasswordService(UserRepository userRepository,
                                 StringRedisTemplate stringRedisTemplate, UnifiedJedis jedis,
                                 EmailService emailService) {
        this.userRepository = userRepository;
        this.jedis = jedis;
        this.emailService = emailService;
    }

    private String generateOTP(){
        int otp = (int) (Math.random() * 900000);
        return String.valueOf(otp);
    }

    public void sendOTP(String email) {
        String otp = generateOTP();

        String key = email;

        jedis.set(key, otp);
        jedis.expire(key, OTP_EXPIRED_TIME);

        MailBody mailBody = new MailBody(email, "Зміна пароля",
                otp);
        emailService.sendMail(mailBody);
    }

    public Boolean validateOTP(String email, String otp) {
        String sentOtp = jedis.get(email);

        if (Objects.equals(sentOtp, otp)) {
            jedis.del(email);
            return true;
        }
        else return false;
    }



}
