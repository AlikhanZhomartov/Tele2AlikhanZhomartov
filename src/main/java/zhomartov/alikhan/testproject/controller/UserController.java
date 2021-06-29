package zhomartov.alikhan.testproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import zhomartov.alikhan.testproject.dto.requestDto.user.UserCreateRequestDto;
import zhomartov.alikhan.testproject.dto.requestDto.user.UserUpdateRequestDto;
import zhomartov.alikhan.testproject.dto.responseDto.UserResponseDto;
import zhomartov.alikhan.testproject.exception.ExceptionHandling;
import zhomartov.alikhan.testproject.exception.ResponseErrorValidation;
import zhomartov.alikhan.testproject.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController extends ExceptionHandling {

    private final UserService userService;
    private final ResponseErrorValidation responseErrorValidation;

    @Autowired
    public UserController(UserService userService, ResponseErrorValidation responseErrorValidation) {
        this.userService = userService;
        this.responseErrorValidation = responseErrorValidation;
    }

    @GetMapping("/get-all")
    public ResponseEntity<HashMap<String, Object>> getAllUsers(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                               @RequestParam(name = "size", required = false, defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(userService.selectAllUsers(pageable), HttpStatus.OK);
    }

    @GetMapping("get-user-by-one/{user_id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable(name = "user_id") Long userId) {
        return new ResponseEntity<>(userService.selectUserById(userId), HttpStatus.OK);
    }

    @PostMapping("/create-user")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserCreateRequestDto userCreateRequestDto, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        userService.createUserService(userCreateRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("update-user/{user_id}")
    public ResponseEntity<HttpStatus> updateUserById(@PathVariable(name = "user_id") Long userId, @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        userService.updateUserService(userUpdateRequestDto, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("delete-user/{user_id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable(name = "user_id") Long userId) {
        userService.deleteUserService(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
