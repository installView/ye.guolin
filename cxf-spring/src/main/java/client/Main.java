package client;

import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import server.domain.Address;
import server.util.XmlUtil;

import javax.xml.namespace.QName;

/**
 * 作者：ye.guolin
 * 日期：2020/01/13
 * 描述：
 */
public class Main {

    public static void main(String[] args) {
        Address address = new Address();
        address.setAddress("安徽省安庆市宿松县");
        address.setCountry("中国");
        address.setProvince("安徽");
        address.setCity("安庆");
        address.setIdentifier(1);
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        try{
            org.apache.cxf.endpoint.Client client = dcf.createClient("http://localhost:8080/cxf/Address?wsdl");
            // 这个方法参数为wsdl中的targetNamespace，需要调用的方法名
            QName name = new QName("http://service.server/", "setAddress");
            Object[] objects = client.invoke(name, XmlUtil.javaBean2Xml(address,Address.class));
            System.out.println(objects[0].toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
