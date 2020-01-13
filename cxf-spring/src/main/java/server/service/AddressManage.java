package server.service;


import server.domain.Address;
import server.util.XmlUtil;

import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * 作者：ye.guolin
 * 日期：2020/01/08
 * 描述：
 */
@WebService
public class AddressManage {

    public String setAddress(String requestXml){
        Address address = (Address) XmlUtil.xml2JavaBean(requestXml,Address.class);
        System.out.println(address.toString());
        return requestXml;
    }
}
