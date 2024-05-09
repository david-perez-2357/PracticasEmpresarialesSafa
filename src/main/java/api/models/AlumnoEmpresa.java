package api.models;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"id", "tutor"})
@EqualsAndHashCode
public class AlumnoEmpresa {
    private int id;
    private Persona alumno;
    private Persona tutor;
    private Empresa empresa;
}
