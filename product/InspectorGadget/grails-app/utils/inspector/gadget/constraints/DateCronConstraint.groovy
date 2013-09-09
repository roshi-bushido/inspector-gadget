package inspector.gadget.constraints

import inspector.gadget.util.DateUtil

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
class DateCronConstraint {

    def validate = { sourceField, sourceObject, errorList ->
        return DateUtil.validateCronExpression(sourceField)
    }
}
