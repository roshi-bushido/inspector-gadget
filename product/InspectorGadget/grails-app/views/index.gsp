<!doctype html>
<html>
<head>
    <meta name="layout" content="bootstrap"/>
    <title>Welcome to the Academy</title>
</head>

<body>
<div class="row-fluid">
    <aside id="application-status" class="span3">
        <g:render template="/common/actionMenu"/>
    </aside>
    <section id="main" class="span8">
        <div class="hero-unit">
            <h1>Inspector Gadget</h1>

            <p>Bienvenido al sistema de monitoreo de proceso de <em>AGEA</em>.</p>

            <p>Este es un sistema de monitoreo de los procesos internos de las aplicaciones que corren background. A través
            de una simple API los procesos reportan sus actividades y el mismo alerta cuando se encuentren en estado de error.</p>

            <p>Para más información al respecto, consultar la <a href="http://arquitectura-wiki.agea.com.ar/index.php/Inspector_Gadget">wiki</a> del producto.</p>
        </div>
    </section>
</div>
</body>
</html>
