<!DOCTYPE html>
<html>

<head>
	<title>Editar Estudiante</title>

	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/add-student-style.css">	
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>Xideral</h2>
		</div>
	</div>
	
	<div id="container">
		<h3>Editar Estudiante</h3>
		
		<form action="EstudianteControllerServlet" method="GET">
		
			<input type="hidden" name="command" value="EDITAR" />

			<input type="hidden" name="estudianteId" value="${EL_ESTUDIANTE.id}" />
			
			<table>
				<tbody>
					<tr>
						<td><label>Nombre:</label></td>
						<td><input type="text" name="nombre" 
								   value="${EL_ESTUDIANTE.nombre}" /></td>
					</tr>

					<tr>
						<td><label>Apellido:</label></td>
						<td><input type="text" name="apellido" 
								   value="${EL_ESTUDIANTE.apellido}" /></td>
					</tr>

					<tr>
						<td><label>correo:</label></td>
						<td><input type="text" name="correo" 
								   value="${EL_ESTUDIANTE.correo}" /></td>
					</tr>
					
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Guardar" class="save" /></td>
					</tr>
					
				</tbody>
			</table>
		</form>
		
		<div style="clear: both;"></div>
		
		<p>
			<a href="EstudianteControllerServlet">Atras</a>
		</p>
	</div>
</body>

</html>











