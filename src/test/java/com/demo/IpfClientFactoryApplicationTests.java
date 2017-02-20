package com.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openehealth.ipf.commons.ihe.xds.XDS_B;
import org.openehealth.ipf.commons.ihe.xds.core.XdsClientFactory;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.query.AdhocQueryRequest;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.query.ResponseOptionType;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rim.AdhocQueryType;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rim.SlotListType;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rim.SlotType1;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rim.ValueListType;
import org.openehealth.ipf.commons.ihe.xds.iti18.Iti18PortType;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {IpfClientFactoryApplication.class, TestConfig.class})
public class IpfClientFactoryApplicationTests {

	@LocalServerPort
	private int port;

	@Test
	public void iti18AuditFailureTest() {

		XdsClientFactory clientFactory = new XdsClientFactory(XDS_B.Interactions.ITI_18.getWsTransactionConfiguration(), "http://localhost:" + port + "/services/registry", XDS_B.Interactions.ITI_18.getWsTransactionConfiguration().getClientAuditStrategy(),
			null,
			null,
			Collections.singletonList(new XUAFeature()), //remove XUA feature to prevent stacktrace
			null);
		Iti18PortType client = (Iti18PortType) clientFactory.getClient();

		AdhocQueryRequest request = new AdhocQueryRequest();
		request.setAdhocQuery(new AdhocQueryType());
		request.getAdhocQuery().setHome("urn:oid:1.2.3.4.5.6");

		SlotType1 patientIdSlot = new SlotType1();
		patientIdSlot.setName("$XDSDocumentEntryPatientId");
		ValueListType patientIdList = new ValueListType();
		patientIdList.getValue().add("1");
		patientIdSlot.setValueList(patientIdList);

		request.setRequestSlotList(new SlotListType());
		request.getRequestSlotList().getSlot().add(patientIdSlot);

		SlotType1 entryStatusSlot = new SlotType1();
		entryStatusSlot.setName("$XDSDocumentEntryStatus");
		ValueListType entryStatusList = new ValueListType();
		entryStatusList.getValue().add("('urn:oasis:names:tc:ebxml-regrep:StatusType:Approved')");
		entryStatusSlot.setValueList(entryStatusList);

		request.setResponseOption(new ResponseOptionType());
		request.getResponseOption().setReturnType("LeafClass");
		request.getResponseOption().setReturnComposedObjects(true);

		client.documentRegistryRegistryStoredQuery(request);


	}

}
