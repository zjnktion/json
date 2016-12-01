package cn.zjnktion.json.reader.tokenizer;

import cn.zjnktion.json.exception.JsonParseException;
import cn.zjnktion.json.reader.JsonReader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zjnktion
 */
public class Tokenizer {

    private JsonReader reader;

    private List<Token> tokens = new ArrayList<Token>();
    private boolean isUnread = false; // 标记当前read是否需要从缓存中读取，每次true的read都会被重置会false
    private int saved; // 缓存起来的一个char
    private int current; // 当前读到的char

    public Tokenizer(JsonReader reader) {
        this.reader = reader;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void tokenize() throws JsonParseException {
        Token token;
        do {
            token = nextToken();
            tokens.add(token);
        }
        while (token.getType() != TokenType.FINISH);
    }

    public Token next() {
        return tokens.remove(0);
    }

    public Token peek() {
        return tokens.get(0);
    }

    public boolean hasNext() {
        return tokens.size() != 0;
    }

    // --- 私有方法 -----------------------------------------------------------------------------------------------------
    private Token nextToken() throws JsonParseException {
        current = '?';
        do {
            current = read(); // 读取字符，知道第一个不为空白符的字符出现
        }
        while (isSpace(current));

        if (isNull(current)) {
            return new Token(TokenType.NULL, null);
        }
        else if (current == ',') {
            return new Token(TokenType.COMMA, ",");
        }
        else if (current == ':') {
            return new Token(TokenType.COLON, ":");
        }
        else if (current == '{') {
            return new Token(TokenType.OBJ_PREFIX, "{");
        }
        else if (current == '}') {
            return new Token(TokenType.OBJ_SUFFIX, "}");
        }
        else if (current == '[') {
            return new Token(TokenType.ARRAY_PREFIX, "[");
        }
        else if (current == ']') {
            return new Token(TokenType.ARRAY_SUFFIX, "]");
        }
        else if (isTrue(current)) {
            return new Token(TokenType.BOOLEAN, "true");
        }
        else if (isFalse(current)) {
            return new Token(TokenType.BOOLEAN, "false");
        }
        else if (isNum(current)) {
            unread(); // 让下一次读取读取缓存
            return readNum();
        }
        else if (current == '"') {
            return readString();
        }
        else if (current == -1) {
            return new Token(TokenType.FINISH, "EOF");
        }
        else {
            throw new JsonParseException("invalid input");
        }
    }

    private int read() throws JsonParseException {
        if (!isUnread) {
            int c = reader.read();
            saved = c;
            return c;
        }
        else {
            isUnread = false;
            return saved;
        }
    }

    private boolean isSpace(int c) {
        return c >= '0' && c <= ' ';
    }

    private boolean isNull(int c) throws JsonParseException {
        if (c == 'n') {
            c = read();
            if (c == 'u') {
                c = read();
                if (c == 'l') {
                    c = read();
                    if (c == 'l') {
                        return true;
                    }
                    else {
                        throw new JsonParseException("invalid input");
                    }
                }
                else {
                    throw new JsonParseException("invalid input");
                }
            }
            else  {
                throw new JsonParseException("invalid input");
            }
        }
        else {
            return false;
        }
    }

    private boolean isTrue(int c) throws JsonParseException {
        if (c == 't') {
            c = read();
            if (c == 'r') {
                c = read();
                if (c == 'u') {
                    c = read();
                    if (c == 'e') {
                        return true;
                    }
                    else {
                        throw new JsonParseException("invalid input");
                    }
                }
                else {
                    throw new JsonParseException("invalid input");
                }
            }
            else {
                throw new JsonParseException("invalid input");
            }
        }
        else {
            return false;
        }
    }

    private boolean isFalse(int c) throws JsonParseException {
        if (c == 'f') {
            c = read();
            if (c == 'a') {
                c = read();
                if (c == 'l') {
                    c = read();
                    if (c == 's') {
                        c = read();
                        if (c == 'e') {
                            return true;
                        }
                        else {
                            throw new JsonParseException("invalid input");
                        }
                    }
                    else {
                        throw new JsonParseException("invalid input");
                    }
                }
                else {
                    throw new JsonParseException("invalid input");
                }
            }
            else {
                throw new JsonParseException("invalid input");
            }
        }
        else {
            return false;
        }
    }

    private boolean isNum(int c) {
        return isDigit(c) || c == '-';
    }

    private boolean isDigit(int c) {
        return c >= '0' && c <= '9';
    }

    private void unread() {
        isUnread = true;
    }

    private Token readNum() throws JsonParseException {
        StringBuilder sb = new StringBuilder();
        int c = read();
        if (c == '-') { // -
            sb.append((char) c);
            c = read();
            if (c == '0') { // -0
                sb.append((char) c);
                appendLeftNum(sb); // 读取数字剩余部分（有可能是科学计数，也有可能是小数）
            }
            else if (isDigitOne2Night(c)) { // -1~9
                do {
                    sb.append((char) c);
                    c = read();
                }
                while (isDigit(c));
                unread();
                appendLeftNum(sb); // 读取数字剩余部分（有可能是科学计数，也有可能是小数）
            }
            else {
                throw new JsonParseException("- not followed by digit");
            }
        }
        else if (c == '0') {
            sb.append((char) c);
            appendLeftNum(sb); // 读取数字剩余部分（有可能是科学计数，也有可能是小数）
        }
        else if (isDigitOne2Night(c)) {
            do {
                sb.append((char) c);
                c = read();
            }
            while (isDigit(c));
            unread();
            appendLeftNum(sb);
        }

        return new Token(TokenType.NUMBER, sb.toString());
    }

    private void appendLeftNum(StringBuilder sb) throws JsonParseException {
        current = read();
        if (current == '.') { // frac
            sb.append((char) current);
            appendFrac(sb);
            if (isExp(current)) { // e or E
                sb.append((char) current);
                appendExp(sb);
            }
        }
        else if (isExp(current)) {
            sb.append((char) current);
            appendExp(sb);
        }
        else {
            unread(); // 数字解析完全，使下一次开始tokenize的时候读取的是缓存
        }
    }

    private void appendFrac(StringBuilder sb) throws JsonParseException {
        current = read();
        while (isDigit(current)) {
            sb.append((char) current);
            current = read();
        }
        unread();
    }

    private boolean isExp(int c) {
        return c == 'e' || c == 'E';
    }

    private void appendExp(StringBuilder sb) throws JsonParseException {
        int c = read();
        if (c == '+' || c == '-') { // e(E)+(-)
            sb.append((char) c);
            c = read();
            if (!isDigit(c)) {
                throw new JsonParseException("e+(-) or E+(-) not followed by digit");
            }
            else {
                do {
                    sb.append((char) c);
                    c = read();
                }
                while (isDigit(c));
                unread(); // 使下一次开始tokenize的时候读取的是缓存
            }
        }
        else if (!isDigit(c)) {
            throw new JsonParseException("e or E not followed by digit");
        }
        else {
            do {
                sb.append((char) c);
                c = read();
            }
            while (isDigit(c));
            unread(); // 使下一次开始tokenize的时候读取的是缓存
        }
    }

    private boolean isDigitOne2Night(int c) {
        return c >= '1' && c <= '9';
    }

    private Token readString() throws JsonParseException {
        StringBuilder sb = new StringBuilder();

        while (true) {
            current = read();
            if (isEscape()) {
                if (current == 'u') {
                    sb.append('\\').append((char) current);
                    for (int i = 0; i < 4; i++) {
                        current = read();
                        if (isHex(current)) {
                            sb.append((char) current);
                        }
                        else {
                            throw new JsonParseException("invalid input");
                        }
                    }
                }
                else {
                    sb.append((char) current);
                }
            }
            else if (current == '"') {
                return new Token(TokenType.STRING, sb.toString());
            }
            else if (current == '\r' || current == '\n') {
                throw new JsonParseException("invalid input");
            }
            else {
                sb.append((char) current);
            }
        }
    }

    private boolean isEscape() throws JsonParseException {
        //判断是否为\", \\, \/, \b, \f, \n, \t, \r.
        if (current == '\\') {
            current = read();
            if (current == '"' || current == '\\' || current == '/' || current == 'b' || current == 'f' || current == 'n' || current == 't' || current == 'r' || current == 'u') {
                return true;
            } else {
                throw new JsonParseException("invalid input.");
            }
        }
        else {
            return false;
        }
    }

    private boolean isHex(int c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
    }
}
