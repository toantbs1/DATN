package com.users.app.service.utils;

import java.math.BigDecimal;
import java.math.MathContext;

public class DistanceCalculator {
    private static final BigDecimal EARTH_RADIUS = new BigDecimal("6371.0"); // Bán kính Trái Đất (km)

    // Chuyển đổi độ sang radian (sử dụng BigDecimal)
    public static BigDecimal toRadians(BigDecimal degree) {
        BigDecimal pi = new BigDecimal(Math.PI);
        return degree.multiply(pi).divide(new BigDecimal("180"), MathContext.DECIMAL128);
    }

    // Tính khoảng cách giữa 2 điểm trên mặt đất sử dụng công thức Haversine (sử dụng BigDecimal)
    public static BigDecimal haversineDistance(BigDecimal lat1, BigDecimal lon1, BigDecimal lat2, BigDecimal lon2) {
        BigDecimal lat1Rad = toRadians(lat1);
        BigDecimal lon1Rad = toRadians(lon1);
        BigDecimal lat2Rad = toRadians(lat2);
        BigDecimal lon2Rad = toRadians(lon2);

        BigDecimal deltaLat = lat2Rad.subtract(lat1Rad);
        BigDecimal deltaLon = lon2Rad.subtract(lon1Rad);

        BigDecimal bigDecimalLat = BigDecimal.valueOf(Math.sin(deltaLat.doubleValue() / 2));
        BigDecimal bigDecimalLng = BigDecimal.valueOf(Math.sin(deltaLon.doubleValue() / 2));
        BigDecimal a = bigDecimalLat
            .multiply(bigDecimalLat)
            .add(BigDecimal.valueOf(Math.cos(lat1Rad.doubleValue()))
                .multiply(BigDecimal.valueOf(Math.cos(lat2Rad.doubleValue())))
                .multiply(bigDecimalLng)
                .multiply(bigDecimalLng));

        BigDecimal c = BigDecimal.valueOf(2)
            .multiply(BigDecimal.valueOf(Math.atan2(Math.sqrt(a.doubleValue()), Math.sqrt(1 - a.doubleValue()))));

        return EARTH_RADIUS.multiply(c); // Khoảng cách mặt đất (km)
    }

    // Tính khoảng cách tổng thể (bao gồm độ cao) giữa hai điểm
    public static BigDecimal calculateDistance(BigDecimal lat1, BigDecimal lon1, BigDecimal alt1, BigDecimal lat2, BigDecimal lon2, BigDecimal alt2) {
        // Tính khoảng cách trên mặt đất
        BigDecimal groundDistance = haversineDistance(lat1, lon1, lat2, lon2);

        // Tính độ cao
        BigDecimal heightDifference = alt2.subtract(alt1).divide(new BigDecimal("1000"), MathContext.DECIMAL128);

        // Tính khoảng cách cuối cùng bao gồm độ cao
        return BigDecimal.valueOf(Math.sqrt(groundDistance.doubleValue() * groundDistance.doubleValue() + heightDifference.doubleValue() * heightDifference.doubleValue()));
    }
}
