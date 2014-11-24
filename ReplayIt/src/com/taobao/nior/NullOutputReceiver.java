package com.taobao.nior;

public final class NullOutputReceiver implements IShellOutputReceiver {

    private static NullOutputReceiver sReceiver = new NullOutputReceiver();

    public static IShellOutputReceiver getReceiver() {
        return sReceiver;
    }

    /* (non-Javadoc)
     * @see com.android.ddmlib.adb.IShellOutputReceiver#addOutput(byte[], int, int)
     */
    public void addOutput(byte[] data, int offset, int length) {
    }

    /* (non-Javadoc)
     * @see com.android.ddmlib.adb.IShellOutputReceiver#flush()
     */
    public void flush() {
    }

}