package cn.zjnktion.json;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

/**
 * @author zjnktion
 */
public class FileLockTest {

    public static void main(String[] args) {
        Boolean flag = null;
        try {
            flag = lockFile("/data/test");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(flag);

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new FileLockTest().isRepeatStart();
    }

    public static boolean lockFile(String fileName) throws IOException {
        File lockFile = new File(fileName);
        if (!lockFile.exists()) {
            lockFile.createNewFile();
        }
        RandomAccessFile raFile = new RandomAccessFile(lockFile, "rw");
        FileLock fileLock = raFile.getChannel().tryLock();
        return fileLock != null && fileLock.isValid();
    }

    private static final String INSTANCE_FILE = "/data/hkadapter";
    private boolean isRepeatStart() {
        try {
            File lockFile = new File(INSTANCE_FILE);
            if (!lockFile.exists()) {
                lockFile.createNewFile();
            }
            RandomAccessFile raFile = new RandomAccessFile(lockFile, "rw");
            FileLock fileLock = raFile.getChannel().tryLock();
            return fileLock == null || !fileLock.isValid();
        }
        catch (Exception e) {
            throw new IllegalStateException("illegal state");
        }
    }
}
