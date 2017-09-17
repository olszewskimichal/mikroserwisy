package pl.michal.olszewski.shoppingcartservice.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Long id;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private long createdDate;
    private long modifiedDate;

}
