package inspector.gadget

class Application {
    String name
    String code
    Date dateCreated
    Date lastUpdated

    static constraints = {
        name(nullable: false, blank: false, unique: false)
        code(nullable: false, blank: false, unique: true)
    }

    static mapping = {
        table 'T_APPLICATION'
        version false
        id(column: 'P_ID')
        name(column: 'A_NAME')
        code(column: 'A_CODE')
        dateCreated(column: 'A_CREATION_DATE')
        lastUpdated(column: 'A_LAST_UPDATE_DATE')
    }


    @Override
    public String toString() {
      return this.name
    }
}