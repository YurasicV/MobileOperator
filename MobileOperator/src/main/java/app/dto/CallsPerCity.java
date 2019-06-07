package app.dto;

public class CallsPerCity {
    private String cityName;
    private Long callsNumber;

    public CallsPerCity(String cityName, Long callsNumber) {
        this.cityName = cityName;
        this.callsNumber = callsNumber;
    }

    public CallsPerCity() {
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getCallsNumber() {
        return callsNumber;
    }

    public void setCallsNumber(Long callsNumber) {
        this.callsNumber = callsNumber;
    }
}
