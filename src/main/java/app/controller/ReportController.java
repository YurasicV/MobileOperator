package app.controller;

import app.dto.CallsPerCity;
import app.dto.ClientAndDateRange;
import app.entity.Call;
import app.service.ReportService;
import app.validation.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReportController {
    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/calls-per-cities/")
    public ResponseEntity<List<CallsPerCity>> listCallsPerCities() throws CustomException {
        List<CallsPerCity> callsPerCities = reportService.findCallsPerCities();
        if (callsPerCities.isEmpty()) {
            throw new CustomException("No content", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(callsPerCities, HttpStatus.OK);
    }

    @PostMapping("/the-longest-call/")
    public ResponseEntity<Call> getTheLongestCall(@Valid @RequestBody ClientAndDateRange clientAndDateRange) throws CustomException {
        Call call = reportService.findTheLongestCallByClientAndDataRange(clientAndDateRange);
        if (call == null) {
            throw new CustomException("No content", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(call, HttpStatus.OK);
    }
}