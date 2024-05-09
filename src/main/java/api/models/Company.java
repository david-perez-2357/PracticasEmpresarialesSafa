package api.models;

import lombok.*;


@AllArgsConstructor
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
            "email",
            "workManager",
            "workTutor"
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
    private Person workManager;
    private Person workTutor;
}
