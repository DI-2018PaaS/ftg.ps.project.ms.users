package ftg.ps.project.ms.users.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Operation.
 */
@Entity
@Table(name = "operation")
public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "operation_unique_id", nullable = false)
    private Long operationUniqueID;

    @Column(name = "operation_name")
    private String operationName;

    @Column(name = "operation_description")
    private String operationDescription;

    @ManyToOne
    @JsonIgnoreProperties("roleOps")
    private RoleOperation roleOperation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOperationUniqueID() {
        return operationUniqueID;
    }

    public Operation operationUniqueID(Long operationUniqueID) {
        this.operationUniqueID = operationUniqueID;
        return this;
    }

    public void setOperationUniqueID(Long operationUniqueID) {
        this.operationUniqueID = operationUniqueID;
    }

    public String getOperationName() {
        return operationName;
    }

    public Operation operationName(String operationName) {
        this.operationName = operationName;
        return this;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getOperationDescription() {
        return operationDescription;
    }

    public Operation operationDescription(String operationDescription) {
        this.operationDescription = operationDescription;
        return this;
    }

    public void setOperationDescription(String operationDescription) {
        this.operationDescription = operationDescription;
    }

    public RoleOperation getRoleOperation() {
        return roleOperation;
    }

    public Operation roleOperation(RoleOperation roleOperation) {
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
        Operation operation = (Operation) o;
        if (operation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Operation{" +
            "id=" + getId() +
            ", operationUniqueID=" + getOperationUniqueID() +
            ", operationName='" + getOperationName() + "'" +
            ", operationDescription='" + getOperationDescription() + "'" +
            "}";
    }
}
