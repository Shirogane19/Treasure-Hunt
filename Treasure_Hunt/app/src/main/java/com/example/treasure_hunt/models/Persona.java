package com.example.treasure_hunt.models;

public class Persona {

    private String Id;
    private String Correo;
    private String Password;

    public Persona() {
    }

    public Persona(String id, String correo, String password) {
        Id = id;
        Correo = correo;
        Password = password;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return Correo;
    }
}
