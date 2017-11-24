package webservice;

import org.endeavourhealth.adastrareceiver.soapws.database.PersistenceManager;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService()
public class EndeavourImplementation {

    @WebMethod
    public String resetDBCache() {

        String result = "Cache reset";
        try {
            PersistenceManager.ClearEntityManager();
        } catch (Exception e) {
            result = "Error resetting Cache : " + e.getMessage();
        }
        System.out.println("Endeavour webservice cache reset result :" + result);
        return result;
    }
}
