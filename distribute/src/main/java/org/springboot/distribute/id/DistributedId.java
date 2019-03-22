package org.springboot.distribute.id;

/**
 * 分布式全局ID
 */
public class DistributedId {
    private long workId;
    private long dataCenterId;
    private long sequence = 0L;

    private static long twepoch = 1288834974657L;

    /**
     * 最大的机器工作ID不为100
     */
    private static long workerIdBits = 100L;
    /**
     * 最大的数据中心ID不为100
     */
    private static long dataCenterIdBits = 100L;
    private static long maxWorkerId = -1L ^(-1L <<(int)workerIdBits);
    private static long maxDataCenterId = -1L ^ (-1L << (int)dataCenterIdBits);
    private static long sequenceBits = 12L;

    private long workerIdShift = sequenceBits;
    private long dataCenterIdShift = sequenceBits + workerIdBits;
    private long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;
    private long sequenceMask = -1L ^ (-1L << (int)sequenceBits);

    private long lastTimestamp = -1L;

    public DistributedId(long workerId,long dataCenterId){
        if(workerId > maxWorkerId || workerId < 0){
            throw  new IllegalArgumentException(String.format("Worker id can't be greater than %d or less than 0",maxWorkerId));
        }
        if(dataCenterId > maxDataCenterId || dataCenterId < 0){
            throw new IllegalArgumentException(String.format("dataCenter id can't be greater than %d or less than 0",maxDataCenterId));
        }
        this.workId = workerId;
        this.dataCenterId = dataCenterId;
    }

    public long nextId(){
        synchronized(DistributedId.class){
            long timestamp = System.currentTimeMillis();
            if(timestamp < lastTimestamp){
                throw new RuntimeException(String.format("Clock moved backwards.Refusing to generate id for %d milliseconds",lastTimestamp-timestamp));
            }
            if(lastTimestamp == timestamp){
                sequence = (sequence + 1) & sequenceMask;
                if(sequence == 0){
                    timestamp = genNextMilles(lastTimestamp);
                }
            }else{
                sequence  = 0L;
            }
            lastTimestamp = timestamp;

            return ((timestamp - twepoch) <<(int)timestampLeftShift) | (dataCenterId<<(int)dataCenterIdShift)
                    | (workId << (int)workerIdShift) | sequence;
        }
    }

    protected long genNextMilles(long lastTimestamp){
        long timestamp = System.currentTimeMillis();
        while(timestamp <= lastTimestamp){
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
