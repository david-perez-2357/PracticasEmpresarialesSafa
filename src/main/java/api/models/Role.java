package api.models;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Role implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;

    @Override
    public String toString() {
        // The first letter of the role is capitalized
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
