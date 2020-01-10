package com.dlc.common.utils;

public class LocationUtils {

    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }


    /**
     * 根据两个位置的经纬度，来计算两地的距离（单位为KM）
     * 参数为String类型
     * @return
     */
    public static String getDistance(String lat1Str, String lng1Str, String lat2Str, String lng2Str) {

        Double lat1 = Double.parseDouble(lat1Str);
        Double lng1 = Double.parseDouble(lng1Str);
        Double lat2 = Double.parseDouble(lat2Str);
        Double lng2 = Double.parseDouble(lng2Str);

        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double difference = radLat1 - radLat2;
        double mdifference = rad(lng1) - rad(lng2);
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(mdifference / 2), 2)));
        distance = distance * EARTH_RADIUS;
        distance = Math.round(distance * 10000) / 10000;
        String distanceStr = distance+"";
//        distanceStr = distanceStr.substring(0, distanceStr.indexOf("."));

        return distanceStr;
    }

    public static double getDistanceNew(String long1, String lati1, String long2, String lati2) {
        Double lng1 = Double.parseDouble(long1);
        Double lat1 = Double.parseDouble(lati1);
        Double lng2 = Double.parseDouble(long2);
        Double lat2 = Double.parseDouble(lati2);

        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s*1000;
        return s;
    }

    public static double GetDistance(String long1, String lati1, String long2, String lati2){
        Double lng1 = Double.parseDouble(long1);
        Double lat1 = Double.parseDouble(lati1);
        Double lng2 = Double.parseDouble(long2);
        Double lat2 = Double.parseDouble(lati2);

        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 1000);
        return s;
    }

    /**
     * test
     * @param args
     */
//    public static void main(String[] args){
//        String distance = getDistance("113.733086","22.992339","113.729444","22.993757");
//        System.out.println("Distance is:"+distance);
//
//        Double distanceNew = GetDistance("0","0","113.733086","22.992339");
//        System.out.println("DistanceNew is:"+distanceNew);
//    }
}
