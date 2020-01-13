package server.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * 作者：ye.guolin
 * 日期：2020/01/13
 * 描述：
 */
public class XmlUtil {

    /**
     * 作者: ye.guolin
     * 日期: 2020/01/10 15:29
     * 描述: 将java Bean对象转化成xml字符串
     * obj为对象，tClass为obj的class对象
     */
    public static String javaBean2Xml(Object obj, Class tClass) {
        try {
            JAXBContext context = JAXBContext.newInstance(tClass);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 作者: ye.guolin
     * 日期: 2020/01/10 15:42
     * 描述: 将xml字符串转换成实体类
     */
    public static Object xml2JavaBean(String xmlStr,Class tClass){
        try{
            JAXBContext context = JAXBContext.newInstance(tClass);
            //反序列化
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader reader = new StringReader(xmlStr);
            Object obj = unmarshaller.unmarshal(reader);
            return obj;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
