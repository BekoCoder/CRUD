package uz.pdp.website.dto.request;

import lombok.*;
import uz.pdp.website.entity.enums.Role;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {
    private String name;
    private String username;
    private String password;
    private List<Role> userRoles;
}
