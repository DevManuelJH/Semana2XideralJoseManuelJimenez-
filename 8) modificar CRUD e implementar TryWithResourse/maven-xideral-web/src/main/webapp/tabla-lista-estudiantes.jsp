<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
	<title>Academia Xideral</title>
	
	<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>Candidatos JAVA Xideral</h2>
		</div>
	</div>

	<div id="container">
	
		<div id="content">
		
			<!-- put new button: Add Student -->
			
			<input type="button" value="Agregar" 
				   onclick="window.location.href='tabla-agregar-estudiantes.jsp'; return false;"
				   class="add-student-button"
			/>
			
			<table>
			
				<tr>
					<th>Nombre</th>
					<th>Apellido</th>
					<th>Correo</th>
					<th>Opciones</th>
				</tr>
				
				<c:forEach var="tempStudent" items="${LISTA_ESTUDIANTES}">
					
					<!-- set up a link for each student -->
					<c:url var="tempLink" value="EstudianteControllerServlet">
						<c:param name="command" value="CARGAR" />
						<c:param name="estudianteId" value="${tempStudent.id}" />
					</c:url>

					<!--  set up a link to delete a student -->
					<c:url var="deleteLink" value="EstudianteControllerServlet">
						<c:param name="command" value="ELIMINAR" />
						<c:param name="estudianteId" value="${tempStudent.id}" />
					</c:url>
																		
					<tr>
						<td> ${tempStudent.nombre} </td>
						<td> ${tempStudent.apellido} </td>
						<td> ${tempStudent.correo} </td>
						<td> 
							<a href="${tempLink}">Actualizar</a> 
							 | 
							<a href="${deleteLink}"
							onclick="if (!(confirm('¿Desea eliminar el Registro?'))) return false">
							Eliminar</a>	
						</td>
					</tr>
				
				</c:forEach>
				
			</table>
		
		</div>
	
	</div>
</body>


</html>








