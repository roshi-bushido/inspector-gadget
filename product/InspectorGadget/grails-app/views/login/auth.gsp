<html>
<head>
	<meta name='layout' content='bootstrap'/>
	<title><g:message code="springSecurity.login.title"/></title>
    <style type="text/css">
    /* Override some defaults */
    html, body {
      background-color: #eee;
    }
    body {
      padding-top: 40px;
    }
    .container {
      width: 350px !important;
      margin-top: 30px;
    }

    /* The white background content wrapper */
    .container > .content {
      background-color: #fff;
      padding: 20px;
      margin: 0 -20px;
      -webkit-border-radius: 10px 10px 10px 10px;
         -moz-border-radius: 10px 10px 10px 10px;
              border-radius: 10px 10px 10px 10px;
      -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.15);
         -moz-box-shadow: 0 1px 2px rgba(0,0,0,.15);
              box-shadow: 0 1px 2px rgba(0,0,0,.15);
    }

    .login-form {
    margin-left: 65px;
    }

    legend {
    margin-right: -50px;
    font-weight: bold;
    color: #404040;
    }

    </style>
</head>

<body>
<div class="container">
    <div class="content">
        <div class="row">
            <div class="login-form">
                <h2><g:message code="springSecurity.login.header"/></h2>
                <g:if test='${flash.message}'>
              		<div class='login_message'>${flash.message}</div>
              	</g:if>
                <form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
                    <fieldset>
                        <div class="clearfix">
                          <label for='username'><g:message code="springSecurity.login.username.label"/>:</label>
                          <input type='text' class='text_' name='j_username' id='username'/>
                        </div>
                        <div class="clearfix">
                          <label for='password'><g:message code="springSecurity.login.password.label"/>:</label>
                            <input type='password' class='text_' name='j_password' id='password'/>
                        </div>
                        <div class="clearfix">
                          <input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>/>
                          <label for='remember_me'><g:message code="springSecurity.login.remember.me.label"/></label>
                        </div>
                        <button class="btn btn-primary" type="submit"> ${message(code: "springSecurity.login.button")}</button>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
<script type='text/javascript'>
	<!--
	(function() {
		document.forms['loginForm'].elements['j_username'].focus();
	})();
	// -->
</script>
</body>
</html>
