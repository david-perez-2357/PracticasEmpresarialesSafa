package api.models;

import lombok.*;

import java.io.Serial;
import java.util.Collection;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Person implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    private String dni;
    private String name;
    private String surnames;
    private int telephone;
    private String email;
    private Role role;

    public String getFullName() {
        return name + " " + surnames;
    }

    @Override
    public String toString() {
        return getFullName();
    }
}
