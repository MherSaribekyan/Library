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

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "t_payment_card",
        uniqueConstraints = {@UniqueConstraint(name = "uk_payment_card_id_deleted", columnNames = {"id", "deleted"})})
public class PaymentCard extends BaseEntity {

    @Column(name = "pan")
    private String pan;

    @Column(name = "expirationDate")
    private LocalDate expirationDate;

    @Column(name = "cvv")
    private String cvv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_payment_card_user_id"), nullable = false)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final PaymentCard pc = (PaymentCard) o;

        return new EqualsBuilder()
                .appendSuper(true)
                .append(this.pan, pc.pan)
                .append(this.expirationDate, pc.expirationDate)
                .append(this.cvv, pc.cvv)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(INIT_ODD, MULTIPLY_ODD)
                .appendSuper(super.hashCode())
                .append(this.pan)
                .append(this.expirationDate)
                .append(this.cvv)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("pan", this.pan)
                .append("expirationDate", this.expirationDate)
                .append("cvv", this.cvv)
                .toString();
    }
}
