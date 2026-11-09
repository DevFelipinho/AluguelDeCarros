package web.trabalhodaw1.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import web.trabalhodaw1.core.web.pagination.PageWrapper;
import web.trabalhodaw1.dto.carro.CarroCreateRequest;
import web.trabalhodaw1.dto.carro.CarroResponse;
import web.trabalhodaw1.dto.carro.CarroSearchRequest;
import web.trabalhodaw1.dto.carro.CarroUpdateRequest;
import web.trabalhodaw1.service.CarroService;

@Slf4j
@Controller
@RequestMapping("/carros")
public class CarroController {

    private final CarroService carroService;

    public CarroController(CarroService carroService) {
        this.carroService = carroService;
    }

    @GetMapping
    public String pesquisar(CarroSearchRequest request, Model model,
                            @PageableDefault(size = 7)
                            @SortDefault(sort = "id", direction = Sort.Direction.ASC)
                            Pageable pageable,
                            HttpServletRequest httpRequest,
                            HtmxRequest htmxRequest) {
        Page<CarroResponse> pagina = carroService.pesquisar(request, pageable);
        log.info("Carros pesquisados: {}", pagina.getContent());
        PageWrapper<CarroResponse> paginaWrapper = new PageWrapper<>(pagina, httpRequest);
        model.addAttribute("pagina", paginaWrapper);
        model.addAttribute("carroSearchRequest", request != null ? request : new CarroSearchRequest(null, null, null, null, null, null, null));
        return htmxRequest.isHtmxRequest() ? "carros/lista :: conteudo" : "carros/lista";
    }

    @GetMapping("/novo")
    public String abrirCadastro(CarroCreateRequest form, HtmxRequest htmxRequest, Model model) {
        model.addAttribute("carroCreateRequest", form != null ? form : new CarroCreateRequest("", "", "", "", null, true));
        return htmxRequest.isHtmxRequest() ? "carros/create :: formulario" : "carros/create";
    }

    @PostMapping
    public String cadastrar(@Valid CarroCreateRequest form,
                            BindingResult resultado,
                            RedirectAttributes redirect,
                            HtmxRequest htmxRequest,
                            Model model) {
        if (resultado.hasErrors()) {
            log.info("O carro recebido para cadastrar não é válido. Erros: {}", resultado.getAllErrors());
            model.addAttribute("carroCreateRequest", form);
            return htmxRequest.isHtmxRequest() ? "carros/create :: formulario" : "carros/create";
        }
        try {
            carroService.salvar(form);
            redirect.addFlashAttribute("mensagem", "Carro cadastrado com sucesso!");
            return htmxRequest.isHtmxRequest() ? "redirect:htmx:location:/carros" : "redirect:/carros";
        } catch (IllegalArgumentException e) {
            resultado.addError(new FieldError("carroCreateRequest", "placa", e.getMessage()));
            model.addAttribute("carroCreateRequest", form);
            return htmxRequest.isHtmxRequest() ? "carros/create :: formulario" : "carros/create";
        }
    }

    @GetMapping("/{id}/editar")
    public String abrirEdicao(@PathVariable("id") Long id, Model model, HtmxRequest htmxRequest) {
        CarroUpdateRequest request = carroService.buscarParaEdicao(id);
        model.addAttribute("request", request);
        model.addAttribute("id", id);
        return htmxRequest.isHtmxRequest() ? "carros/edit :: formulario" : "carros/edit";
    }

    @PutMapping("/{id}")
    public String atualizar(@PathVariable("id") Long id,
                            @Valid CarroUpdateRequest request,
                            BindingResult resultado,
                            RedirectAttributes redirect,
                            HtmxRequest htmxRequest,
                            Model model) {
        model.addAttribute("id", id);
        if (resultado.hasErrors()) {
            log.info("O carro recebido para editar não é válido. Erros: {}", resultado.getAllErrors());
            model.addAttribute("request", request);
            return htmxRequest.isHtmxRequest() ? "carros/edit :: formulario" : "carros/edit";
        }
        try {
            carroService.atualizar(id, request);
            redirect.addFlashAttribute("mensagem", "Carro alterado com sucesso!");
            return htmxRequest.isHtmxRequest() ? "redirect:htmx:location:/carros" : "redirect:/carros";
        } catch (IllegalArgumentException e) {
            resultado.addError(new FieldError("request", "placa", e.getMessage()));
            model.addAttribute("request", request);
            return htmxRequest.isHtmxRequest() ? "carros/edit :: formulario" : "carros/edit";
        }
    }

    @DeleteMapping("/{id}")
    public String remover(@PathVariable("id") Long id,
                          RedirectAttributes redirect,
                          HtmxRequest htmxRequest) {
        carroService.remover(id);
        redirect.addFlashAttribute("mensagem", "Carro removido com sucesso!");
        return htmxRequest.isHtmxRequest() ? "redirect:htmx:location:/carros" : "redirect:/carros";
    }
}
