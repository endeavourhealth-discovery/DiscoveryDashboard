package webservice;

import AdastraWS.AdastraWebServiceSoap;
import AdastraWS.AddUpdateCase;
import AdastraWS.AddUpdateCaseResult;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService()
public class AdastraImplementation implements AdastraWebServiceSoap {

    @WebMethod
    public AddUpdateCaseResult addUpdateCase(String userName, String password, AddUpdateCase.Data data) {
        System.out.println("hello");
        return null;
    }
}