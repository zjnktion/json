package cn.zjnktion.json.writer;

import cn.zjnktion.json.util.IConstants;

/**
 * @author zjnktion
 */
public class StringBuilderWriter implements JsonWriter {

    private final StringBuilder builder = new StringBuilder();

    @Override
    public void write(boolean bool) {
        if (bool) {
            builder.append("true");
        }
        else {
            builder.append("false");
        }
    }

    @Override
    public void write(byte b) {
        builder.append(IConstants.BYTE_PREFIX);

        String hexString = Integer.toHexString(b & 0xff);
        if (hexString.length() < 2) {
            builder.append("0");
        }
        builder.append(hexString);
    }

    @Override
    public void write(char c) {
        builder.append(c);
    }

    @Override
    public void write(short s) {
        builder.append(s);
    }

    @Override
    public void write(int i) {
        builder.append(i);
    }

    @Override
    public void write(float f) {
        builder.append(f);
    }

    @Override
    public void write(long l) {
        builder.append(l);
    }

    @Override
    public void write(double d) {
        builder.append(d);
    }

    @Override
    public void write(String str) {
        builder.append(str);
    }

    @Override
    public void writeNull() {
        builder.append("null");
    }

    public String toString() {
        return builder.toString();
    }
}
