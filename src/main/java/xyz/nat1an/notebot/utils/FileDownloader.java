package xyz.nat1an.notebot.utils;

import net.minecraft.client.MinecraftClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownloader {
    public static Path downloadFile(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // 응답 코드 확인
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.err.println("파일 다운로드 실패: HTTP " + responseCode);
                return null;
            }

            // 파일명 가져오기 (Content-Disposition 확인)
            String fileName = getFileNameFromConnection(connection, fileUrl);

            // 저장할 경로 설정
            Path saveDir = Paths.get(MinecraftClient.getInstance().runDirectory.getPath(), "notebot/songs/downloads");
            if (!Files.exists(saveDir)) {
                Files.createDirectories(saveDir); // 폴더 생성
            }
            Path savePath = saveDir.resolve(fileName);

            // 파일 스트림 처리
            try (InputStream inputStream = connection.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(savePath.toFile())) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                System.out.println("file downloaded: " + savePath);
                return saveDir.getParent().relativize(savePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getFileNameFromConnection(HttpURLConnection connection, String fileUrl) {
        String fileName = null;

        // Content-Disposition 헤더에서 파일명 추출
        String contentDisposition = connection.getHeaderField("Content-Disposition");
        if (contentDisposition != null && contentDisposition.contains("filename=")) {
            fileName = contentDisposition.split("filename=")[1].replaceAll("\"", "").trim();
        }

        // 헤더에서 못 찾으면 URL에서 파일명 추출
        if (fileName == null || fileName.isEmpty()) {
            fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        }

        return fileName;
    }
}
