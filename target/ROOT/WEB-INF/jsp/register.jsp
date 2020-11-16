<%--
  Created by IntelliJ IDEA.
  User: Raul
  Date: 09/11/2020
  Time: 17:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="es">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <title>Hello, user!</title>
</head>
<body>
<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <article class="container">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="/">Inici</a>
                    </li>
                    <c:choose>
                        <c:when test="${not empty userId}">
                                <li class="nav-item active dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        Notes
                                    </a>
                                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                        <a class="dropdown-item" href="/notes">Les meves notes</a>
                                        <a class="dropdown-item" href="/notesCompartides">Notes compartides amb jo</a>
                                    </div>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="/user" tabindex="-1" aria-disabled="true">El meu perfil</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="/logout" tabindex="-1" aria-disabled="true">Tanca sessió</a>
                                </li>
                            </ul>

                            <form class="form-inline my-2 my-lg-0">
                                <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
                                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Cerca per títol</button>
                            </form>
                        </c:when>

                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="/login" tabindex="-1" aria-disabled="true">Incia sessió</a>
                            </li>
                            <li class="nav-item active">
                                <a class="nav-link" href="/register" tabindex="-1" aria-disabled="true">Registra't</a>
                            </li>
                        </c:otherwise>

                    </c:choose>
            </div>

        </article>
    </nav>
</header>

<main class="container">
    <article class="d-flex justify-content-center mt-3">
        <article class="col-12 col-md-8 d-flex flex-column">
            <h1 class="display-1 text-center">Registra't</h1>
            <small class="text-center">Es gratis</small>
        </article>

        <article>
            <article class="col-12 col-md-8">
                <c:if test="${not empty err}">
                    <div class="alert alert-danger" role="alert">
                            ${err}
                    </div>
                </c:if>
            </article>
        </article>

    </article>

    <article class="d-flex justify-content-center mt-5">
        <form class="col-12 col-md-8" action="/register" method="post">
            <div class="form-group">
                <label for="username">Nom d'usuari:</label>
                <input type="text" class="form-control" id="username" name="username" aria-describedby="usernamehelp" placeholder="El teu nom d'usuari">
            </div>
            <div class="form-group">
                <label for="email">El teu email:</label>
                <input type="email" class="form-control" id="email" name="email" aria-describedby="emailHelp" placeholder="El teu email">
            </div>
            <div class="form-group">
                <label for="password">Contrasenya:</label>
                <input type="password" class="form-control" id="password" placeholder="La teva contrasenya" name="password">
                <small id="emailHelp" class="form-text text-muted">
                    La contrasenya ha de tenir al manco: <br>
                    <ul>
                        <li>1 número</li>
                        <li>1 lletra majúsucla</li>
                        <li>1 lletra minúscula</li>
                        <li>1 caracter especial ( @ $ & # )</li>
                        <li>Ha de tenir entre 5 y 16 caracters</li>
                    </ul>
                </small>
            </div>
            <div class="form-group">
                <label for="password2">Repeteix la teva contrasenya:</label>
                <input type="password" class="form-control" id="password2" placeholder="Repeteix la teva contrasenya" name="password2">
            </div>
            <button type="submit" class="btn btn-primary">Registra't</button>
        </form>
    </article>
</main>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>
