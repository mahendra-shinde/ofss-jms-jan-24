package com.mahendra.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.openejb.util.Enumerator;

/**
 * Servlet implementation class ReceiveMessageServlet
 */
@WebServlet("/read")
public class ReceiveMessageServlet extends HttpServlet {
	// Message Destination
	@Resource(name="orderQueue")
	private Queue orderQueue;
	
	// Connection Factory
	@Resource(name="conFactory")
	private ConnectionFactory conFactory;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReceiveMessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		
			out.println("Aquiring the JMS Connection ...");
		try {
			Connection con = conFactory.createConnection();
			con.start();
			Session session = con.createSession();
			out.println("Looking for a message");
			MessageConsumer consumer =  session.createConsumer(orderQueue);
			Message message = consumer.receive();			
			if(message != null ) {
				if ( message instanceof TextMessage ) {
					TextMessage txt = (TextMessage) message;
					out.println("Message received : "+ txt.getText());
					Enumeration en = txt.getPropertyNames();
					while(en.hasMoreElements()) {
						Object element = en.nextElement();
						out.println("Attribute : "+ element);
					}
				}
				message.acknowledge(); // Inform message queue, that message is processed
			}
			
			con.close();	
		}catch(JMSException ex) {
			ex.printStackTrace();
		}
	
		out.close();
	}

}
