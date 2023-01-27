package com.luv2code.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class EstudianteDbUtil {

	private DataSource dataSource;

	public EstudianteDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	
public List<Estudiante> getEstudiantes() throws Exception {
		
		List<Estudiante> estudiantes = new ArrayList<>();
		String sql = "SELECT * FROM webxideral.estudiante ORDER BY apellido";
		
		//Se APLICA EL TRY WITH RESOURSE EN TODOS LOS METODOS DE CONEXION A BDD
		try (Connection myConn = dataSource.getConnection();
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery(sql)){
			
			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				int id = myRs.getInt("id");
				String nombre = myRs.getString("nombre");
				String apellido = myRs.getString("apellido");
				String correo = myRs.getString("correo");
				
				// create new student object
				Estudiante planEstudiante = new Estudiante(id, nombre, apellido, correo);
				
				// add it to the list of students
				estudiantes.add(planEstudiante);				
			}
			
			return estudiantes;		
		}		
	}

	

	public void agregarEstudiante(Estudiante elEstudiante) throws Exception {
		// create sql for insert
		   String sql = "insert into webxideral.estudiante "
							   + "(nombre, apellido, correo) "
							   + "values (?, ?, ?)";
		
		   //aplica try with resources
		try (Connection myConn = dataSource.getConnection();
				PreparedStatement myStmt = myConn.prepareStatement(sql)){
			// get db connection
			
			
			// set the param values for the student
			myStmt.setString(1, elEstudiante.getNombre());
			myStmt.setString(2, elEstudiante.getApellido());
			myStmt.setString(3, elEstudiante.getCorreo());
			
			// execute sql insert
			myStmt.execute();
		}
	}


	public Estudiante getEstudiante(String elEstudianteId) throws Exception {

		Estudiante elEstudiante = null;
				
		//create sql to get selected student
				String sql = "select * from webxideral.estudiante where id=?";
				ResultSet myRs = null;
				int estudianteId;
				
				try (Connection myConn = dataSource.getConnection();
						PreparedStatement myStmt= myConn.prepareStatement(sql)){
					// convert student id to int
					estudianteId = Integer.parseInt(elEstudianteId);
					
					// set params
					myStmt.setInt(1, estudianteId);
					
					// execute statement
					myRs = myStmt.executeQuery();
					
					// retrieve data from result set row
					if (myRs.next()) {
						String nombre = myRs.getString("nombre");
						String apellido = myRs.getString("apellido");
						String correo = myRs.getString("correo");
						
						// use the studentId during construction
						elEstudiante = new Estudiante(estudianteId, nombre, apellido, correo);
					}
					else {
						throw new Exception("No se encontro al estudiante : " + estudianteId);
					}				
					
					return elEstudiante;
				}
			}


	public void editarEstudiante(Estudiante elEstudiante) throws Exception {
		
		String sql = "update webxideral.estudiante "
				+ "set nombre=?, apellido=?, correo=?"
				+ "where id=?";
	

		try (Connection myConn = dataSource.getConnection();
				PreparedStatement myStmt = myConn.prepareStatement(sql)){
			
			// set params
			myStmt.setString(1, elEstudiante.getNombre());
			myStmt.setString(2, elEstudiante.getApellido());
			myStmt.setString(3, elEstudiante.getCorreo());
			myStmt.setInt(4, elEstudiante.getId());
			
			// execute SQL statement
			myStmt.execute();
		}
	}

	public void eliminarEstudiante(String elEstudianteId) throws Exception {

		// create sql to delete student
		String sql = "delete from webxideral.estudiante where id=?";
		
		try (Connection myConn = dataSource.getConnection();
				PreparedStatement myStmt = myConn.prepareStatement(sql)){
			// convert student id to int
			int estudianteId = Integer.parseInt(elEstudianteId);
			
			// set params
			myStmt.setInt(1, estudianteId);
			
			// execute sql statement
			myStmt.execute();
		}
	}
}















