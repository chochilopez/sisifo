package muni.eolida.sisifo.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Slf4j
public class Helper {
    public static HttpHeaders httpHeaders (String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "message");
        headers.set("message", message);

        return headers;
    }

    /*
    DateTimeFormatter
    ofLocalizedDate(dateStyle)	Formatter with date style from the locale	'2011-12-03'
    ofLocalizedTime(timeStyle)	Formatter with time style from the locale	'10:15:30'
    ofLocalizedDateTime(dateTimeStyle)	Formatter with a style for date and time from the locale	'3 Jun 2008 11:05:30'
    ofLocalizedDateTime(dateStyle,timeStyle)	Formatter with date and time styles from the locale	'3 Jun 2008 11:05'
    BASIC_ISO_DATE	Basic ISO date	'20111203'
    ISO_LOCAL_DATE	ISO Local Date	'2011-12-03'
    ISO_OFFSET_DATE	ISO Date with offset	'2011-12-03+01:00'
    ISO_DATE	ISO Date with or without offset	'2011-12-03+01:00'; '2011-12-03'
    ISO_LOCAL_TIME	Time without offset	'10:15:30'
    ISO_OFFSET_TIME	Time with offset	'10:15:30+01:00'
    ISO_TIME	Time with or without offset	'10:15:30+01:00'; '10:15:30'
    ISO_LOCAL_DATE_TIME	ISO Local Date and Time	'2011-12-03T10:15:30'
    ISO_OFFSET_DATE_TIME	Date Time with Offset	2011-12-03T10:15:30+01:00'
    ISO_ZONED_DATE_TIME	Zoned Date Time	'2011-12-03T10:15:30+01:00[Europe/Paris]'
    ISO_DATE_TIME	Date and time with ZoneId	'2011-12-03T10:15:30+01:00[Europe/Paris]'
    ISO_ORDINAL_DATE	Year and day of year	'2012-337'
    ISO_WEEK_DATE	Year and Week	2012-W48-6'
    ISO_INSTANT	Date and Time of an Instant	'2011-12-03T10:15:30Z'
    RFC_1123_DATE_TIME	RFC 1123 / RFC 822	'Tue, 3 Jun 2008 11:05:30 GMT'
    */
    public static String localDateTimeToString(LocalDateTime localDateTime, String format) {
        if (format.isEmpty())
            format = "HH:mm:ss dd-MM-yyyy";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);

        return dateTimeFormatter.format(ZonedDateTime.of(localDateTime, ZoneId.of("America/Argentina/Buenos_Aires")));
    }

    public static LocalDateTime zonedDateTimeToLocalDateTime(ZonedDateTime date) {
        try {
            ZoneId zoneId = ZoneId.of("America/Argentina/Buenos_Aires");
            ZonedDateTime zonedDateTime = date.withZoneSameInstant(zoneId);
            return zonedDateTime.toLocalDateTime();
        } catch (Exception e) {
            log.error("Error converting ZonedDateTime to LocalDateTime. Exception: " + e);
            return null;
        }
    }

    public static LocalDateTime stringToLocalDateTime(String date, String format) {
        if (format.isEmpty())
            format = "HH:mm:ss dd-MM-yyyy";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);

        return LocalDateTime.parse(date, dateTimeFormatter);
    }

    public static LocalDateTime getNow(String zone){
        if (zone.isEmpty())
            zone = "America/Argentina/Buenos_Aires";
        ZoneId zoneId = ZoneId.of(zone);

        return LocalDateTime.now(zoneId);
    }

    public static Double getNDecimal(Double number, int decimals){
        BigDecimal bigDecimal = new BigDecimal(number).setScale(decimals, RoundingMode.UP);
        return bigDecimal.doubleValue();
    }

    public static Boolean checkCellphoneNumber(String phoneNumber){
        // regex for e.164 phone number
        Pattern pattern = Pattern.compile("^\\+[1-9]\\d{1,14}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.find();
    }

    public static Integer getInteger(String stringNumber){
        try {
            if (stringNumber.isEmpty())
                return null;
            return Integer.parseInt(stringNumber);
        }catch(Exception e) {
            return null;
        }
    }

    public static Long getLong(String stringNumber){
        try {
            if (stringNumber.isEmpty())
                return null;
            return Long.parseLong(stringNumber);
        }catch(Exception e) {
            return null;
        }
    }

    public static Double getDouble(String stringNumber){
        try {
            if (stringNumber.isEmpty())
                return null;
            return Double.parseDouble(stringNumber);
        }catch(Exception e) {
            return null;
        }
    }

    public static Boolean isNullString (String aString) {
        return aString == null;
    }

    public static Boolean isEmptyString (String aString) {
        if (isNullString(aString)) return true;
        return aString.isEmpty();
    }
}
