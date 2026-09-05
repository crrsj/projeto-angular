package br.com.empresa.controller;


import br.com.empresa.dto.DashboardDTO;
import br.com.empresa.service.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class DashBoardController {

    private final DashBoardService dashBoardService;

    @GetMapping
    public ResponseEntity<DashboardDTO> obterDadosDashboard() {
        return ResponseEntity.status(HttpStatus.OK).body(dashBoardService.obterDadosDashboard());
    }
}
