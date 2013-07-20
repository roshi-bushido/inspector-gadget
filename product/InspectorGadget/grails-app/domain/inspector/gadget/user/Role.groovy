package inspector.gadget.user

class Role {
  String authority

  static mapping = {
      table '`T_ROLE`'
      version false
      cache true
      id (column: 'P_ID')
      authority (column: 'A_AUTHORITY')
 	}

	static constraints = {
      authority blank: false, unique: true
	}

  String toString() {
    return authority
  }
}
