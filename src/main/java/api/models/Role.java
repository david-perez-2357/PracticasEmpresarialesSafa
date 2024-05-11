package api.models;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Role {
    private int id;
    private String name;

    @Override
    public String toString() {
        // The first letter of the role is capitalized
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
