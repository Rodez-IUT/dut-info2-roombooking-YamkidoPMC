package xmlws.roombooking.xmltools;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.text.SimpleDateFormat;

public class RoomBookingDomParser implements RoomBookingParser{

    @Override
    public RoomBooking parse(InputStream inputStream) {
        try {
            RoomBooking roomBooking = new RoomBooking();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(inputStream);
            roomBooking.setRoomLabel(doc.getElementsByTagName("label").item(0).getTextContent());
            roomBooking.setUsername(doc.getElementsByTagName("username").item(0).getTextContent());
            roomBooking.setEndDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(doc.getElementsByTagName("" +
                    "endDate").item(0).getTextContent()));
            roomBooking.setStartDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(doc.getElementsByTagName("" +
                    "startDate").item(0).getTextContent()));
            return roomBooking;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
