
package com.tandc.blog.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String email;
    
    private String username;
    
    private String password;
    
    private String resetToken;
    
    private LocalDateTime resetTokenExpiration;
    
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
	@JoinTable(name="user_roles", 
	joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "id")
	)
	private Set<Role> roles;
    
    public List<String> getRolesName() {
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }
	
}
