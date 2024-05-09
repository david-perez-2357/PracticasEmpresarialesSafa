package api.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
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
public class Person {
    private int id;
    private String dni;
    private String name;
    private String surnames;
    private int telephone;
    private String email;
    private Role role;
}
