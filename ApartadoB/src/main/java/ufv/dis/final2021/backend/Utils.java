package ufv.dis.final2021.backend;

import com.google.gson.Gson;
import com.googlecode.gentyref.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Utils {

    //TODO leer fichero json
    public static ArrayList<GeoIP> getFile(){
        Gson gson = new Gson();

        ArrayList<GeoIP> listIP = null;
        BufferedReader br =  null;
        try{
            br = new BufferedReader(new FileReader("LocalizaIP.json"));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        listIP = gson.fromJson(br, new TypeToken<ArrayList<GeoIP>>(){}.getType());
        return listIP;
    }

    //TODO buscar ip fichero
    public GeoIP locateIP(String ip, ArrayList<GeoIP> listGeoIP) throws Exception{
        if (ip == ""){
            throw new Exception("Ip vacia");
        }

        long longIP = Dot2LongIP(ip);
        long above = Dot2LongIP(ip);
        long below = Dot2LongIP("0.0.0.-1");

        if (longIP >= (long)(4294967040.0)){
            throw new Exception("Ip fuera de rango por arriba");
        }
        if (longIP < (long)(0.0)){
            throw new Exception("Ip fuera de rango por debajo");
        }

        boolean found = false;
        GeoIP foundItem = null;
        int i = 0;

        while(!found && i < listGeoIP.size()){
            GeoIP item = listGeoIP.get(i);
            if(longIP >= item.getIp_from() && longIP <= item.getIp_to()){
                found = true;
                foundItem = item;
            }
            i++;
        }

        if (foundItem != null){
            return foundItem;
        }
        else {
            return new GeoIP();
        }
    }

    public static String longToIp(long ip) {

        StringBuilder result = new StringBuilder(15);
        for (int i = 0; i < 4; i++) {
            result.insert(0, Long.toString(ip & 0xff));
            if (i < 3) {
                result.insert(0, '.');
            }
            ip = ip >> 8;
        }
        return result.toString();
    }

    public static Long Dot2LongIP(String dottedIP) {

        String[] addrArray = dottedIP.split("\\.");
        long num = 0;
        for (int i=0;i<addrArray.length;i++) {
            int power = 3-i;
            num += ((Integer.parseInt(addrArray[i]) % 256) * Math.pow(256,power));
        }
        return num;
    }
}
