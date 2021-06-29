package zhomartov.alikhan.testproject.dto.requestDto.user;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateRequestDto {
    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private String additionalInformation;
    private String phoneNumber;
}
