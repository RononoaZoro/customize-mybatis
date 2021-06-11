package com.luo.ibatis.parsing;

/**
 * @author ：archer
 * @date ：Created in 2021/6/9 15:01
 * @description：通用令牌解析器
 * @modified By：
 */
public class GenericTokenParser {

    private final String openToken;
    private final String closeToken;
    private final TokenHandler handler;

    public GenericTokenParser(String openToken, String closeToken, TokenHandler handler) {
        this.openToken = openToken;
        this.closeToken = closeToken;
        this.handler = handler;
    }

    public String parse(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        // 获取第一个openToken在SQL中的位置
        int start = text.indexOf(openToken, 0);
        // start为-1说明SQL中不存在任何参数占位符
        if (start == -1) {
            return text;
        }
        // 將SQL转换为char数组
        char[] src = text.toCharArray();
        // offset用于记录已解析的#{或者}的偏移量，避免重复解析
        int offset = 0;
        final StringBuilder builder = new StringBuilder();
        // expression为参数占位符中的内容
        StringBuilder expression = null;
        // 遍历获取所有参数占位符的内容，然后调用TokenHandler的handleToken（）方法替换参数占位符
        while (start > -1) {
            if (start > 0 && src[start - 1] == '\\') {
                // this open token is escaped. remove the backslash and continue.
                // 这个打开的令牌被转义了。 删除反斜杠并继续。
                builder.append(src, offset, start - offset - 1).append(openToken);
                offset = start + openToken.length();
            } else {
                // found open token. let's search close token.
                if (expression == null) {
                    expression = new StringBuilder();
                } else {
                    expression.setLength(0);
                }
                builder.append(src, offset, start - offset);
                offset = start + openToken.length();
                int end = text.indexOf(closeToken, offset);
                while (end > -1) {
                    if (end > offset && src[end - 1] == '\\') {
                        // this close token is escaped. remove the backslash and continue.
                        expression.append(src, offset, end - offset - 1).append(closeToken);
                        offset = end + closeToken.length();
                        end = text.indexOf(closeToken, offset);
                    } else {
                        expression.append(src, offset, end - offset);
                        offset = end + closeToken.length();
                        break;
                    }
                }
                if (end == -1) {
                    // close token was not found.
                    builder.append(src, start, src.length - start);
                    offset = src.length;
                } else {
                    // 调用TokenHandler的handleToken（）方法替换参数占位符
                    builder.append(handler.handleToken(expression.toString()));
                    offset = end + closeToken.length();
                }
            }
            start = text.indexOf(openToken, offset);
        }
        if (offset < src.length) {
            builder.append(src, offset, src.length - offset);
        }

        return builder.toString();
    }
}
