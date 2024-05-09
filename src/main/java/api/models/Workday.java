package api.models;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"id"})
@EqualsAndHashCode
public class Workday {
    private int id;
    private String name;
}
