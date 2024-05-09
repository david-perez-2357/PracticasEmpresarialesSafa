package api.models;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"id"})
@EqualsAndHashCode
public class Role {
    private int id;
    private String name;
}
