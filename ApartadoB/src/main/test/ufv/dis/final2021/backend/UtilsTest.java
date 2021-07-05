package ufv.dis.final2021.backend;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class UtilsTest extends TestCase {

    Utils utils = null;
    String noFacilitado;
    String below;
    String above;
    ArrayList<GeoIP> list = null;

    @Before
    public void setUp() throws Exception {
        utils = new Utils();
        noFacilitado = ""; //Aqui ponemos la ip que queramos para testear
        above = "255.255.255.256";
        below = "0.0.0.-1";
        list = utils.getFile();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test(expected = Exception.class)
    public void testLocateIP() throws Exception {
        utils.locateIP(noFacilitado, list);
        //fail("Esto es un error");
        //assertTrue("Esto si pasa", true);
    }

    @Test(expected = Exception.class)
    public void paramAbove() throws Exception {
        utils.locateIP(above, list);
    }

    @Test(expected = Exception.class)
    public void paramBelow() throws Exception {
        utils.locateIP(below, list);
    }
}