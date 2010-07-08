package cs236369.hw5;

import java.util.ArrayList;

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
		//TODO: init the wsdl
		AddEndpoint myAppWSDL= new AddEndpoint();
		myAppWSDL.setUrl("checking");
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
		myAppWSDL.setUrl("checking");
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
		return response.get_return();
		}
		catch (Exception e)
		{
			throw new YelloPageError();
		}
	}
	
	
	
	
}
