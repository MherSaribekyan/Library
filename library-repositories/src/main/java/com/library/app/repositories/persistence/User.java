package com.library.app.repositories.persistence;

import com.library.app.repositories.persistence.base.BaseEntity;
import com.library.app.repositories.types.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "t_user",
        uniqueConstraints = {@UniqueConstraint(name = "uk_user_id_deleted", columnNames = {"id", "deleted"}),
                @UniqueConstraint(name = "uk_email_deleted", columnNames = {"email", "deleted"})})
public class User extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Setter(AccessLevel.NONE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private Set<UserPreference> userPreferences = new HashSet<>();

    @Setter(AccessLevel.NONE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private List<Address> addresses = new ArrayList<>();

    @Setter(AccessLevel.NONE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private List<PaymentCard> paymentCards = new ArrayList<>();


    public void addPreference(final UserPreference userPreference) {
        userPreference.setUser(this);
        this.userPreferences.add(userPreference);
    }

    public void addAddress(final Address address) {
        address.setUser(this);
        this.addresses.add(address);
    }

    public void addPaymentCard(final PaymentCard paymentCard) {
        paymentCard.setUser(this);
        this.paymentCards.add(paymentCard);
    }

    public void setUserPreferences(final List<UserPreference> userPreferences) {
        userPreferences.forEach(preference -> preference.setUser(this));
        this.userPreferences.clear();
        this.userPreferences.addAll(userPreferences);
    }

    public void setAddresses(final List<Address> addresses) {
        addresses.forEach(address -> address.setUser(this));
        this.addresses.clear();
        this.addresses.addAll(addresses);
    }

    public void setPaymentCards(final List<PaymentCard> paymentCards) {
        paymentCards.forEach(paymentCard -> paymentCard.setUser(this));
        this.paymentCards.clear();
        this.paymentCards.addAll(paymentCards);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final User user = (User) o;

        return new EqualsBuilder()
                .appendSuper(true)
                .append(this.name, user.name)
                .append(this.phone, user.phone)
                .append(this.email, user.email)
                .append(this.role, user.role)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(INIT_ODD, MULTIPLY_ODD)
                .appendSuper(super.hashCode())
                .append(this.name)
                .append(this.phone)
                .append(this.email)
                .append(this.role)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("name", this.name)
                .append("phone", this.phone)
                .append("email", this.email)
                .append("role", this.role)
                .toString();
    }

}
