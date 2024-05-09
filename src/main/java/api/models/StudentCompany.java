package api.models;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"id", "tutor"})
@EqualsAndHashCode
public class StudentCompany {
    private int id;
    private Person student;
    private Person tutor;
    private Company company;
}
