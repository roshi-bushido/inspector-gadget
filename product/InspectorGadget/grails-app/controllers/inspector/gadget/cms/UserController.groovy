package inspector.gadget.cms
import inspector.gadget.user.User

class UserController extends CmsController {
    static scaffold = User
    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

}
