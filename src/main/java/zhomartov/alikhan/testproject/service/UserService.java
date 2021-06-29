package zhomartov.alikhan.testproject.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import zhomartov.alikhan.testproject.dto.requestDto.user.UserCreateRequestDto;
import zhomartov.alikhan.testproject.dto.requestDto.user.UserUpdateRequestDto;
import zhomartov.alikhan.testproject.dto.responseDto.UserResponseDto;
import zhomartov.alikhan.testproject.exception.domain.UserNotFoundException;
import zhomartov.alikhan.testproject.exception.domain.UsernameExistException;
import zhomartov.alikhan.testproject.model.User;
import zhomartov.alikhan.testproject.repository.UserRepository;
import zhomartov.alikhan.testproject.util.facade.UserFacade;

import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public void createUserService(UserCreateRequestDto userCreateRequestDto) {
        userRepository.findByUsername(userCreateRequestDto.getUsername()).ifPresent(value -> {
            throw new UsernameExistException("Username " + value.getUsername() +
            " already exist");
        });

        User user = new User();
        user.setUsername(userCreateRequestDto.getUsername());
        user.setPassword(encoder.encode(userCreateRequestDto.getPassword()));
        user.setDateOfBirth(userCreateRequestDto.getDateOfBirth());
        user.setAdditionalInformation(userCreateRequestDto.getAdditionalInformation());
        user.setPhoneNumber(userCreateRequestDto.getPhoneNumber());
        userRepository.save(user);
    }

    public void updateUserService(UserUpdateRequestDto userUpdateRequestDto, Long user_id) {
        Optional<User> user = userRepository.findById(user_id);
        if (user.isEmpty()) throw new UserNotFoundException("User not found by id: " + user_id);

        if (Strings.isNotEmpty(userUpdateRequestDto.getUsername())) {
            userRepository.findByUsername(userUpdateRequestDto.getUsername()).ifPresent(value -> {
                if (!user.get().getUsername().equals(value.getUsername())) {
                    throw new UsernameExistException("Username " + value.getUsername() +
                            " already exist");
                }
            });
            user.get().setUsername(userUpdateRequestDto.getUsername());
        }
        if (Strings.isNotEmpty(userUpdateRequestDto.getPassword())) {
            user.get().setPassword(encoder.encode(userUpdateRequestDto.getPassword()));
        }
        if (!ObjectUtils.isEmpty(userUpdateRequestDto.getDateOfBirth())) {
            user.get().setDateOfBirth(userUpdateRequestDto.getDateOfBirth());
        }
        if (Strings.isNotEmpty(userUpdateRequestDto.getAdditionalInformation())) {
            user.get().setAdditionalInformation(userUpdateRequestDto.getAdditionalInformation());
        }
        if (Strings.isNotEmpty(userUpdateRequestDto.getPhoneNumber())) {
            user.get().setPhoneNumber(userUpdateRequestDto.getPhoneNumber());
        }
        userRepository.save(user.get());
    }

    public HashMap<String, Object> selectAllUsers(Pageable pageable) {
        return pageConvertToMap(userRepository.findAll(pageable));
    }

    private HashMap<String, Object> pageConvertToMap(Page<User> page) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("list", page.getContent().stream().map(value -> new UserFacade().userToUserResponseDto(value))
                .collect(Collectors.toList()));
        map.put("number_of_pages", page.getTotalPages());
        map.put("current_page", page.getNumber() + 1);
        return map;
    }

    public UserResponseDto selectUserById(Long user_id) {
        Optional<User> user = userRepository.findById(user_id);
        if (user.isEmpty()) throw new UserNotFoundException("User not found by id: " + user_id);
        return new UserFacade().userToUserResponseDto(user.get());
    }

    public void deleteUserService(Long user_id) {
        userRepository.deleteById(user_id);
    }
}
