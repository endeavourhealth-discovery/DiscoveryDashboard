
package AdastraWS;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.7-b01 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "AdastraWebServiceSoap", targetNamespace = "http://tempuri.org/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface AdastraWebServiceSoap {


    /**
     * 
     * @param password
     * @param data
     * @param userName
     * @return
     *     returns AdastraWS.AddUpdateCaseResult
     */
    @WebMethod(operationName = "AddUpdateCase", action = "http://tempuri.org/webservices/adastra/AddUpdateCase")
    @WebResult(name = "AddUpdateCaseResult", targetNamespace = "http://tempuri.org/webservices/adastra")
    @RequestWrapper(localName = "AddUpdateCase", targetNamespace = "http://tempuri.org/webservices/adastra", className = "AdastraWS.AddUpdateCase")
    @ResponseWrapper(localName = "AddUpdateCaseResponse", targetNamespace = "http://tempuri.org/webservices/adastra", className = "AdastraWS.AddUpdateCaseResponse")
    public AddUpdateCaseResult addUpdateCase(
        @WebParam(name = "userName", targetNamespace = "http://tempuri.org/webservices/adastra")
        String userName,
        @WebParam(name = "password", targetNamespace = "http://tempuri.org/webservices/adastra")
        String password,
        @WebParam(name = "data", targetNamespace = "http://tempuri.org/webservices/adastra")
        AdastraWS.AddUpdateCase.Data data);

}