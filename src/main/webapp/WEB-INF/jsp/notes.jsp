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

    <title>Notes de ${user.getName()}</title>
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
                    <c:when test="${not empty user.getId()}">
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
                    <li class="nav-item">
                        <a class="nav-link" href="/register" tabindex="-1" aria-disabled="true">Registra't</a>
                    </li>
                </c:otherwise>

                </c:choose>
            </div>

        </article>
    </nav>
</header>

<main class="container">
    <h1 class="display-4">Benvingut, ${user.getName()}</h1>
    <p>Aquesta es la teva pàgina de notes</p>
    <a href="/newNote">Crea una nota</a>
    <c:if test="${empty notes}">
        <p>No tens notes, accedeix al formulari per a crear una</p>
    </c:if>
    <article>
        <c:if test="${not empty errD}">
            <div class="alert alert-danger" role="alert">
                ${errD}
            </div>
        </c:if>

        <c:if test="${not empty err}">
            <div class="alert alert-danger" role="alert">
                    ${err}
            </div>
        </c:if>

        <c:if test="${not empty sharedErr}">
            <div class="alert alert-danger" role="alert">
                    ${sharedErr}
            </div>
        </c:if>

        <c:if test="${not empty delSucs}">
            <div class="alert alert-success" role="alert">
                    ${delSucs}
            </div>
        </c:if>

        <c:if test="${not empty sharedOk}">
            <div class="alert alert-success" role="alert">
                    ${sharedOk}
            </div>
        </c:if>

    </article>

    <div class="container">
        <div class="d-flex flex-wrap justify-content-between">
            <c:forEach var="c" items="${notes}">

                <div class="card mt-3 mb-3" style="width: 25rem;">
                    <div class="card-body">
                        <h5 class="card-title">${c.getTitle()}</h5>
                        <p class="card-text">${c.getContent()}</p>

                        <a href="/editNote?noteId=${c.getNoteId()}" class="btn btn-primary">Edita la nota</a>

                        <a href="/viewNote?noteId=${c.getNoteId()}" class="btn btn-success">Veure la nota</a>

                        <a href="/deleteNote?noteId=${c.getNoteId()}" class="btn btn-danger">Elimina la nota</a>

                    </div>
                    <div class="card-footer">
                        <form action="/shareNote" method="post">
                            <input type="hidden" value="${c.getNoteId()}" name="noteId">
                            <input type="hidden" value="${c.getOwner()}" name="ownerId">

                            <select name="usersList" id="usersList">
                                <option value="" selected>-- Selecciona un usuari --</option>

                                <c:forEach var="us" items="${users}">
                                    <option value="${us.getId()}">${us.getEmail()}</option>
                                </c:forEach>

                            </select>

                            <button type="submit" class="btn btn-warning">Comparteix la nota</button>
                            
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>

        <c:if test="${not empty notes}">
            <nav aria-label="Paginació de notes" class="d-flex justify-content-center">
                <ul class="pagination">
                    <c:if test="${pageId != 1}">
                        <li class="page-item"><a class="page-link"
                                                 href="/notes?total=${pagines}&page=${pageId-1}">Previous</a>
                        </li>
                    </c:if>

                    <c:forEach begin="1" end="${pagines}" var="i">
                        <c:choose>
                            <c:when test="${pageId eq i}">
                                <li class="page-item active"><a class="page-link">
                                        ${i} <span class="sr-only">(current)</span></a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item"><a class="page-link"
                                                         href="/notes?total=${total}&page=${i}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${pageId lt pagines}">
                        <li class="page-item"><a class="page-link"
                                                 href="notes?total=${total}&page=${pageId+1}">Next</a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </c:if>


    </div>



</main>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>
