package com.demo;

import org.openehealth.ipf.commons.ihe.xds.core.responses.Status;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.query.AdhocQueryRequest;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.query.AdhocQueryResponse;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rim.ExtrinsicObjectType;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rim.ObjectFactory;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rim.RegistryObjectListType;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rim.SlotType1;
import org.openehealth.ipf.commons.ihe.xds.core.stub.ebrs30.rim.ValueListType;
import org.openehealth.ipf.commons.ihe.xds.iti18.Iti18PortType;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBElement;

import java.math.BigInteger;

/**
 * Created by remcoo on 20/02/2017.
 */
@Service
public class RegistryMock implements Iti18PortType {

	@Override
	public AdhocQueryResponse documentRegistryRegistryStoredQuery(AdhocQueryRequest body) {
		AdhocQueryResponse response = new AdhocQueryResponse();

		response.setStatus(Status.SUCCESS.getOpcode30());
		response.setTotalResultCount(BigInteger.ONE);

		SlotType1 repositoryUniqueIdSlot = new SlotType1();
		repositoryUniqueIdSlot.setName("repositoryUniqueId");
		ValueListType repositoryUniqueIdValueList = new ValueListType();
		repositoryUniqueIdValueList.getValue().add("urn:oid:1.2.3.4.5.6");
		repositoryUniqueIdSlot.setValueList(repositoryUniqueIdValueList);

		ObjectFactory objectFactory = new ObjectFactory();
		ExtrinsicObjectType extrinsicObjectType = objectFactory.createExtrinsicObjectType();
		extrinsicObjectType.getSlot().add(repositoryUniqueIdSlot);
		JAXBElement<ExtrinsicObjectType> extrinsicObject = objectFactory.createExtrinsicObject(extrinsicObjectType);

		RegistryObjectListType registryObjectList = new RegistryObjectListType();
		registryObjectList.getIdentifiable().add(extrinsicObject);

		return response;
	}
}
