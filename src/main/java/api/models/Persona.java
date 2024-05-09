package api.models;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString(exclude =
        {
            "id",
            "dni",
            "telefono",
            "email",
            "rol"
        })
@EqualsAndHashCode
public class Persona {
    private int id;
    private String dni;
    private String nombre;
    private String apellidos;
    private int telefono;
    private String email;
    private Rol rol;
}
