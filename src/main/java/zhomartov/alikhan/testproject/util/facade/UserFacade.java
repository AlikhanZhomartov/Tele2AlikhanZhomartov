package zhomartov.alikhan.testproject.util.facade;

import zhomartov.alikhan.testproject.dto.responseDto.UserResponseDto;
import zhomartov.alikhan.testproject.model.User;

public class UserFacade {

    public UserResponseDto userToUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setDateOfBirth(user.getDateOfBirth());
        userResponseDto.setAdditionalInformation(user.getAdditionalInformation());
        userResponseDto.setPhoneNumber(user.getPhoneNumber());
        userResponseDto.setCreatedDate(user.getCreatedDate());
        return userResponseDto;
    }
}
