package com.centurylink.xprsr.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import com.centurylink.xprsr.service.IUploadService;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class UploadService implements IUploadService {
    JSch jsch = new JSch();
    String command = "pwd";
    String scriptPath;
    String SFTPHOST = "lamare24.centurytel.com";
    int SFTPPORT = 22;
    String SFTPUSER = "prcoper";
    String SFTPPASS = "trypet1";
    String SFTPWORKINGDIR = "aks/bkup/DCORDER/dc_orders/forGhost";

    Session session = null;
    Channel channel = null;
    Channel channelExec = null;
    ChannelSftp channelSftp = null;

    public void uploadScript(String scriptName) {
        this.scriptPath = scriptName;
        Thread t = new Thread(new ThreadClass());
        t.start();        
    }

    class ThreadClass implements Runnable {

        @Override
        public void run() {
            try {
                JSch jsch = new JSch();
                session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
                session.setPassword(SFTPPASS);
                java.util.Properties config = new java.util.Properties();
                config.put("StrictHostKeyChecking", "no");
                session.setConfig(config);
                session.connect();
                channel = session.openChannel("sftp");
                channelExec = session.openChannel("shell");
                OutputStream ops = channelExec.getOutputStream();
                PrintStream ps = new PrintStream(ops, true);
                channel.connect();
                channelExec.connect();

                channelSftp = (ChannelSftp) channel;
                channelSftp.cd(SFTPWORKINGDIR);
                File file = new File(scriptPath);
                FileInputStream fiStream = new FileInputStream(file);
                channelSftp.put(fiStream, "tcktinpt.txt");

                ps.println("cd " + SFTPWORKINGDIR);
                ps.println("sed -e 's/^M//g' tcktinpt.txt > tcktinpt1.txt");
                /* ps.println("tr -d '\r' < tcktinpt.txt > tcktinpt1.txt"); */
                ps.println("mv tcktinpt1.txt tcktinpt.txt");
                ps.println("./main.sh");

                Thread.sleep(60000);

                ps.close();
                ops.close();
                fiStream.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            } finally {
                channelExec.disconnect();
                channelSftp.disconnect();
                channel.disconnect();
                session.disconnect();
            }
        }
    }
}
