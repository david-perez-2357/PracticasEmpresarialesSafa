package core.utils;

import api.models.Person;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import java.io.File;
import java.util.List;

public class XmlFileManager {

    public static void exportPeople(List<Person> personas, String filePath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(PeopleWrapper.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        PeopleWrapper wrapper = new PeopleWrapper();
        wrapper.setPeople(personas);

        marshaller.marshal(wrapper, new File(filePath));
    }

    public static List<Person> importPeople(String filePath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(PeopleWrapper.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        PeopleWrapper wrapper = (PeopleWrapper) unmarshaller.unmarshal(new File(filePath));
        return wrapper.getPeople();
    }
}
