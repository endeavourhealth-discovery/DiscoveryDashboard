package org.endeavourhealth.adastrareceiver.api.endpoints;

import adastraWS.AdastraWebServiceSoap;
import adastraWS.AddUpdateCase;
import adastraWS.AddUpdateCaseResult;

import javax.jws.WebService;

@WebService(serviceName = "addUpdateCase", portName = "AdastraWebServiceSoap", endpointInterface = "adastraWS.AdastraWebServiceSoap",
        targetNamespace = "http://tempuri.org/", wsdlLocation = "WEB-INF/wsdl/IOWLogica.wsdl")
public class AddUpdateCaseImpl implements AdastraWebServiceSoap {

    @Override
    public AddUpdateCaseResult addUpdateCase(String userName, String password, AddUpdateCase.Data data) {
        System.out.println("boom!!");
        return null;
    }
}
