package com.sangboak.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class AccountRoleId implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long accountId;
    private Long roleId;

    public AccountRoleId(Long accountId, Long roleId) {
        super();
        this.accountId = accountId;
        this.roleId = roleId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((accountId == null) ? 0 : accountId.hashCode());
        result = prime * result
                + ((roleId == null) ? 0 : roleId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AccountRoleId other = (AccountRoleId) obj;
        return Objects.equals(getAccountId(), other.getAccountId()) && Objects.equals(getRoleId(), other.getRoleId());
    }
}
