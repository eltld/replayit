package com.taobao.nior;

public interface IShellOutputReceiver {
    /**
     * Called every time some new data is available.
     * @param data The new data.
     * @param offset The offset at which the new data starts.
     * @param length The length of the new data.
     */
    public void addOutput(byte[] data, int offset, int length);

    /**
     * Called at the end of the process execution (unless the process was
     * canceled). This allows the receiver to terminate and flush whatever
     * data was not yet processed.
     */
    public void flush();

};