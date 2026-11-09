package web.trabalhodaw1.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import web.trabalhodaw1.common.enums.Status;
import web.trabalhodaw1.dto.carro.CarroCreateRequest;
import web.trabalhodaw1.dto.carro.CarroResponse;
import web.trabalhodaw1.dto.carro.CarroSearchRequest;
import web.trabalhodaw1.dto.carro.CarroUpdateRequest;
import web.trabalhodaw1.model.Carro;
import web.trabalhodaw1.repository.CarroRepository;

@Service
public class CarroService {

    private final CarroRepository carroRepository;

    public CarroService(CarroRepository carroRepository) {
        this.carroRepository = carroRepository;
    }

    public Page<CarroResponse> pesquisar(CarroSearchRequest request, Pageable pageable) {
        if (request == null || request.isEmpty()) {
            return carroRepository.findAllByStatus(Status.ATIVO, pageable)
                    .map(this::toResponse);
        }

        Carro carroFiltro = toEntity(request);
        carroFiltro.setStatus(Status.ATIVO);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        return carroRepository.findAll(Example.of(carroFiltro, matcher), pageable)
                .map(this::toResponse);
    }

    @Transactional
    public void salvar(CarroCreateRequest form) {
        if (carroRepository.existsByPlacaAndStatus(form.placa(), Status.ATIVO)) {
            throw new IllegalArgumentException("Já existe um carro cadastrado com a placa informada.");
        }
        carroRepository.save(toEntity(form));
    }

    public CarroUpdateRequest buscarParaEdicao(Long id) {
        Carro carro = carroRepository.findByIdAndStatus(id, Status.ATIVO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carro não encontrado"));
        return toUpdateRequest(carro);
    }

    @Transactional
    public void atualizar(Long id, CarroUpdateRequest request) {
        Carro carro = carroRepository.findByIdAndStatus(id, Status.ATIVO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carro não encontrado"));

        if (carroRepository.existsByPlacaAndIdNotAndStatus(request.placa(), id, Status.ATIVO)) {
            throw new IllegalArgumentException("Já existe outro carro cadastrado com a placa informada.");
        }

        carro.setPlaca(request.placa());
        carro.setMarca(request.marca());
        carro.setModelo(request.modelo());
        carro.setCor(request.cor());
        carro.setValorDiaria(request.valorDiaria());
        carro.setDisponivel(request.disponivel() != null ? request.disponivel() : true);

        carroRepository.save(carro);
    }

    @Transactional
    public void remover(Long id) {
        Carro carro = carroRepository.findByIdAndStatus(id, Status.ATIVO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carro não encontrado"));
        carro.setStatus(Status.INATIVO);
        carroRepository.save(carro);
    }

    private CarroUpdateRequest toUpdateRequest(Carro carro) {
        return new CarroUpdateRequest(
                carro.getPlaca(),
                carro.getMarca(),
                carro.getModelo(),
                carro.getCor(),
                carro.getValorDiaria(),
                carro.isDisponivel()
        );
    }

    private CarroResponse toResponse(Carro carro) {
        return new CarroResponse(
                carro.getId(),
                carro.getPlaca(),
                carro.getMarca(),
                carro.getModelo(),
                carro.getCor(),
                carro.getValorDiaria(),
                carro.isDisponivel()
        );
    }

    private Carro toEntity(CarroSearchRequest request) {
        Carro carro = new Carro();
        carro.setId(request.id());
        carro.setPlaca(request.placa());
        carro.setMarca(request.marca());
        carro.setModelo(request.modelo());
        carro.setCor(request.cor());
        carro.setValorDiaria(request.valorDiaria());
        if (request.disponivel() != null) {
            carro.setDisponivel(request.disponivel());
        }
        return carro;
    }

    private Carro toEntity(CarroCreateRequest request) {
        Carro carro = new Carro();
        carro.setPlaca(request.placa());
        carro.setMarca(request.marca());
        carro.setModelo(request.modelo());
        carro.setCor(request.cor());
        carro.setValorDiaria(request.valorDiaria());
        carro.setDisponivel(request.disponivel() != null ? request.disponivel() : true);
        carro.setStatus(Status.ATIVO);
        return carro;
    }
}
