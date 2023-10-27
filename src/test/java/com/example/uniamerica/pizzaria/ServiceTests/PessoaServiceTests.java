package com.example.uniamerica.pizzaria.ServiceTests;

import com.example.uniamerica.pizzaria.dto.PessoaDTO;
import com.example.uniamerica.pizzaria.entity.Endereco;
import com.example.uniamerica.pizzaria.entity.Pessoa;
import com.example.uniamerica.pizzaria.entity.TipoPessoa;
import com.example.uniamerica.pizzaria.repository.PessoaRepository;
import com.example.uniamerica.pizzaria.service.PessoaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
 class PessoaServiceTests {
    @Mock
    private PessoaRepository repository;

    @InjectMocks
    private PessoaService service;

    private List<PessoaDTO> pessoaDTOList = new ArrayList<>();
    private PessoaDTO pessoaDTO = new PessoaDTO();
    private List<Endereco>enderecoList;
    private Pessoa pessoaEntidade = new Pessoa();
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        pessoaEntidade.setTipoPessoa(TipoPessoa.FUNCIONARIO);
        pessoaEntidade.setId(1L);
        pessoaEntidade.setNome("nome");
        pessoaEntidade.setCpf("cpf");
        pessoaEntidade.setTelefone("telefone");

        pessoaDTO.setTipoPessoa(TipoPessoa.FUNCIONARIO);
        pessoaDTO.setCpf("cpf");
        pessoaDTO.setId(1L);
        pessoaDTO.setNome("nome");
        pessoaDTO.setTelefone("telefone");

        pessoaDTOList.add(pessoaDTO);


        when(repository.findAll()).thenReturn(pessoaDTOList.stream().map(pessoaDTO -> service.toPessoa(pessoaDTO)).toList());
        when(repository.save(Mockito.any(Pessoa.class))).thenReturn(pessoaEntidade);
        when(repository.findById(1L)).thenReturn(Optional.of(pessoaEntidade));

    }
    @Test
    void pessoaDtoToPessoaEntityTest(){
        Pessoa pessoa = service.toPessoa(pessoaDTO);
        org.assertj.core.api.Assertions.assertThat(pessoa).usingRecursiveComparison().isEqualTo(pessoaEntidade);
    }
    @Test
    void pessoaEntidadeToPessoaDtoTest(){
        PessoaDTO pessoa = service.toPessoaDTO(pessoaEntidade);
        org.assertj.core.api.Assertions.assertThat(pessoa).usingRecursiveComparison().isEqualTo(pessoaDTO);
    }

    @Test
    void pessoaGetById(){
        PessoaDTO result = service.findById(1L);
        Assertions.assertNotNull(result);
        verify(repository,times(1)).findById(1L);
    }

    @Test
    void pessoaGetAll(){
        List<PessoaDTO>result = service.getAll();
        Assertions.assertNotNull(service);
        assertThat(result).usingRecursiveComparison().isEqualTo(pessoaDTOList);
    }

    @Test
    void pessoaPostTest(){
        PessoaDTO result = service.post(pessoaDTO);
        verify(repository,times(1)).save(Mockito.any(Pessoa.class));

        Assertions.assertNotNull(result);

        Assertions.assertEquals("nome",result.getNome());
        Assertions.assertEquals(1L,result.getId());
        Assertions.assertEquals("cpf",result.getCpf());
        Assertions.assertEquals("telefone",result.getTelefone());
        Assertions.assertEquals(TipoPessoa.FUNCIONARIO,result.getTipoPessoa());

    }

    @Test
    void pessoaPutTest(){
        PessoaDTO result = service.put(pessoaDTO,1L);
        Assertions.assertNotNull(result);
        assertThat(result).usingRecursiveComparison().isEqualTo(pessoaDTO);
    }

    @Test
    void pessoaDeletarTest() {
        service.desativar(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}
