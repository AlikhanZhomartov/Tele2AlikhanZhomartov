package zhomartov.alikhan.testproject.dto.requestDto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserCreateRequestDto {
    @NotBlank(message = "User's username can not be empty")
    private String username;
    @NotBlank(message = "User's password can not be empty")
    private String password;
    @NotNull(message = "User's dateOfBirth can not be empty")
    private LocalDate dateOfBirth;
    @NotBlank(message = "User's additional information can not be empty")
    private String additionalInformation;
    @NotBlank(message = "User's phone number can not be empty")
    private String phoneNumber;
}
