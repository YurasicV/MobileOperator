package app.service;

import app.dto.CallsPerCity;
import app.dto.ClientAndDateRange;
import app.entity.Call;
import app.repository.CallRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    private CallRepository callRepository;

    public ReportService(CallRepository callRepository) {
        this.callRepository = callRepository;
    }

    public List<CallsPerCity> findCallsPerCities() {
        return callRepository.findCallsPerCities();
    }

    public Call findTheLongestCallByClientAndDataRange(ClientAndDateRange clientAndDateRange) {
        return callRepository.findCallsByClientAndDataRange(clientAndDateRange, PageRequest.of(0,1))
                .stream().findFirst().orElse(null);
    }
}
