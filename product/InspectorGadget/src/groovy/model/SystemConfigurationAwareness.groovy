package model

import inspector.gadget.SystemConfiguration

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
public interface SystemConfigurationAwareness {

    void propertyChanged(SystemConfiguration configuration)

}