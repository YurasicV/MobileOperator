package app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CALLS")
public class Call {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CALLER_PHONE_NUMBER_ID")
    private PhoneNumber callerPhoneNumber;

    @ManyToOne
    @JoinColumn(name = "RECIPIENT_PHONE_NUMBER_ID")
    private PhoneNumber recipientPhoneNumber;

    @Column(name = "CALL_DT")
    private Date callDt;

    @Column(name = "DURATION")
    private Double duration;

    @ManyToOne
    @JoinColumn(name = "CITY_ID")
    private City city;

    public Call() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PhoneNumber getCallerPhoneNumber() {
        return callerPhoneNumber;
    }

    public void setCallerPhoneNumber(PhoneNumber callerPhoneNumber) {
        this.callerPhoneNumber = callerPhoneNumber;
    }

    public PhoneNumber getRecipientPhoneNumber() {
        return recipientPhoneNumber;
    }

    public void setRecipientPhoneNumber(PhoneNumber recipientPhoneNumber) {
        this.recipientPhoneNumber = recipientPhoneNumber;
    }

    public Date getCallDt() {
        return callDt;
    }

    public void setCallDt(Date callDt) {
        this.callDt = callDt;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}