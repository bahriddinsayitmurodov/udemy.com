package com.udemy.user;

import com.udemy.common.service.GenericCrudService;
import com.udemy.sms.dto.SmsSendDTO;
import com.udemy.sms.service.SmsServiceImp;
import com.udemy.user.dto.UserCreateDto;
import com.udemy.user.dto.UserResponseDto;
import com.udemy.user.dto.UserUpdateDto;
import com.udemy.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
@Getter
@RequiredArgsConstructor
public class UserService extends GenericCrudService<User, UUID, UserCreateDto, UserResponseDto, UserUpdateDto>
                        implements UserDetailsService {

    private final UserRepository repository;
    private final UserModelMapper mapper;
    private final Class<User> entityClass = User.class;
    private final PasswordEncoder passwordEncoder;
    private final SmsServiceImp smsService;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        return repository.findByPhoneNumber(phone)
                .orElseThrow(() -> new BadCredentialsException("bad credentials"));
    }



    @Override
    public User save(UserCreateDto createDto) {
        User user = mapper.toEntity(createDto);
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        return user;
    }

    public String phoneNumber(UUID id){
        User user = repository.findUserById(id)
                .orElseThrow(() -> new BadCredentialsException("bad credentials"));

        return user.getPhoneNumber();
    }
    public String sendVerificationCode(UUID id) {
        User user = repository.findUserById(id)
                .orElseThrow(() -> new BadCredentialsException("bad credentials"));

        String phoneNumber = user.getPhoneNumber();

        int verificationCode = generateRandomSixDigitNumber();

        user.setSmsCode(verificationCode);
        repository.save(user);
        return smsService.sendSms(new SmsSendDTO(phoneNumber,"Don't share this code with anyone else.\n Verification code: "+verificationCode));
    }

    public UserResponseDto checkVerificationCode(UUID id, Integer verificationCode) {
        User user = repository.findUserById(id)
                .orElseThrow(() -> new BadCredentialsException("bad credentials"));

        if (!user.getSmsCode().equals(verificationCode)){
            return mapper.toResponseDto(user);
        }
        user.setPhoneNumberVerified(true);
        repository.save(user);
        return mapper.toResponseDto(user);
    }





    private int generateRandomSixDigitNumber() {
        Random random = new Random();
        return random.nextInt(900000) + 100000;
    }
}
