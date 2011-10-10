package com.example.jrestart.service;

import com.example.jrestart.data.Employee;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/employees")
public class EmployeeService {

    private static Map<String, Employee> employees = new HashMap<String, Employee>();

    static {
        Employee employee1 = new Employee();
        employee1.setEmployeeId("1");
        employee1.setEmployeeName("Alex");
        employee1.setJob("Software Engineer");
        employees.put(employee1.getEmployeeId(), employee1);

        Employee employee2 = new Employee();
        employee2.setEmployeeId("2");
        employee2.setEmployeeName("John");
        employee2.setJob("Business Analyst");
        employees.put(employee2.getEmployeeId(), employee2);
    }

    /*
     * RETRIEVE method(s) (using HTTP GET)
     */

    @GET
    @Path("/hello")
    @Produces("text/plain")
    public String hello() {
        return "Hello World";
    }

    @GET
    @Path("/echo/{message}")
    @Produces("text/plain")
    public String echo(@PathParam("message") String message) {
        return message;
    }

    @GET
    @Path("/employees")
    @Produces("application/xml")
    public List<Employee> listEmployees() {
        return new ArrayList<Employee>(employees.values());
    }

    @GET
    @Path("/employee/{employeeid}")
    @Produces("application/xml")
    public Employee getEmployee(@PathParam("employeeid") String employeeId) {
        return employees.get(employeeId);
    }

    @GET
    @Path("/json/employees/")
    @Produces("application/json")
    public List<Employee> listEmployeesJSON() {
        return new ArrayList<Employee>(employees.values());
    }

    @GET
    @Path("/json/employee/{employeeid}")
    @Produces("application/json")
    public Employee getEmployeeJSON(@PathParam("employeeid") String employeeId) {
        return employees.get(employeeId);
    }

    /*
     * CREATE method(s) (using HTTP POST)
     */

    @POST
	@Path("/post")
	@Consumes("application/json")
	public Response addEmployeeJSON(Employee employee) {
		String result = "Employee added to the list : " + employee;
		return Response.status(201).entity(result).build();
	}

    /*
     * UPDATE method(s) (using HTTP PUT)
     */

    /*
     * DELETE method(s) (using HTTP DELETE)
     */

}
