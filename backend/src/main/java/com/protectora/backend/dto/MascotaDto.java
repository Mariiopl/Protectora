package com.protectora.backend.dto;

import com.protectora.backend.model.Mascota;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class MascotaDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    private String nombre;

    @NotBlank(message = "La especie es obligatoria")
    @Size(max = 50)
    private String especie;

    @NotBlank(message = "La raza es obligatoria")
    @Size(max = 50)
    private String raza;

    @Min(0)
    @Max(50)
    private Integer edad;

    private Mascota.Tamano tamano;

    private Mascota.Sexo sexo;

    @Size(max = 200)
    private String caracter;

    @Size(max = 500)
    private String necesidadesEspeciales;

    private Boolean esterilizado;
    private Boolean vacunado;
    private Boolean desparasitado;

    private Mascota.EstadoAdopcion estadoAdopcion;

    @Size(max = 1000)
    private String historia;

    @Size(max = 200)
    private String ubicacion;

    private LocalDate fechaIngreso;

    /*
     * =======================================================================
     * MÉTODOS DE CONVERSIÓN
     * =======================================================================
     */

    public Mascota toEntity() {
        Mascota m = new Mascota();
        updateEntity(m);
        return m;
    }

    public void updateEntity(Mascota m) {
        m.setNombre(nombre);
        m.setEspecie(especie);
        m.setRaza(raza);
        m.setEdad(edad);
        m.setTamano(tamano);
        m.setSexo(sexo);
        m.setCaracter(caracter);
        m.setNecesidadesEspeciales(necesidadesEspeciales);
        m.setEsterilizado(esterilizado);
        m.setVacunado(vacunado);
        m.setDesparasitado(desparasitado);
        m.setEstadoAdopcion(
                estadoAdopcion != null ? estadoAdopcion : Mascota.EstadoAdopcion.adoptable);
        m.setHistoria(historia);
        m.setUbicacion(ubicacion);
        m.setFechaIngreso(fechaIngreso);
    }
}
