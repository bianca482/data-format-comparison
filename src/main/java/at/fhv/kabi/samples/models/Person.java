package at.fhv.kabi.samples.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.Serializable;

// Subset of the VCARD specification https://datatracker.ietf.org/doc/html/rfc6350
public class Person implements Serializable {
	private String fn;
    private String bday;
    private String gender;
    private String adr;
    private String email;
    private String tel;
    private String lang;
    private String version;

    public Person() {}

    public Person(String fn, String bday, String gender, String adr, String email, String tel, String lang, String version) {
        this.fn = fn;
        this.bday = bday;
        this.gender = gender;
        this.adr = adr;
        this.email = email;
        this.tel = tel;
        this.lang = lang;
        this.version = version;
    }

    public String getFn() {
        return fn;
    }

    public void setFn(String fn) {
        this.fn = fn;
    }

    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        JsonMapper mapper = new JsonMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public String toFormattedString() {
        return "Person{" +
                "fn='" + fn + '\'' +
                ", bday=" + bday +
                ", gender=" + gender +
                ", adr='" + adr + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", lang='" + lang + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
