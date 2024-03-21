package net.oliste;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        var a = ZonedDateTime.now(ZoneId.of("UTC"));
        var b = a.plusDays(1);

        var inter = SingleTimeInterval.of(a, b, "Test1");

        var result = inter.combine().split(a.plusHours(4));


    }
}
