package com.martingarrote.equip_rental.domain.user;

import com.martingarrote.equip_rental.infrastructure.persistence.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "User")
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(of = {"id", "email"}, callSuper = false)
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;

    @NotBlank(message = "{user.name.notBlank}")
    @Size(min = 2, max = 100, message = "{user.name.size}")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotBlank(message = "{user.email.notBlank}")
    @Email(message = "{user.email.valid}")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "{user.password.notBlank}")
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull(message = "{user.role.notNull}")
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private Role role;

    @NotBlank(message = "{user.phone.notBlank}")
    @Size(min = 10, max = 20, message = "{user.phone.size}")
    @Pattern(regexp = "^[0-9+\\(\\)\\s-]*$", message = "{user.phone.invalid}")
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Size(max = 100, message = "{user.companyName.size}")
    @Column(name = "company_name", length = 100)
    private String companyName;

    @Size(max = 18, message = "{user.companyCnpj.size}")
    @Pattern( regexp = "^(\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2})?$", message = "{user.companyCnpj.invalid}")
    @Column(name = "company_cnpj", length = 18)
    private String companyCnpj;

    @Size(max = 50, message = "{user.department.size}")
    @Column(name = "department", length = 50)
    private String department;

    @Size(max = 50, message = "{user.position.size}")
    @Column(name = "position", length = 50)
    private String position;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

}
