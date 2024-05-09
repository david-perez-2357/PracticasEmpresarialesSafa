package api.models;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"id"})
@EqualsAndHashCode
public class Rol {
    private int id;
    private String nombre;
}
