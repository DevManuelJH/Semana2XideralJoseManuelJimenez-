package com.luv2code.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/EstudianteControllerServlet")
public class EstudianteControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private EstudianteDbUtil estudianteDbUtil;
	
	@Resource(name="jdbc/webxideral")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		// create our student db util ... and pass in the conn pool / datasource
		try {
			estudianteDbUtil = new EstudianteDbUtil(dataSource);
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");
			
			// if the command is missing, then default to listing students
			if (theCommand == null) {
				theCommand = "LISTA";
			}
			
			// route to the appropriate method
			switch (theCommand) {
			
			case "LISTA":
				listaEstudiantes(request, response);
				break;
				
			case "AGREGAR":
				agregarEstudiante(request, response);
				break;
				
			case "CARGAR":
				cargarEstudiante(request, response);
				break;
				
			case "EDITAR":
				editarEstudiante(request, response);
				break;
			
			case "ELIMINAR":
				eliminarEstudiante(request, response);
				break;
				
			default:
				listaEstudiantes(request, response);
			}
				
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
		
	}

	private void eliminarEstudiante(HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		// read student id from form data
		String elEstudianteId = request.getParameter("estudianteId");
		
		// delete student from database
		estudianteDbUtil.eliminarEstudiante(elEstudianteId);
		
		// send them back to "list students" page
		listaEstudiantes(request, response);
	}

	private void editarEstudiante(HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		// read student info from form data
		int id = Integer.parseInt(request.getParameter("estudianteId"));
		String nombre = request.getParameter("nombre");
		String apellido = request.getParameter("apellido");
		String correo = request.getParameter("correo");
		
		// create a new student object
		Estudiante elEstudiante = new Estudiante(id, nombre, apellido, correo);
		
		// perform update on database
		estudianteDbUtil.editarEstudiante(elEstudiante);
		
		// send them back to the "list students" page
		listaEstudiantes(request, response);
		
	}

	private void cargarEstudiante(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {

		// read student id from form data
		String elEstudianteId = request.getParameter("estudianteId");
		
		// get student from database (db util)
		Estudiante elEstudiante = estudianteDbUtil.getEstudiante(elEstudianteId);
		
		// place student in the request attribute
		request.setAttribute("EL_ESTUDIANTE", elEstudiante);
		
		// send to jsp page: update-student-form.jsp
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/tabla-editar-estudiantes.jsp");
		dispatcher.forward(request, response);		
	}

	private void agregarEstudiante(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read student info from form data
		String nombre = request.getParameter("nombre");
		String apellido = request.getParameter("apellido");
		String correo = request.getParameter("correo");		
		
		// create a new student object
		Estudiante elEstudiante = new Estudiante(nombre, apellido, correo);
		
		// add the student to the database
		estudianteDbUtil.agregarEstudiante(elEstudiante);
				
		// send back to main page (the student list)
		listaEstudiantes(request, response);
	}

	private void listaEstudiantes(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {

		// get students from db util
		List<Estudiante> estudiantes = estudianteDbUtil.getEstudiantes();
		
		// add students to the request
		request.setAttribute("LISTA_ESTUDIANTES", estudiantes);
				
		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/tabla-lista-estudiantes.jsp");
		dispatcher.forward(request, response);
	}

}













