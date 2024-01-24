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


@WebServlet("/test")
public class TestQueue1 extends HttpServlet {
	
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
    public TestQueue1() {
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
		
		String msg = request.getParameter("msg");
		if(msg == null || msg.trim().length() == 0 ) {
			msg = "Hello world";
		}
			out.println("Aquiring the JMS Connection ...");
		try {
			Connection con = conFactory.createConnection();
			out.println("Creating the JMS Session");
			Session session = con.createSession();
			
			out.println("Creating the Producer");
			MessageProducer producer = session.createProducer(orderQueue);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			
			out.println("Preparing text-message");
			TextMessage message = session.createTextMessage();
			message.setIntProperty("MessageID", 1234);
			message.setStringProperty("PIN", "400703");
			message.setText(msg);
			out.println("Sending message ...");
			producer.send(message);
			
			out.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
