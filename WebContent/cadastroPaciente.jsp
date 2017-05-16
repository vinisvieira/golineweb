<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>GoLine</title>

    <!-- Bootstrap Core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Catamaran:100,200,300,400,500,600,700,800,900" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Muli" rel="stylesheet">

    <!-- Plugin CSS -->
    <link rel="stylesheet" href="vendor/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="vendor/simple-line-icons/css/simple-line-icons.css">
    <link rel="stylesheet" href="vendor/device-mockups/device-mockups.min.css">

    <!-- Theme CSS -->
    <link href="css/new-age.min.css" rel="stylesheet">
    <link href="css/mycss.css" rel="stylesheet">
    <link href="img/css.css" rel="stylesheet">

   

</head>

<body id="page-top">

    <nav id="mainNav" class="navbar ">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span> Menu <i class="fa fa-bars"></i>
                </button>
                <a class="navbar-brand page-scroll" href="index.html">Home</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                 <li>
                        <a class="page-scroll" href="cadastroPaciente.jsp">Cadastre-se</a>
                    </li>
                    
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>
     <div class="panel-body ">
     		<div class="col-lg-12">
                    <h1 class="page-header">Cadastro Cliente</h1>
             </div>
              <form action="CadastroPaciente" method="POST">
 					<div class="col-md-6">
                      <div class="form-group">
                          <label>Nome*:</label>
                          <input name="nome" class="form-control" placeholder="Digite seu nome completo">
                      </div>
                       <div class="form-group">
                          <label>Email*:</label>
                          <input  name="email" class="form-control" type="email" placeholder="Digite seu Email ">
                      </div>
                       <div class="form-group col-md-4">
                          <label>Telefone*:</label>
                          <input  name="telefone" class="form-control" placeholder="Digite seu telefone ">
                      </div>
                       <div class="form-group col-md-4">
                          <label>CPF*:</label>
                          <input  name="cpf" class="form-control" placeholder="Digite seu CPF ">
                      </div>
            	  </div>
            	  <div class="col-md-6">
                 
                       <div class="form-group">
                          <label>Login*:</label>
                          <input  name="login" class="form-control" placeholder="Digite seu login ">
                      </div>
                       <div class="form-group">
                          <label>Senha*:</label>
                          <input  name="senha" class="form-control" type="password" placeholder="Digite sua senha ">
                      </div>
                      <button  name="cadastrarPaciente" type="submit" class="btn btn-default">Cadastrar</button>
                  </div>
             </form>
              
              
	</div>
 <footer>
        <div class="container">
            <p>&copy; 2016 GoLine.</p>
            <ul class="list-inline">
                <li>
                    <a href="#">Privacy</a>
                </li>
                <li>
                    <a href="#">Terms</a>
                </li>
                <li>
                    <a href="#">FAQ</a>
                </li>
            </ul>
        </div>
    </footer>

    <!-- jQuery -->
    <script src="vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>

    <!-- Theme JavaScript -->
    <script src="js/new-age.min.js"></script>

</body>
</html>