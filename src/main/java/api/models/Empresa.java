package api.models;

import lombok.*;


@AllArgsConstructor
@Getter
@Setter
@ToString(exclude =
        {
            "codigoEmpresa",
            "cif",
            "direccion",
            "codigoPostal",
            "localidad",
            "jornada",
            "modalidad",
            "email",
            "responsable",
            "tutorLaboral"
        })
@EqualsAndHashCode
public class Empresa {
    private int codigoEmpresa;
    private String cif;
    private String nombre;
    private String direccion;
    private int codigoPostal;
    private String localidad;
    private Jornada jornada;
    private Modalidad modalidad;
    private String email;
    private Persona responsable;
    private Persona tutorLaboral;
}
