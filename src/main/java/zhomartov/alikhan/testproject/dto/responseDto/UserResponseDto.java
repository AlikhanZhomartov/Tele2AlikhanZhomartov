package zhomartov.alikhan.testproject.dto.responseDto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private LocalDate dateOfBirth;
    private String additionalInformation;
    private String phoneNumber;
    private LocalDate createdDate;
}
