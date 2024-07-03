package com.sunbeam.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunbeam.daos.CandidateDao;
import com.sunbeam.daos.CandidateDaoImpl;
import com.sunbeam.pojos.Candidate;

@WebServlet("/result")
public class ResultServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doMethod(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doMethod(req, resp);
	}

	protected void doMethod(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Candidate> list = new ArrayList<Candidate>();

		try (CandidateDao cdao = new CandidateDaoImpl()) {
			list = cdao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}

		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Result</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<table border='1'>");
		out.println("<thead>");
		out.println("<th>Id</th>");
		out.println("<th>Name</th>");
		out.println("<th>Party</th>");
		out.println("<th>Votes</th>");
		out.println("<th>Action</th>");
		out.println("</thead>");
		out.println("<tbody>");

		for (Candidate c : list) {
			out.println("<tr>");
			out.printf("<td>%d</td>\n", c.getId());
			out.printf("<td>%s</td>\n", c.getName());
			out.printf("<td>%s</td>\n", c.getParty());
			out.printf("<td>%d</td>\n", c.getVotes());
			out.printf(
					"<td><a href='editcand?id=%d'><img width='24' height='24' src='src/images/edit.jpg'/></a> <a href='delcand?id=%d'><img width='24' height='24' src='/src/images/delete.jpg'/></a></td>\n",
					c.getId(), c.getId());

			out.println("</tr>");
		}
		out.println("</tbody>");
		out.println("</table>");
		String msg = (String) req.getAttribute("message");
		if (msg != null)
			out.println("<br/><br/>  " + msg + "  <br/><br/>");
		out.println("<a href=announcement.html>Annoucement</a>");
		out.println("<a href='index.html'>Sign Out</a>");
		
		out.println("</body>");
		out.println("</html>");
	}

}
