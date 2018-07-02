package com.addit.ift.Activity;

import android.os.StrictMode;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by user on 6/29/2016.
 */

public class Ftp {

    public boolean upLoad(String IP, String userName, String Password, String sourceFileName, String destinationFileName) {
        FTPClient ftpClient = new FTPClient();
        try {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            ftpClient.connect(IP);
            ftpClient.login(userName, Password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            InputStream inputStream = new FileInputStream(sourceFileName);
            boolean completed = ftpClient.storeFile(destinationFileName, inputStream);

            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
            if (completed) {
                return true;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
