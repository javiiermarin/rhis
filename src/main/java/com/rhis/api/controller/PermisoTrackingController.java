package com.rhis.api.controller;

import com.rhis.api.dto.PermisoTrackingRequestDto;
import com.rhis.api.dto.PermisoTrackingResponseDto;
import com.rhis.api.service.PermisoTrackingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rhis/tracking")
public class PermisoTrackingController {

    private final PermisoTrackingService permisoTrackingService;

    public PermisoTrackingController(PermisoTrackingService permisoTrackingService) {
        this.permisoTrackingService = permisoTrackingService;
    }

    /**
     * peticion que devuelve una lista de permisos traking dependiendo de el estado
     *
     * @param estado
     * @return
     */

    @GetMapping
    public ResponseEntity<List<PermisoTrackingResponseDto>> obtenerPermisoTracking(
            @RequestParam(value = "estado") boolean estado){

        var permisoTracking = permisoTrackingService.obtenerTracking(estado);
        return new ResponseEntity<>(permisoTracking, HttpStatus.OK);
    }

    /**
     * peticion para actualizar el estado de un permiso
     *
     * @param idTracking
     * @param permisoTrackingRequestDto
     * @return
     */

    @PutMapping("/{idTracking}")
    public ResponseEntity<PermisoTrackingResponseDto> actualizarPermisoTracking(
            @PathVariable String idTracking,
            @RequestBody @Valid PermisoTrackingRequestDto permisoTrackingRequestDto){
        var perimisoTracking = permisoTrackingService.actualizarTracking(idTracking, permisoTrackingRequestDto);
        return new ResponseEntity<>(perimisoTracking, HttpStatus.OK);

    }



}
