package api.models;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"id", "tutor"})
@EqualsAndHashCode
public class StudentCompany {
    private int id;
    private Persona student;
    private Persona tutor;
    private Company company;
}
