package api.models;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {
            "id",
            "dni",
            "telephone",
            "email",
            "role"
        })
@EqualsAndHashCode
public class Persona {
    private int id;
    private String dni;
    private String name;
    private String surnames;
    private int telephone;
    private String email;
    private Role role;
}
