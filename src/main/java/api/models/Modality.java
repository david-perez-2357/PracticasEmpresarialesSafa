package api.models;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"id"})
@EqualsAndHashCode
public class Modality {
    private int id;
    private String name;
}
