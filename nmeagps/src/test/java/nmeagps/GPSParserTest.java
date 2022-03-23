package nmeagps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZoneId;

import org.junit.jupiter.api.Test;

import nmeagps.data.Trans;

public class GPSParserTest {
  static final String[] DATA = {
      "$GPTXT,01,01,01,ANTENNA OPEN*25\r\n",
      "$GNGGA,234212.000,3722.09149,N,12158.33784,W,1,10,2.0,2.1,M,0.0,M,,*61\r\n",
      "$GNGLL,3722.09149,N,12158.33784,W,234212.000,A,A*54\r\n",
      "$GPGSA,A,3,02,12,24,25,29,,,,,,,,2.7,2.0,1.8*37\r\n",
      "$BDGSA,A,3,16,24,26,35,44,,,,,,,,2.7,2.0,1.8*2E\r\n",
      "$GPGSV,3,1,09,02,85,114,26,05,10,164,,06,39,045,,12,74,338,37*7B\r\n",
      "$GPGSV,3,2,09,19,17,066,,20,29,143,26,24,34,216,20,25,34,313,38*78\r\n",
      "$GPGSV,3,3,09,29,16,269,31*43\r\n",
      "$BDGSV,3,1,09,06,05,326,27,16,06,324,27,24,13,320,29,26,53,290,40*60\r\n",
      "$BDGSV,3,2,09,29,,,29,35,66,284,41,36,22,152,,39,,,24*62\r\n",
      "$BDGSV,3,3,09,44,17,251,31*53\r\n",
      "$GNRMC,234212.000,A,3722.09149,N,12158.33784,W,0.00,23.08,200322,,,A*5B\r\n",
      "$GNVTG,23.08,T,,M,0.00,N,0.00,K,A*1A\r\n",
      "$GNZDA,234212.000,20,03,2022,00,00*4F\r\n",
  };

  @Test
  void testGeneral() {
    GPSParser parser = new GPSParser();

    parser.setHandler_GGA(d -> {
      assertEquals("GN", d.talker);
      assertEquals("37\u00b0 22.0915' N", d.lat.toString());
      assertEquals("121\u00b0 58.3378' W", d.lng.toString());
      assertEquals("Standard position service", Trans.GGA.fixQuality.get(d.fixQuality));
    });

    parser.setHandler_GLL(d -> {
      assertEquals("Autonomous", Trans.RMC.mode.get(d.mode));
    });

    parser.setHandler_GSA(d -> {
      assertEquals("Automatic", Trans.GSA.mode1.get(d.mode1));
      assertEquals("3D", Trans.GSA.mode2.get(d.mode2));
      assertEquals(2.7, d.pdop);
      assertEquals(d.talker.equals("GP") ? 24 : 26, d.satellitesUsed.get(2));
    });

    parser.setHandler_GSV(d -> {
      assertEquals(9, d.satelliteViews.size());
      if (d.talker.equals("GP")) {
        assertEquals("{id:29,elevation:16,azimuth:269,snr:31}",
            d.satelliteViews.get(8).toString());
      } else {
        assertEquals("{id:44,elevation:17,azimuth:251,snr:31}",
            d.satelliteViews.get(8).toString());
      }
    });

    parser.setHandler_RMC(d -> {
      assertEquals("2022-03-20T23:42:12Z[UTC]",
          d.dateTime.toInstant().atZone(ZoneId.of("UTC")).toString());
      assertEquals("37\u00b0 22.0915' N", d.lat.toString());
      assertEquals("121\u00b0 58.3378' W", d.lng.toString());
      assertEquals(23.08, d.trackMadeGood);
      assertEquals("Autonomous", Trans.RMC.mode.get(d.mode));
      assertEquals("Active", Trans.RMC.status.get(d.status));
      assertEquals("Unknown", Trans.RMC.navStatus.get(d.navStatus));
    });

    parser.setHandler_VTG(d -> {
      assertEquals(23.08, d.trueCourse);
      assertEquals(Double.NaN, d.magneticCourse);
    });

    parser.setHandler_ZDA(d -> {
      assertEquals("2022-03-20T23:42:12Z[UTC]",
          d.dateTime.toInstant().atZone(ZoneId.of("UTC")).toString());
    });

    for (String s : DATA) {
      parser.parse(s);
    }
  }

  @Test
  void testAggregator() {
    SimpleGPSAggregator agg = new SimpleGPSAggregator();
    for (String s : DATA) {
      agg.parse(s);
    }

    assertEquals("37\u00b0 22.0915' N", agg.getResult("GN").lat.toString());
    assertEquals("121\u00b0 58.3378' W", agg.getResult("GN").lng.toString());
    assertEquals(5, agg.getResult("BD").satellitesUsed.size());
    assertEquals(9, agg.getResult("GP").satelliteViews.size());
  }
}
