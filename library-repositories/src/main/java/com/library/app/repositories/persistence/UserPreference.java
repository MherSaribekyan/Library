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

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "t_user_preferences",
        uniqueConstraints = {@UniqueConstraint(name = "uk_user_preference_id_deleted", columnNames = {"id", "deleted"})})
public class UserPreference extends BaseEntity {

    @Column(name = "genre")
    private String genre;

    @Column(name = "old")
    private LocalDate old;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_user_preference_user_id"), nullable = false)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final UserPreference userPreference = (UserPreference) o;

        return new EqualsBuilder()
                .appendSuper(true)
                .append(this.genre, userPreference.genre)
                .append(this.old, userPreference.old)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(INIT_ODD, MULTIPLY_ODD)
                .appendSuper(super.hashCode())
                .append(this.genre)
                .append(this.old)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("genre", this.genre)
                .append("old", this.old)
                .toString();
    }
}
