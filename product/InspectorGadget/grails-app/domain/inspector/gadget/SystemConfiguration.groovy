package inspector.gadget

import model.SystemConfigurationAwareness

class SystemConfiguration {
    String key
    String value

    transient static Collection<SystemConfigurationAwareness> observers = new ArrayList<SystemConfigurationAwareness>()

    static constraints = {
        key(nullable: false, blank: false, unique: true)
        value(nullable: false, blank: false)
    }

    static mapping = {
        table 'T_SYSTEM_CONFIGURATION'
        cache(true)
        version false
        id(column: 'P_ID')
        key(column: 'A_KEY')
        value(column: 'A_VALUE')
    }

    String toString() {
        return "${key}=${value}"
    }

    Integer valueAsInteger() {
        return Integer.valueOf(this.value)
    }
    Long valueAsLong() {
        return Long.valueOf(this.value)
    }

    Boolean valueAsBoolean() {
        return Boolean.valueOf(this.value)
    }

    public static void registerObserver(SystemConfigurationAwareness element) {
        if ( !observers.contains(element) ) {
            observers.add(element)
        }
    }

    def afterUpdate() {
        this.notifyObservers()
    }

    private def notifyObservers() {
        observers.each { observer ->
            observer.propertyChanged(this)
        }
    }
}