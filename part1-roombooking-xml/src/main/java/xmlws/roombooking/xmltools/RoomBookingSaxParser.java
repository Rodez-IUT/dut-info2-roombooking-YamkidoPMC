package xmlws.roombooking.xmltools;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import xmlws.roombooking.xmltools.samples.RoomBookingBasicSaxParser;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.text.*;
import java.util.Date;

public class RoomBookingSaxParser implements RoomBookingParser {

    /**
     * Parse an xml file provided as an input stream
     *
     * @param inputStream the input stream corresponding to the xml file
     */
    public RoomBooking parse(InputStream inputStream) {
        RoomBooking roomBooking = new RoomBooking();
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            SAXParser saxParser = spf.newSAXParser();
            saxParser.parse(inputStream, new RoomBookingBasicHandler(roomBooking));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roomBooking;
    }

    private class RoomBookingBasicHandler extends DefaultHandler {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        RoomBooking roomBooking;
        String localNameTemp;

        public RoomBookingBasicHandler(RoomBooking roomBooking) {
            this.roomBooking = roomBooking;
        }

        public void startElement(String namespaceURI,
                                 String localName,
                                 String qName,
                                 Attributes atts)
                throws SAXException {
            System.out.println(localName);
            localNameTemp = localName;
        }

        public void characters(char ch[], int start, int length)
                throws SAXException {
            String chaine = new String(ch, start, length);
            if(chaine.charAt(0) != '\n') {
                switch (localNameTemp) {
                    case "label":
                        roomBooking.setRoomLabel(chaine);
                        break;
                    case "username":
                        roomBooking.setUsername(chaine);
                        break;
                    case "startDate":
                        System.out.println(chaine);
                        try {
                            roomBooking.setStartDate(sdf.parse(chaine));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "endDate":
                        try {
                            roomBooking.setEndDate(sdf.parse(chaine));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        }
    }
}
