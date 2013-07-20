hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}

// environment specific settings
environments {
    development {
      dataSource {
          dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
          url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
      }
    }
    test {
        dataSource {
            driverClassName = "com.mysql.jdbc.Driver"
            username = "gadget"
            password = "gadget"
            dbCreate = "validate" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:mysql://localhost:3306/gadget"
        }
    }
    production {
        dataSource {
            jndiName = "java:/comp/env/jdbc/inspectorGadgetDS"
        }
    }
}
