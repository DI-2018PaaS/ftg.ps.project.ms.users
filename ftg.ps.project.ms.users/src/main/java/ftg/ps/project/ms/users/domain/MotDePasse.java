package ftg.ps.project.ms.users.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A MotDePasse.
 */
@Entity
@Table(name = "mot_de_passe")
public class MotDePasse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "valeur_mot_de_passe", nullable = false)
    private String valeurMotDePasse;

    @NotNull
    @Column(name = "mdp_owner_user", nullable = false)
    private Long mdpOwnerUser;

    @Column(name = "last_mot_de_passe")
    private String lastMotDePasse;

    @Column(name = "mdp_creation_date")
    private LocalDate mdpCreationDate;

    @Column(name = "mdp_last_connection_date")
    private LocalDate mdpLastConnectionDate;

    @Column(name = "mdp_last_modification_date")
    private LocalDate mdpLastModificationDate;

    @ManyToOne
    @JsonIgnoreProperties("motdePasses")
    private Utilisateur utilisateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValeurMotDePasse() {
        return valeurMotDePasse;
    }

    public MotDePasse valeurMotDePasse(String valeurMotDePasse) {
        this.valeurMotDePasse = valeurMotDePasse;
        return this;
    }

    public void setValeurMotDePasse(String valeurMotDePasse) {
        this.valeurMotDePasse = valeurMotDePasse;
    }

    public Long getMdpOwnerUser() {
        return mdpOwnerUser;
    }

    public MotDePasse mdpOwnerUser(Long mdpOwnerUser) {
        this.mdpOwnerUser = mdpOwnerUser;
        return this;
    }

    public void setMdpOwnerUser(Long mdpOwnerUser) {
        this.mdpOwnerUser = mdpOwnerUser;
    }

    public String getLastMotDePasse() {
        return lastMotDePasse;
    }

    public MotDePasse lastMotDePasse(String lastMotDePasse) {
        this.lastMotDePasse = lastMotDePasse;
        return this;
    }

    public void setLastMotDePasse(String lastMotDePasse) {
        this.lastMotDePasse = lastMotDePasse;
    }

    public LocalDate getMdpCreationDate() {
        return mdpCreationDate;
    }

    public MotDePasse mdpCreationDate(LocalDate mdpCreationDate) {
        this.mdpCreationDate = mdpCreationDate;
        return this;
    }

    public void setMdpCreationDate(LocalDate mdpCreationDate) {
        this.mdpCreationDate = mdpCreationDate;
    }

    public LocalDate getMdpLastConnectionDate() {
        return mdpLastConnectionDate;
    }

    public MotDePasse mdpLastConnectionDate(LocalDate mdpLastConnectionDate) {
        this.mdpLastConnectionDate = mdpLastConnectionDate;
        return this;
    }

    public void setMdpLastConnectionDate(LocalDate mdpLastConnectionDate) {
        this.mdpLastConnectionDate = mdpLastConnectionDate;
    }

    public LocalDate getMdpLastModificationDate() {
        return mdpLastModificationDate;
    }

    public MotDePasse mdpLastModificationDate(LocalDate mdpLastModificationDate) {
        this.mdpLastModificationDate = mdpLastModificationDate;
        return this;
    }

    public void setMdpLastModificationDate(LocalDate mdpLastModificationDate) {
        this.mdpLastModificationDate = mdpLastModificationDate;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public MotDePasse utilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        return this;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
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
        MotDePasse motDePasse = (MotDePasse) o;
        if (motDePasse.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), motDePasse.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MotDePasse{" +
            "id=" + getId() +
            ", valeurMotDePasse='" + getValeurMotDePasse() + "'" +
            ", mdpOwnerUser=" + getMdpOwnerUser() +
            ", lastMotDePasse='" + getLastMotDePasse() + "'" +
            ", mdpCreationDate='" + getMdpCreationDate() + "'" +
            ", mdpLastConnectionDate='" + getMdpLastConnectionDate() + "'" +
            ", mdpLastModificationDate='" + getMdpLastModificationDate() + "'" +
            "}";
    }
}
