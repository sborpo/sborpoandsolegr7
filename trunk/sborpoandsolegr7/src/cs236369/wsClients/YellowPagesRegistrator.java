package cs236369.wsClients;

import java.util.ArrayList;

import cs236369.hw5.RegistrationServiceStub;
import cs236369.hw5.RegistrationServiceStub.AddEndpoint;
import cs236369.hw5.RegistrationServiceStub.AddEndpointResponse;
import cs236369.hw5.RegistrationServiceStub.DeleteEndpoint;
import cs236369.hw5.RegistrationServiceStub.DeleteEndpointResponse;
import cs236369.hw5.RegistrationServiceStub.GetRegisteredEndpointsResponse;

public class YellowPagesRegistrator {
	
	
	public static class YelloPageError extends Exception{}
	public static void registerApplicationToYellow() throws YelloPageError
	{
		try{
		RegistrationServiceStub stub = new RegistrationServiceStub();
		AddEndpoint myAppWSDL= new AddEndpoint();
		myAppWSDL.setUrl("http://localhost:8080/sborpoandsolegr7/services/searchWS?wsdl");
		AddEndpointResponse reponse=stub.addEndpoint(myAppWSDL);
		System.out.println("This is the response: "+reponse.get_return()+".");
		}
		catch (Exception e)
		{
			throw new YelloPageError();
		}
	}
	
	public static void removeApplicationFromYellow() throws YelloPageError
	{
		try{
		RegistrationServiceStub stub = new RegistrationServiceStub();
		DeleteEndpoint myAppWSDL= new DeleteEndpoint();
		myAppWSDL.setUrl("http://localhost:8080/sborpoandsolegr7/services/searchWS?wsdl");
		DeleteEndpointResponse reponse=stub.deleteEndpoint(myAppWSDL);
		System.out.println("This is the response: "+reponse.get_return()+".");
		}
		catch (Exception e)
		{
			throw new YelloPageError();
		}
	}
	
	public static String[] getServicesFromYellow() throws YelloPageError
	{
		try{
		RegistrationServiceStub stub = new RegistrationServiceStub();
		GetRegisteredEndpointsResponse response=stub.getRegisteredEndpoints();
		String [] rsp= response.get_return();
		return rsp;
		}
		catch (Exception e)
		{
			throw new YelloPageError();
		}
	}
	
	
	
	
}
