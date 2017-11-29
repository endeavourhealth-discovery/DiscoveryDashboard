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
            ms.setMessagePayload(getMessagePayload());
            ms.setReceivedDateTime(Timestamp.from(ZonedDateTime.now(ZoneOffset.UTC).toInstant()));
            ms.setSource(1);
            ms.setStatus((byte) 0);

            MessageStoreEntity.storeMessage(ms);
        } catch ( Exception e ) {

        }
    }

    private void processMessageData(AddUpdateCase.Data data) throws Exception {

        //JAXBContext payloadContext = JAXBContext.newInstance(AdastraCaseDataExport.class);

        //payloadContext.createUnmarshaller().unmarshal((Node)data.getContent());



    }

    private String getMessagePayload() throws Exception {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<adastraCaseDataExport xsi:schemaLocation=\"http://www.adastra.com/dataExport AdastraCaseDataExport.xsd\" xmlns=\"http://www.adastra.com/dataExport\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "\t<adastraCaseReference>66c3bd5d-7a00-45d1-9db0-b5b9fd9dc740</adastraCaseReference>\n" +
                "\t<adastraCaseNumber>10475</adastraCaseNumber>\n" +
                "\t<patient>\n" +
                "\t\t<firstName>Matt</firstName>\n" +
                "\t\t<lastName>Stibbs</lastName>\n" +
                "\t\t<dateOfBirth dataOfBirthType=\"Exact\">\n" +
                "\t\t\t<dobValue>2011-05-23</dobValue>\n" +
                "\t\t</dateOfBirth>\n" +
                "\t\t<gender>Male</gender>\n" +
                "\t\t<nationalNumber nationalNumberStatus=\"Confirmed\">\n" +
                "\t\t\t<number>1111111111</number>\n" +
                "\t\t</nationalNumber>\n" +
                "\t\t<address addressType=\"Home\">\n" +
                "\t\t\t<line1>Bolberry Down</line1>\n" +
                "\t\t\t<line2>4 Crossway</line2>\n" +
                "\t\t\t<locality>Kennington</locality>\n" +
                "\t\t\t<town>Ashford</town>\n" +
                "\t\t\t<county>Kent</county>\n" +
                "\t\t\t<postcode>TN248SB</postcode>\n" +
                "\t\t</address>\n" +
                "\t\t<address addressType=\"CurrentLocation\">\n" +
                "\t\t\t<line1/>\n" +
                "\t\t\t<line2>5 Clover Rise</line2>\n" +
                "\t\t\t<locality>Stanford</locality>\n" +
                "\t\t\t<town>Canterbury</town>\n" +
                "\t\t\t<county>Kent</county>\n" +
                "\t\t\t<postcode>CT53AD</postcode>\n" +
                "\t\t</address>\n" +
                "\t\t<phone numberType=\"Home\">\n" +
                "\t\t\t<number>01234 567890</number>\n" +
                "\t\t\t<extension>132323</extension>\n" +
                "\t\t</phone>\n" +
                "\t\t<phone numberType=\"Mobile\">\n" +
                "\t\t\t<number>07777 789987</number>\n" +
                "\t\t\t<extension/>\n" +
                "\t\t</phone>\n" +
                "\t\t<gpRegistration>\n" +
                "\t\t\t<registrationStatus>Registered</registrationStatus>\n" +
                "\t\t\t<gpNationalCode>G1234567</gpNationalCode>\n" +
                "\t\t\t<surgeryNationalCode> V8199</surgeryNationalCode>\n" +
                "\t\t\t<surgeryPostcode>CT56JH</surgeryPostcode>\n" +
                "\t\t</gpRegistration>\n" +
                "\t</patient>\n" +
                "\t<presentingCondition>\n" +
                "\t\t<symptoms>Patient has a severe earache.</symptoms>\n" +
                "\t\t<comments>Patient seems to be quite distressed.</comments>\n" +
                "\t</presentingCondition>\n" +
                "\t<priority codeScheme=\"String\">\n" +
                "\t\t<code>4</code>\n" +
                "\t\t<description>Routine</description>\n" +
                "\t</priority>\n" +
                "\t<caseType codeScheme=\"String\">\n" +
                "\t\t<code>A</code>\n" +
                "\t\t<description>Advice</description>\n" +
                "\t</caseType>\n" +
                "\t<caseStatus>COMPLETE</caseStatus>\n" +
                "\t<activeDate>2012-05-30T09:30:10Z</activeDate>\n" +
                "\t<completedDate>2012-05-30T10:15:10Z</completedDate>\n" +
                "\t<returnPhone>\n" +
                "\t\t<number>01234555555</number>\n" +
                "\t\t<extension>444444</extension>\n" +
                "\t</returnPhone>\n" +
                "\t<callerPhone>\n" +
                "\t\t<number>01234666666</number>\n" +
                "\t\t<extension>333333</extension>\n" +
                "\t</callerPhone>\n" +
                "\t<latestAppointment>\n" +
                "\t\t<appointmentTime>2012-05-30T09:45:00Z</appointmentTime>\n" +
                "\t\t<location>Walk-In Centre</location>\n" +
                "\t\t<status>Arrived</status>\n" +
                "\t</latestAppointment>\n" +
                "\t<questions>A block of free text will be displayed here listing questions and answers asked during the case. Line breaks will be included.</questions>\n" +
                "\t<consultation>\n" +
                "\t\t<startTime>2012-05-30T10:46:32Z</startTime>\n" +
                "\t\t<endTime>2012-05-30T10:56:23Z</endTime>\n" +
                "\t\t<consultationBy providerType=\"Doctor\">\n" +
                "\t\t\t<name>Dr John Smith</name>\n" +
                "\t\t</consultationBy>\n" +
                "\t\t<location>Walk-In Centre</location>\n" +
                "\t\t<summary>A free text block of text detailing all information captured during the first consultation.</summary>\n" +
                "\t\t<medicalHistory>A block of text summarising all medical history information known at the time of the first consultaiton.</medicalHistory>\n" +
                "\t\t<eventOutcome codeScheme=\"String\">\n" +
                "\t\t\t<code>ABC</code>\n" +
                "\t\t\t<description>Description of ABC code</description>\n" +
                "\t\t</eventOutcome>\n" +
                "\t\t<eventOutcome codeScheme=\"String\">\n" +
                "\t\t\t<code>DEF</code>\n" +
                "\t\t\t<description>Description of DEF code</description>\n" +
                "\t\t</eventOutcome>\n" +
                "\t\t<clinicalCode codeScheme=\"String\">\n" +
                "\t\t\t<code>1348.</code>\n" +
                "\t\t\t<description>Description of 1348. clinical code</description>\n" +
                "\t\t</clinicalCode>\n" +
                "\t\t<clinicalCode codeScheme=\"String\">\n" +
                "\t\t\t<code>137B.</code>\n" +
                "\t\t\t<description>Description of 137B. clinical code</description>\n" +
                "\t\t</clinicalCode>\n" +
                "\t</consultation>\n" +
                "\t<consultation>\n" +
                "\t\t<startTime>2012-05-30T09:46:32Z</startTime>\n" +
                "\t\t<endTime>2012-05-30T09:56:23Z</endTime>\n" +
                "\t\t<consultationBy providerType=\"Doctor\">\n" +
                "\t\t\t<name>Dr John Smith</name>\n" +
                "\t\t</consultationBy>\n" +
                "\t\t<location>Walk-In Centre</location>\n" +
                "\t\t<summary>A free text block of text detailing all information captured during the second consultation.</summary>\n" +
                "\t\t<medicalHistory>A block of text summarising all medical history information known at the time of the second consultaiton.</medicalHistory>\n" +
                "\t\t<eventOutcome codeScheme=\"String\">\n" +
                "\t\t\t<code>CBA</code>\n" +
                "\t\t\t<description>Description of ABC code</description>\n" +
                "\t\t</eventOutcome>\n" +
                "\t\t<eventOutcome codeScheme=\"String\">\n" +
                "\t\t\t<code>FED</code>\n" +
                "\t\t\t<description>Description of DEF code</description>\n" +
                "\t\t</eventOutcome>\n" +
                "\t\t<clinicalCode codeScheme=\"String\">\n" +
                "\t\t\t<code>8431.</code>\n" +
                "\t\t\t<description>Description of 1348. clinical code</description>\n" +
                "\t\t</clinicalCode>\n" +
                "\t\t<clinicalCode codeScheme=\"String\">\n" +
                "\t\t\t<code>731B.</code>\n" +
                "\t\t\t<description>Description of 137B. clinical code</description>\n" +
                "\t\t</clinicalCode>\n" +
                "\t</consultation>\n" +
                "\t<specialNote>\n" +
                "\t\t<reviewDate>2012-06-01</reviewDate>\n" +
                "\t\t<text>This patient has a very nasty dog.</text>\n" +
                "\t</specialNote>\n" +
                "\t<specialNote>\n" +
                "\t\t<reviewDate>2012-06-02</reviewDate>\n" +
                "\t\t<text>This patient has an incredibly pleasant dog too.</text>\n" +
                "\t</specialNote>\n" +
                "\t<outcome codeScheme=\"String\">\n" +
                "\t\t<code>OUTCOME1</code>\n" +
                "\t\t<description>Description of OUTCOME1 code</description>\n" +
                "\t</outcome>\n" +
                "\t<outcome codeScheme=\"String\">\n" +
                "\t\t<code>OUTCOME2</code>\n" +
                "\t\t<description>Description of OUTCOME2 code</description>\n" +
                "\t</outcome>\n" +
                "</adastraCaseDataExport>\n";
    }
}