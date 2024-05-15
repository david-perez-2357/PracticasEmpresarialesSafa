package api.models;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Modality {
    private int id;
    private String name;

    public String toString() {
        return name;
    }
}
