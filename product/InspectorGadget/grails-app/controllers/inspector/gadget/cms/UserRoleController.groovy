package inspector.gadget.cms

import inspector.gadget.user.UserRole

class UserRoleController extends CmsController {
    static scaffold = UserRole
    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

}
