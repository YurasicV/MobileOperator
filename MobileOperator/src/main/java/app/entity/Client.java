package app.entity;

import app.converter.GenderConverter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "CLIENTS")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "FULLNAME", length = 50, nullable = false)
    @NotBlank(message = "Full name is mandatory")
    private String fullName;

    @Column(name = "BIRTHDAY")
    private Date birthday;

    @Column(name = "GENDER_ID")
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    @OneToMany(mappedBy="client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PhoneNumber> phoneNumbers;

    public Client(String fullName, Date birthday, Gender gender) {
        this.fullName = fullName;
        this.birthday = birthday;
        this.gender = gender;
    }

    public Client() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Set<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Set<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id.equals(client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}