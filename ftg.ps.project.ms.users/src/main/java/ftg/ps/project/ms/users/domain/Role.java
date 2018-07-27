package ftg.ps.project.ms.users.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Role.
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "role_unique_id", nullable = false)
    private Long roleUniqueID;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "acteur_type_id")
    private Long acteurTypeID;

    @ManyToOne
    @JsonIgnoreProperties("roles")
    private Utilisateur utilisateur;

    @ManyToOne
    @JsonIgnoreProperties("opsRoles")
    private RoleOperation roleOperation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleUniqueID() {
        return roleUniqueID;
    }

    public Role roleUniqueID(Long roleUniqueID) {
        this.roleUniqueID = roleUniqueID;
        return this;
    }

    public void setRoleUniqueID(Long roleUniqueID) {
        this.roleUniqueID = roleUniqueID;
    }

    public String getRoleName() {
        return roleName;
    }

    public Role roleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getActeurTypeID() {
        return acteurTypeID;
    }

    public Role acteurTypeID(Long acteurTypeID) {
        this.acteurTypeID = acteurTypeID;
        return this;
    }

    public void setActeurTypeID(Long acteurTypeID) {
        this.acteurTypeID = acteurTypeID;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public Role utilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        return this;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public RoleOperation getRoleOperation() {
        return roleOperation;
    }

    public Role roleOperation(RoleOperation roleOperation) {
        this.roleOperation = roleOperation;
        return this;
    }

    public void setRoleOperation(RoleOperation roleOperation) {
        this.roleOperation = roleOperation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        if (role.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), role.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Role{" +
            "id=" + getId() +
            ", roleUniqueID=" + getRoleUniqueID() +
            ", roleName='" + getRoleName() + "'" +
            ", acteurTypeID=" + getActeurTypeID() +
            "}";
    }
}
