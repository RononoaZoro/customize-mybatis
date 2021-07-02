package com.luo.ibatis.cursor;

import java.io.Closeable;

/**
 * @author ：archer
 * @date ：Created in 2021/7/1 11:02
 * @description：
 * Cursor contract to handle fetching items lazily using an Iterator.
 * Cursors are a perfect fit to handle millions of items queries that would not normally fits in memory.
 * Cursor SQL queries must be ordered (resultOrdered="true") using the id columns of the resultMap.
 */
public interface Cursor<T> extends Closeable, Iterable<T>{

    /**
     * @return true if the cursor has started to fetch items from database.
     */
    boolean isOpen();

    /**
     *
     * @return true if the cursor is fully consumed and has returned all elements matching the query.
     */
    boolean isConsumed();

    /**
     * Get the current item index. The first item has the index 0.
     * @return -1 if the first cursor item has not been retrieved. The index of the current item retrieved.
     */
    int getCurrentIndex();
}
