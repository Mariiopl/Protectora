package com.protectora.backend.services;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.protectora.backend.model.FotoMascota;
import com.protectora.backend.model.Mascota;
import com.protectora.backend.repository.FotoMascotaRepository;

@Service
public class FotoMascotaService {

    private final FotoMascotaRepository fotoMascotaRepository;
    private final String uploadDir = "I:/Protectora/frontend/src/assets/mascotas/";

    public FotoMascotaService(FotoMascotaRepository fotoMascotaRepository) {
        this.fotoMascotaRepository = fotoMascotaRepository;
        new File(uploadDir).mkdirs();
    }

    public FotoMascota guardarFoto(Mascota mascota, MultipartFile archivo) {
        try {
            String nombreArchivo = System.currentTimeMillis() + "_" + archivo.getOriginalFilename();
            File destino = new File(uploadDir + nombreArchivo);
            archivo.transferTo(destino);

            FotoMascota foto = FotoMascota.builder()
                    .mascota(mascota)
                    .urlImagen(nombreArchivo)
                    .build();

            return fotoMascotaRepository.save(foto);

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la foto", e);
        }
    }
}
