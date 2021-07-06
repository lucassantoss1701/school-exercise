package br.com.alura.school.registration;

public class NewRegistration{

    private String username;

    public NewRegistration(){

    }

    public NewRegistration(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
