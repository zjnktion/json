package cn.zjnktion.json.reader;

import cn.zjnktion.json.exception.JsonParseException;

/**
 * Need to read json tokenizer from
 *
 * @author zjnktion
 * @link http://www.cnblogs.com/absfree/p/5502705.html
 */
public interface JsonReader {

    int read() throws JsonParseException;
}
