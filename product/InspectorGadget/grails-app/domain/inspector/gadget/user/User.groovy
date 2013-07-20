package inspector.gadget.user

class User {
    transient springSecurityService

    String username
    String password
    String firstName
    String lastName
    Date dateCreated
    Date lastUpdated

    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    static constraints = {
        username blank: false, unique: true
        password blank: false, password: true
        firstName blank: false
        lastName blank: false
    }

    static mapping = {
        table '`T_USER`'
        version false
        id column: 'P_ID'
        password column: 'A_PASSWORD'
        username column: 'A_USERNAME'
        firstName column: 'A_FIRSTNAME'
        lastName column: 'A_LASTNAME'
        dateCreated column: 'A_CREATION_DATE'
        lastUpdated column: 'A_LAST_MODIFICATION_DATE'
        accountExpired column: 'A_ACCOUNT_EXPIRED'
        accountLocked column: 'A_ACCOUNT_LOCKED'
        passwordExpired column: 'A_PASSWORD_EXPIRED'
        enabled column: 'A_IS_ENABLED'
    }

    String toString() {
        return "${firstName} ${lastName}"
    }

    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this).collect { it.role } as Set
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService.encodePassword(password)
    }
}