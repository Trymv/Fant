package com.mycompany.fant;

import javax.annotation.security.DeclareRoles;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.PasswordHash;
import no.ntnu.tollefsen.auth.Group;
import org.eclipse.microprofile.auth.LoginConfig;

/**
 *
 * @author mikael
 */
@DatabaseIdentityStoreDefinition(
    dataSourceLookup=DatasourceProducer.JNDI_NAME,
    callerQuery="select password from users where id = ?",
    groupsQuery="select name from ausergroup where id  = ?",
    hashAlgorithm = PasswordHash.class,
    priority = 80)
@DeclareRoles({Group.ADMIN,Group.USER})
@LoginConfig(authMethod = "MP-JWT",realmName = "template")
public class SecurityConfiguration {    
}
