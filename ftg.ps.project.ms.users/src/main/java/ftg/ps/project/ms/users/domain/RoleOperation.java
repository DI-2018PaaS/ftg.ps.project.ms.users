package ftg.ps.project.ms.users.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A RoleOperation.
 */
@Entity
@Table(name = "role_operation")
public class RoleOperation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToMany(mappedBy = "roleOperation")
    private Set<Operation> roleOps = new HashSet<>();

    @OneToMany(mappedBy = "roleOperation")
    private Set<Role> opsRoles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Operation> getRoleOps() {
        return roleOps;
    }

    public RoleOperation roleOps(Set<Operation> operations) {
        this.roleOps = operations;
        return this;
    }

    public RoleOperation addRoleOps(Operation operation) {
        this.roleOps.add(operation);
        operation.setRoleOperation(this);
        return this;
    }

    public RoleOperation removeRoleOps(Operation operation) {
        this.roleOps.remove(operation);
        operation.setRoleOperation(null);
        return this;
    }

    public void setRoleOps(Set<Operation> operations) {
        this.roleOps = operations;
    }

    public Set<Role> getOpsRoles() {
        return opsRoles;
    }

    public RoleOperation opsRoles(Set<Role> roles) {
        this.opsRoles = roles;
        return this;
    }

    public RoleOperation addOpsRole(Role role) {
        this.opsRoles.add(role);
        role.setRoleOperation(this);
        return this;
    }

    public RoleOperation removeOpsRole(Role role) {
        this.opsRoles.remove(role);
        role.setRoleOperation(null);
        return this;
    }

    public void setOpsRoles(Set<Role> roles) {
        this.opsRoles = roles;
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
        RoleOperation roleOperation = (RoleOperation) o;
        if (roleOperation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), roleOperation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RoleOperation{" +
            "id=" + getId() +
            "}";
    }
}
