package com.ads.elements.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Document
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {

    @Id
    private String id;

    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])([a-zA-Z0-9@$!%*?&]{8,30})$",
            message = "must be between 8 and 30 characters and must consist of at-least one of: " +
                    "uppercase alphabet, lowercase alphabet, number and a special character")
    private String password;

    private String confirmPassword;

    @NotEmpty
    @Size(min = 2, max = 30, message = "must be between 2 and 30 characters")
    private String firstName;

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z]+$",
            message = "must consist of only alphabets")
    @Size(min = 1, max = 30, message = "be between 1 and 30 characters")
    private String lastName;

    private boolean enabled = true;

    @DBRef
    private Set<Role> roles;

}