package webservice;

import AdastraWS.AdastraWebServiceSoap;
import AdastraWS.AddUpdateCase;
import AdastraWS.AddUpdateCaseResult;
import org.endeavourhealth.adastrareceiver.soapws.database.models.MessageStoreEntity;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@WebService()
public class AdastraImplementation implements AdastraWebServiceSoap {

    @WebMethod
    public AddUpdateCaseResult addUpdateCase(String userName, String password, AddUpdateCase.Data data)  {
        System.out.println("hello");
        saveMessage(data);
        return null;
    }

    private void saveMessage(AddUpdateCase.Data data) {
        try {
            MessageStoreEntity ms = new MessageStoreEntity();
            processMessageData(data);
            ms.setMessagePayload("test");
            ms.setReceivedDateTime(Timestamp.from(ZonedDateTime.now(ZoneOffset.UTC).toInstant()));
            ms.setSource(1);
            ms.setStatus((byte) 0);

            MessageStoreEntity.storeMessage(ms);
        } catch ( Exception e ) {

        }
    }

    private void processMessageData(AddUpdateCase.Data data) throws Exception {


    }
}