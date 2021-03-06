
package com.gwang.rpc.webservice.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "Business", targetNamespace = "http://webservice.rpc.gwang.com/client")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Business {


    /**
     * 
     * @param arg0
     * @return
     *     returns com.gwang.rpc.webservice.client.SerializeDemo
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://webservice.rpc.gwang.com/client/Business/echoRequest", output = "http://webservice.rpc.gwang.com/client/Business/echoResponse")
    public SerializeDemo echo(
        @WebParam(name = "arg0", partName = "arg0")
        String arg0);

}
