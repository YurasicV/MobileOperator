package app.controller;

import app.dto.CallsPerCity;
import app.dto.ClientAndDateRange;
import app.entity.Call;
import app.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReportController {
    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/calls-per-cities/")
    public ResponseEntity<List<CallsPerCity>> listCallsPerCities() {
        List<CallsPerCity> callsPerCities = reportService.findCallsPerCities();
        if (callsPerCities.isEmpty()) {
            return new ResponseEntity<>(callsPerCities, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(callsPerCities, HttpStatus.OK);
    }

    @PostMapping("/the-longest-call/")
    public ResponseEntity<Call> getTheLongestCall(@RequestBody ClientAndDateRange clientAndDateRange) {
        Call call = reportService.findTheLongestCallByClientAndDataRange(clientAndDateRange);
        if (call == null) {
            return new ResponseEntity<>(new Call(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(call, HttpStatus.OK);
    }
}