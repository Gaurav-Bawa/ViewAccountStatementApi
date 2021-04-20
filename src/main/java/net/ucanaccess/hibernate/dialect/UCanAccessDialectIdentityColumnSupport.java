package net.ucanaccess.hibernate.dialect;

import org.hibernate.dialect.identity.IdentityColumnSupportImpl;

/**
 * 
 * Hibernate dialect for UCanAccess - identity column support
 * 
 */
public class UCanAccessDialectIdentityColumnSupport extends IdentityColumnSupportImpl {
    @Override
    public boolean supportsIdentityColumns() {
        return true;
    }

    @Override
    public boolean hasDataTypeInIdentityColumn() {
        return false;
    }

    @Override
    public String getIdentityColumnString(int type) {
        return "COUNTER";
    }
}