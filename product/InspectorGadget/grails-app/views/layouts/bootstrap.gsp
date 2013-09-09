<%@ page import="org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes" %>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title><g:layoutTitle default="${meta(name: 'app.name')}"/></title>
		<meta name="description" content="${meta(name: 'app.description')}">
		<meta name="author" content="">

		<meta name="viewport" content="initial-scale = 1.0">

		<!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
		<!--[if lt IE 9]>
			<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->

		<r:require modules="scaffolding"/>

		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">

		<g:layoutHead/>
		<r:layoutResources/>
	</head>

	<body>
		<nav class="navbar navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container-fluid">
					
					<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</a>
					
					<div class="nav-collapse">
                        <div class="nav-user" >
                            <sec:ifNotLoggedIn>
                              <g:link controller="login" action="auth">Login</g:link>
                            </sec:ifNotLoggedIn>
                            <sec:ifLoggedIn>
                              Welcome back, <sec:username/>.
                              <g:link controller="logout" action="index">Logout</g:link>
                            </sec:ifLoggedIn>
                        </div>
					</div>
				</div>
			</div>
		</nav>

		<div class="container-fluid">
            <g:layoutBody/>
			<hr>
			<footer>
				<p>&copy; Inspector Gadget ${Calendar.getInstance().get(Calendar.YEAR)}</p>
			</footer>
		</div>
		<r:layoutResources/>
	</body>
    %{--<script type="text/javascript" src="http://localhost/clickheat/js/clickheat.js"></script>--}%
    %{--<script type="text/javascript">--}%
    %{--<!----}%
        %{--clickHeatSite = 'www.gadget.agea.com.ar';--}%
        %{--clickHeatGroup = '${controllerName}-${actionName}';--}%
        %{--clickHeatQuota = 20;--}%
        %{--clickHeatServer = 'http://localhost/clickheat/click.php';--}%
        %{--initClickHeat();--}%
    %{--//-->--}%
    %{--</script>--}%

</html>