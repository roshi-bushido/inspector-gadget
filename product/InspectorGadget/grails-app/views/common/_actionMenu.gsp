<div class="well sidebar-nav">
    <a class="brand" href="${createLink(uri: '/')}">
        <img src="${resource(dir: 'images', file: 'logo.png')}" alt="Inspector Gadget" width="100px"
             height="100px"/>
    </a>
    <ul class="nav nav-list">
        <li class="nav-header">Available Actions</li>
        <sec:ifNotLoggedIn>
            <li><a href="${createLink(controller: "login", action: "index")}">Login</a></li>
        </sec:ifNotLoggedIn>
        <sec:ifLoggedIn>
            <li><a href="${createLink(controller: "logout", action: "index")}">Logout</a></li>
        </sec:ifLoggedIn>

        <li class="${currentFilter?.equals("job") ? "active" : ""}"><a
                href="${createLink(controller: "job", action: "panel")}">Jobs</a></li>
        <li class="${currentFilter?.equals("jobInstance") ? "active" : ""}"><a
                href="${createLink(controller: "jobInstance", action: "panel")}">Job Instances</a></li>
        <li class="${currentFilter?.equals("jobInstanceEvent") ? "active" : ""}"><a
                href="${createLink(controller: "jobInstanceEvent", action: "panel")}">Job Instance Events</a></li>
        <li class="${currentFilter?.equals("dashboard") ? "active" : ""}"><a
                href="${createLink(controller: "dashboard", action: "index")}">Dashboard</a></li>

        <sec:ifAnyGranted roles="${model.Roles.ROLE_ADMIN}">
            <li class="nav-header">Administration Actions</li>
            <li>
                <a href="${createLink(controller: "job", action: "list")}">Job List</a>
            </li>
            <li>
                <a href="${createLink(controller: "jobInstance", action: "list")}">Job Instance List</a>
            </li>
            <li class="${currentFilter?.equals("config") ? "active" : ""}">
                <a href="${createLink(controller: "configuration", action: "index")}">Job Configuration</a>
            </li>
            <li class="${currentFilter?.equals("application") ? "active" : ""}">
                <a href="${createLink(controller: "application", action: "index")}">Applications</a>
            </li>
            <li class="${currentFilter?.equals("user") ? "active" : ""}">
                <a href="${createLink(controller: "user", action: "index")}">Users</a>
            </li>
            <li class="${currentFilter?.equals("roles") ? "active" : ""}">
                <a href="${createLink(controller: "userRole", action: "index")}">Roles</a>
            </li>
            <li class="${currentFilter?.equals("sysconfig") ? "active" : ""}">
                <a href="${createLink(controller: "systemConfiguration", action: "index")}">System Configuration</a>
            </li>
            <li class="${currentFilter?.equals("notification") ? "active" : ""}">
                <a href="${createLink(controller: "notification", action: "index")}">Mail Notifications</a>
            </li>
        </sec:ifAnyGranted>
    </ul>
</div>



