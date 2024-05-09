package api.models;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {
            "companyCode",
            "cif",
            "direction",
            "postalCode",
            "location",
            "workday",
            "modality",
            "email"
        })
@EqualsAndHashCode
public class Company {
    private int companyCode;
    private String cif;
    private String name;
    private String direction;
    private int postalCode;
    private String location;
    private Workday workday;
    private Modality modality;
    private String email;
}
