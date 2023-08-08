package com.library.app.repositories.persistence;

import com.library.app.repositories.persistence.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "t_address",
        uniqueConstraints = {@UniqueConstraint(name = "uk_address_id_deleted", columnNames = {"id", "deleted"})})
public class Address extends BaseEntity {

    @Column(name = "country")
    private String country;

    @Column(name = "postalZip")
    private String postalZip;

    @Column(name = "house")
    private String house;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_address_user_id"), nullable = false)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Address address = (Address) o;

        return new EqualsBuilder()
                .appendSuper(true)
                .append(this.country, address.country)
                .append(this.postalZip, address.postalZip)
                .append(this.house, address.house)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(INIT_ODD, MULTIPLY_ODD)
                .appendSuper(super.hashCode())
                .append(this.country)
                .append(this.postalZip)
                .append(this.house)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("country", this.country)
                .append("postalZip", this.postalZip)
                .append("house", this.house)
                .toString();
    }


}
