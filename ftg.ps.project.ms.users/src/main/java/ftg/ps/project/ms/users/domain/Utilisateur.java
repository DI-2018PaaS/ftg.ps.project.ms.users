package ftg.ps.project.ms.users.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Utilisateur.
 */
@Entity
@Table(name = "utilisateur")
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "username")
    private String username;

    @NotNull
    @Column(name = "util_uniqueid", nullable = false)
    private Long utilUniqueid;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "mail")
    private String mail;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "jhi_password")
    private String password;

    @Column(name = "creation_date_user")
    private LocalDate creationDateUser;

    @Column(name = "last_connection_date")
    private LocalDate lastConnectionDate;

    @Column(name = "user_acteur_id")
    private Long userActeurID;

    @OneToMany(mappedBy = "utilisateur")
    private Set<MotDePasse> motdePasses = new HashSet<>();

    @OneToMany(mappedBy = "utilisateur")
    private Set<Role> roles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public Utilisateur username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUtilUniqueid() {
        return utilUniqueid;
    }

    public Utilisateur utilUniqueid(Long utilUniqueid) {
        this.utilUniqueid = utilUniqueid;
        return this;
    }

    public void setUtilUniqueid(Long utilUniqueid) {
        this.utilUniqueid = utilUniqueid;
    }

    public String getLastName() {
        return lastName;
    }

    public Utilisateur lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Utilisateur firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMail() {
        return mail;
    }

    public Utilisateur mail(String mail) {
        this.mail = mail;
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Utilisateur phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public Utilisateur password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getCreationDateUser() {
        return creationDateUser;
    }

    public Utilisateur creationDateUser(LocalDate creationDateUser) {
        this.creationDateUser = creationDateUser;
        return this;
    }

    public void setCreationDateUser(LocalDate creationDateUser) {
        this.creationDateUser = creationDateUser;
    }

    public LocalDate getLastConnectionDate() {
        return lastConnectionDate;
    }

    public Utilisateur lastConnectionDate(LocalDate lastConnectionDate) {
        this.lastConnectionDate = lastConnectionDate;
        return this;
    }

    public void setLastConnectionDate(LocalDate lastConnectionDate) {
        this.lastConnectionDate = lastConnectionDate;
    }

    public Long getUserActeurID() {
        return userActeurID;
    }

    public Utilisateur userActeurID(Long userActeurID) {
        this.userActeurID = userActeurID;
        return this;
    }

    public void setUserActeurID(Long userActeurID) {
        this.userActeurID = userActeurID;
    }

    public Set<MotDePasse> getMotdePasses() {
        return motdePasses;
    }

    public Utilisateur motdePasses(Set<MotDePasse> motDePasses) {
        this.motdePasses = motDePasses;
        return this;
    }

    public Utilisateur addMotdePasses(MotDePasse motDePasse) {
        this.motdePasses.add(motDePasse);
        motDePasse.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeMotdePasses(MotDePasse motDePasse) {
        this.motdePasses.remove(motDePasse);
        motDePasse.setUtilisateur(null);
        return this;
    }

    public void setMotdePasses(Set<MotDePasse> motDePasses) {
        this.motdePasses = motDePasses;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Utilisateur roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Utilisateur addRoles(Role role) {
        this.roles.add(role);
        role.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeRoles(Role role) {
        this.roles.remove(role);
        role.setUtilisateur(null);
        return this;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
        Utilisateur utilisateur = (Utilisateur) o;
        if (utilisateur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), utilisateur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", utilUniqueid=" + getUtilUniqueid() +
            ", lastName='" + getLastName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", mail='" + getMail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", password='" + getPassword() + "'" +
            ", creationDateUser='" + getCreationDateUser() + "'" +
            ", lastConnectionDate='" + getLastConnectionDate() + "'" +
            ", userActeurID=" + getUserActeurID() +
            "}";
    }
}
