package com.mahendra.app;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.jms.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PublishMessageServlet
 */
@WebServlet("/publish")
public class PublishMessageServlet extends HttpServlet {
	
	@Resource(name="newsTopic")
	private Topic newsTopic;
	
	
	@Resource(name="topicCon")
	private TopicConnectionFactory factory;
	
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PublishMessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String msg = request.getParameter("msg");
		if(msg == null || msg.isEmpty()) {
			msg = "Hello World !";
		}
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		out.println("<ol type=disc>");
		
		try {
			Connection con = factory.createTopicConnection();
			out.println("<li>Connection established !</li>");
			Session session = con.createSession();
			out.println("<li>New JMS Session created !</li>");
			MessageProducer producer =  session.createProducer(newsTopic);
			out.println("<li>New Message Producer created !</li>");
			TextMessage txt = session.createTextMessage();
			out.println("<li>New Text Message created !</li>");
			txt.setText(msg);
			producer.send(txt);
			out.println("<li>Message published !</li>");
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("<li>Error : "+e.getMessage()+"</li>");
		}
		
		
		out.println("</ol>");
	}

}
