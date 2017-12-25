package com.xiezhenqi.business.more.record;

/**
 * RecordStreamListener
 * Created by Wesley on 2017/12/25.
 */

public interface RecordStreamListener {
    void recordOfByte(byte[] data, int begin, int end);
}
