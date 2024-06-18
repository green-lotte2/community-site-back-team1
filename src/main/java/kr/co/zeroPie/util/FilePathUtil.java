package kr.co.zeroPie.util;

import java.net.URI;
import java.net.URISyntaxException;

// URL에서 파일 시스템 경로를 추출하는 메소드
public class FilePathUtil {
    public static String extractFilePathFromURL(String fileURL) {
        try {
            URI uri = new URI(fileURL);
            String path = uri.getPath();
            String[] pathParts = path.split("/onepie/images/orgArtImage");
            if (pathParts.length > 1) {
                return "uploads/orgArtImage" + pathParts[1];
            } else {
                throw new RuntimeException("Invalid file path: " + fileURL);
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid file URL: " + fileURL, e);
        }
    }
}
