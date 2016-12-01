package cn.zjnktion.json.writer;

/**
 * @author zjnktion
 */
public interface JsonWriter {

    void write(boolean bool);

    void write(byte b);

    void write(char c);

    void write(short s);

    void write(int i);

    void write(float f);

    void write(long l);

    void write(double d);

    void write(String str);

    void writeNull();

    String toString();
}
