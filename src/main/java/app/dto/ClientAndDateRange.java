package app.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ClientAndDateRange {
    @NotNull(message = "ClientId is mandatory")
    private Long clientId;

    @NotNull(message = "DateFrom is mandatory")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateFrom;

    @NotNull(message = "DateTo is mandatory")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateTo;

    public ClientAndDateRange() {
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
}
