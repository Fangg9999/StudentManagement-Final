package util;

public class GradingUtil {
    public static String getRank(double score) {
        if (score >= 8.5) return "Giỏi";
        if (score >= 7.0) return "Khá";
        if (score >= 5.5) return "Trung bình";
        return "Yếu";
    }
}