package inspector.gadget.job

class Configuration {
    String name
    String notificationEmail
    Boolean isNotificationEnabled

    static constraints = {
        name(nullable: false, blank: false, unique: true)
        notificationEmail(nullable: false, blank: false, email: true)
        isNotificationEnabled(nullable: false)
    }

    static mapping = {
        table 'T_CONFIGURATION'
        version false
        id(column: 'P_ID')
        name(column: 'A_NAME')
        notificationEmail(column: 'A_NOTIFICATION_EMAIL')
        isNotificationEnabled(column: 'A_IS_NOTIFICATION_ENABLED')
    }

    String toString() {
        return name
    }
}
