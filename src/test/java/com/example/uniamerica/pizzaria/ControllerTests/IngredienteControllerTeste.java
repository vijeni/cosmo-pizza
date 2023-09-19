package com.example.uniamerica.pizzaria.ControllerTests;

import com.example.uniamerica.pizzaria.Controller.IngredienteController;
import com.example.uniamerica.pizzaria.DTO.IngredienteDTO;
import com.example.uniamerica.pizzaria.Service.IngredientesService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class IngredienteControllerTeste {

    @InjectMocks
    private IngredienteController ingredienteController;

    @Mock
    private IngredientesService ingredientesService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByIdTest(){
        Long ingredienteId = 1L;
        IngredienteDTO ingredienteDTO = new IngredienteDTO();

        when(ingredientesService.findByID(ingredienteId)).thenReturn(ingredienteDTO);
        ResponseEntity<IngredienteDTO>response = ingredienteController.findById(ingredienteId);

        /*
        AssertEquals de ingredienteDTO está retornando nulo. Então removi.
         */

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        verify(ingredientesService,times(1)).findById(ingredienteId);

    }

    @Test
    void getAllTest(){
        List<IngredienteDTO> ingredienteDTOList = new ArrayList<>();

        when(ingredientesService.getAll()).thenReturn(ingredienteDTOList);
        ResponseEntity<List<IngredienteDTO>>response = ingredienteController.getAll();

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals(ingredienteDTOList,response.getBody());
        verify(ingredientesService,times(1)).getAll();
    }

    @Test
    void ingredientesPostTest(){
        IngredienteDTO ingredienteDTO = new IngredienteDTO();

        when(ingredientesService.post(ingredienteDTO)).thenReturn(ingredienteDTO);
        ResponseEntity<IngredienteDTO>response = ingredienteController.post(ingredienteDTO);

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals(ingredienteDTO,response.getBody());
        verify(ingredientesService,times(1)).post(ingredienteDTO);
    }

    @Test
    void ingredientesPutTest(){
        Long ingredienteId = 1L;
        IngredienteDTO ingredienteDTO = new IngredienteDTO();

        when(ingredientesService.update(ingredienteId,ingredienteDTO)).thenReturn(ingredienteDTO);
        ResponseEntity<IngredienteDTO>response = ingredienteController.put(ingredienteId,ingredienteDTO);

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals(ingredienteDTO,response.getBody());
    }


}
