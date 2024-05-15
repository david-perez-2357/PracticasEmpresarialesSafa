package api.models;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Workday {
    private int id;
    private String name;

    public String toString() {
        return name;
    }
}
