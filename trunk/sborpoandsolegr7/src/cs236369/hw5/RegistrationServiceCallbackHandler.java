
/**
 * RegistrationServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */

    package cs236369.hw5;

    /**
     *  RegistrationServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class RegistrationServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public RegistrationServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public RegistrationServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getRegisteredEndpoints method
            * override this method for handling normal response from getRegisteredEndpoints operation
            */
           public void receiveResultgetRegisteredEndpoints(
                    cs236369.hw5.RegistrationServiceStub.GetRegisteredEndpointsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getRegisteredEndpoints operation
           */
            public void receiveErrorgetRegisteredEndpoints(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteEndpoint method
            * override this method for handling normal response from deleteEndpoint operation
            */
           public void receiveResultdeleteEndpoint(
                    cs236369.hw5.RegistrationServiceStub.DeleteEndpointResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteEndpoint operation
           */
            public void receiveErrordeleteEndpoint(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addEndpoint method
            * override this method for handling normal response from addEndpoint operation
            */
           public void receiveResultaddEndpoint(
                    cs236369.hw5.RegistrationServiceStub.AddEndpointResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addEndpoint operation
           */
            public void receiveErroraddEndpoint(java.lang.Exception e) {
            }
                


    }
    