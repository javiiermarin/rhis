package com.rhis.api.service;

import com.rhis.api.dto.VacacionesRequestDto;
import com.rhis.api.dto.VacacionesResponseDto;
import com.rhis.api.mapper.VacacionesMapper;
import com.rhis.api.model.VacacionesTracking;
import com.rhis.api.repository.EmpleadoRepository;
import com.rhis.api.repository.VacacionesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacacionesService {

    private final VacacionesRepository vacacionesRepository;
    private final EmpleadoRepository empleadoRepository;
    private final VacacionesMapper vacacionesMapper;

    public VacacionesService(VacacionesRepository vacacionesRepository, EmpleadoRepository empleadoRepository, VacacionesMapper vacacionesMapper) {
        this.vacacionesRepository = vacacionesRepository;
        this.empleadoRepository = empleadoRepository;
        this.vacacionesMapper = vacacionesMapper;
    }

    public VacacionesResponseDto registrarVacaciones(VacacionesRequestDto vacacionesRequestDto){
        var empleado = empleadoRepository.findById(vacacionesRequestDto.getEmpleado());
        var encargadoDivision = empleado.get().getPuesto().getDivision().getEncargado();
        var encargadoRrhh = empleadoRepository.findByPuestoIdPuesto("c0b7e4b8-c4bc-463e-8847-3e4dc1fa3ba5");

        var vacaciones = vacacionesMapper.toEntity(vacacionesRequestDto);
        vacaciones.setEmpleado(empleado.get());

        VacacionesTracking vacacionesTracking1 = new VacacionesTracking();
        vacacionesTracking1.setVacaciones(vacaciones);
        vacacionesTracking1.setEmpleado(encargadoDivision);
        vacacionesTracking1.setEstado(false);

        VacacionesTracking vacacionesTracking2 = new VacacionesTracking();
        vacacionesTracking2.setVacaciones(vacaciones);
        vacacionesTracking2.setEmpleado(encargadoRrhh);
        vacacionesTracking2.setEstado(false);

        vacaciones.setVacacionesTracking(List.of(vacacionesTracking1, vacacionesTracking2));

        return vacacionesMapper.toDto(vacacionesRepository.save(vacaciones));

    }

    public List<VacacionesResponseDto> obtenerVacacionesPorDivison(String iDivision){
        return vacacionesRepository.findAllByEmpleadoPuestoDivisionIdDivision(iDivision)
                .stream()
                .map(vacacionesMapper::toDto)
                .toList();
    }


}
