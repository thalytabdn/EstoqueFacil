package com.ufcg.psoft.model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufcg.psoft.model.User;
public class UserDTO {

    private User user;

    public UserDTO(User user){
        this.user = user;
    }

    @JsonIgnore
    public User getUser(){
        return user;
    }

    public String getEmail(){
        return this.user.getEmail();
    }

    public void setEmail(String email){
        this.user.setEmail(email);
    }

    public String getFirstName(){
        return user.getFirstName();
    }

    public void setFirstName(String firstName){
        user.setFirstName(firstName);
    }

    public String getLastName(){
        return user.getLastName();
    }

    public void setLastName(String lastName){
        user.setLastName(lastName);
    }
}
