package com.sangboak.core.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@NoArgsConstructor
@Entity(name = "account_role_assignments")
@SQLDelete(sql="Update account_role_assignments SET deleted = true WHERE account_id = ? AND role_id = ?")
@Where(clause="deleted = false")
public class AccountRoleAssignment {

    @EmbeddedId
    private AccountRoleId id = new AccountRoleId();

    @Getter
    @Setter
    @ManyToOne
    @MapsId("accountId")
    private Account account;

    @Getter
    @Setter
    @ManyToOne
    @MapsId("roleId")
    private Role role;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @Builder
    public AccountRoleAssignment(Account account, Role role) {
        this.account = account;
        this.role = role;
        this.id = new AccountRoleId(account.getId(), role.getId());
    }
}
