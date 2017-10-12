import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class DemoSftp {

    public static void main(String[] args) throws JSchException, SftpException, IOException {

        String hostname = "hostname";
        String login = "login";
        String password = "password";
        String directory = ".";

        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");

        JSch ssh = new JSch();
        Session session = ssh.getSession("admin", "localhost", 22);
        session.setConfig(config);
        session.setPassword("");
        session.connect();
        Channel channel = session.openChannel("sftp");
        channel.connect();

        ChannelSftp sftp = (ChannelSftp) channel;
        sftp.cd(directory);
        Vector files = sftp.ls("*");
        System.out.printf("Found %d files in dir %s%n", files.size(), directory);

        for (Object file1 : files) {
            ChannelSftp.LsEntry file = (ChannelSftp.LsEntry)file1;
            if (file.getAttrs().isDir()) {
                continue;
            }
            System.out.printf("Reading file : %s%n", file.getFilename());
            BufferedReader bis = new BufferedReader(new InputStreamReader(sftp.get(file.getFilename())));
            String line = null;
            while ((line = bis.readLine()) != null) {
                System.out.println(line);
            }
            bis.close();
        }

        channel.disconnect();
        session.disconnect();

    }

}